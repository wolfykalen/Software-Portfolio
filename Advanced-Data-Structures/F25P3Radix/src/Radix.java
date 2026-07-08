import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * LSD Radix sorter that operates directly on a binary file whose records are
 * 8-byte (key, data) pairs of 32-bit integers. The implementation obeys the
 * assignment rule that at most one 900 000-byte array is used as working
 * memory; all other in-memory allocations are small.
 * 
 * @author (kalendaco)
 * @version (1)
 */
public class Radix {

    /** 
     * Size (in bytes) of a single record 
     */
    private static final int RECORD_SIZE = 8;

    /**
     *  Logical disk-block size
     */
    private static final int BLOCK_SIZE = 4096;

    /** 
     * Size of the single working memory buffer 
     */
    private static final int POOL_SIZE = 900_000;

    /** 
     * Size of the read buffer 
     */
    private static final int READ_BUF_SIZE = 262_144; 

    /** 
     * Number of 4 096-byte blocks that fit in the cache portion of the pool. 
     */
    private static final int CACHE_BLOCKS = (POOL_SIZE - READ_BUF_SIZE) 
        / BLOCK_SIZE;

    /** 
     * Number of bits processed per pass 
     */
    private static final int BITS_PER_PASS = 8;

    /** 
     * Radix = 2^BITS PER PASS. 
     */
    private static final int RADIX = 1 << BITS_PER_PASS;

    /** 
     * Mask to isolate a digit. 
     */
    private static final int MASK = RADIX - 1;

    /** 
     * Primary file reference  
     */
    private RandomAccessFile fileA;

    /** 
     * Secondary scratch file 
     */
    private RandomAccessFile fileB;

    /** 
     * Stats writer inherited from {@link RadixProj}. 
     */
    private final PrintWriter stats;

    /** 
     * Single working memory buffer
     */
    private final byte[] pool;

    /** 
     * Counting array 
     */
    private final int[] count;

    /** 
     * Running total of logical 4 096-byte disk blocks read. 
     */
    private long diskReads;

    /** 
     * Running total of logical 4 096-byte disk blocks written. 
     */
    private long diskWrites;

    /** 
     * Cached number of records (key/data pairs) in the input file. 
     */
    private final long numRecords;

    private final int[] cacheBlockId;
    private final boolean[] cacheDirty;
    private int nextVictim;


    /**
     * Constructs a Radix sorter and sorts {@code theFile} in place.
     *
     * @param theFile The RandomAcessFile to be sorted
     * @param s the stats PrintWriter
     * 
     * @throws IOException 
     */
    public Radix(RandomAccessFile theFile, PrintWriter s) throws IOException {
        this.fileA = theFile; 
        this.stats = s;

        File tmp = File.createTempFile("radix_scratch", ".bin");
        tmp.deleteOnExit();
        this.fileB = new RandomAccessFile(tmp, "rw");
        this.fileB.setLength(theFile.length());

        this.pool = new byte[POOL_SIZE];
        this.count = new int[RADIX];

        this.cacheBlockId = new int[CACHE_BLOCKS];
        Arrays.fill(this.cacheBlockId, -1);
        this.cacheDirty = new boolean[CACHE_BLOCKS];
        this.nextVictim = 0;

        this.diskReads = 0;
        this.diskWrites = 0;
        this.numRecords = theFile.length() / RECORD_SIZE;

        radixSort();

        stats.println("Blocks in pool: " + (POOL_SIZE / BLOCK_SIZE));
        stats.println("Disk reads:  " + diskReads);
        stats.println("Disk writes: " + diskWrites);
        stats.flush();

        fileB.close();
    }

    /**
     * LSD radix sort on 32-bit keys using 16 bits per
     * pass. 
     */
    private void radixSort() throws IOException {
        final int passes = 32 / BITS_PER_PASS; 

        ByteBuffer readBuf = ByteBuffer.wrap(pool, 0, READ_BUF_SIZE)
            .order(ByteOrder.BIG_ENDIAN);

        for (int pass = 0; pass < passes; pass++) {
            int shift = pass * BITS_PER_PASS;

            //counting  
            Arrays.fill(count, 0);
            long bytesRemaining = fileA.length();
            fileA.seek(0);
            while (bytesRemaining > 0) {
                int chunk = (int) Math.min(bytesRemaining, READ_BUF_SIZE);
                fileA.readFully(pool, 0, chunk);
                diskReads += (chunk + BLOCK_SIZE - 1) / BLOCK_SIZE;

                int records = chunk / RECORD_SIZE;
                for (int off = 0, rec = 0; rec < records; rec++, off 
                    += RECORD_SIZE) {
                    int key = readBuf.getInt(off);
                    int digit = (key >>> shift) & MASK;
                    count[digit]++;
                }
                bytesRemaining -= chunk;
            }

            int running = 0;
            for (int i = 0; i < RADIX; i++) {
                int c = count[i];
                count[i] = running;
                running += c;
            }

            Arrays.fill(cacheDirty, false);
            Arrays.fill(cacheBlockId, -1);
            nextVictim = 0;

            // Redistribution
            bytesRemaining = fileA.length();
            fileA.seek(0);
            while (bytesRemaining > 0) {
                int chunk = (int) Math.min(bytesRemaining, READ_BUF_SIZE);
                fileA.readFully(pool, 0, chunk);
                diskReads += (chunk + BLOCK_SIZE - 1) / BLOCK_SIZE;

                int records = chunk / RECORD_SIZE;
                for (int off = 0, rec = 0; rec < records; rec++, off 
                    += RECORD_SIZE) {
                    int key = readBuf.getInt(off);
                    int data = readBuf.getInt(off + 4);
                    int digit = (key >>> shift) & MASK;

                    long destIndex = count[digit]++;
                    long destBytePos = destIndex * RECORD_SIZE;
                    writeRecordToCache(destBytePos, key, data);
                }
                bytesRemaining -= chunk;
            }
            flushAllCache();

            RandomAccessFile tmp = fileA;
            fileA = fileB;
            fileB = tmp;
        }
    }

    /**
     * Write a (key,data) pair into the in-memory cache
     */
    private void writeRecordToCache(long destBytePos, int key, int data) 
        throws IOException {
        int blockNum = (int) (destBytePos / BLOCK_SIZE);
        int slot = findCacheSlot(blockNum);
        if (slot == -1) {
            slot = loadBlockIntoCache(blockNum);
        }
        int offsetInBlock = (int) (destBytePos % BLOCK_SIZE);
        int base = READ_BUF_SIZE + slot * BLOCK_SIZE;
        putIntBigEndian(pool, base + offsetInBlock, key);
        putIntBigEndian(pool, base + offsetInBlock + 4, data);
        cacheDirty[slot] = true;
    }

    /** 
     * Return cache slot index for the given disk block, or -1 if not present. 
     */
    private int findCacheSlot(int blockNum) {
        for (int i = 0; i < CACHE_BLOCKS; i++) {
            if (cacheBlockId[i] == blockNum) {
                return i;
            }
        }
        return -1;
    }

    /** 
     * Bring a disk block into the cache, evicting a victim if necessary. 
     */
    private int loadBlockIntoCache(int blockNum) throws IOException {
        int slot = nextVictim;
        nextVictim = (nextVictim + 1) % CACHE_BLOCKS;

        if (cacheBlockId[slot] != -1) {
            flushCacheSlot(slot);
        }

        long bytePos = (long) blockNum * BLOCK_SIZE;
        fileB.seek(bytePos);
        fileB.readFully(pool, READ_BUF_SIZE + slot * BLOCK_SIZE, BLOCK_SIZE);
        diskReads++;

        cacheBlockId[slot] = blockNum;
        cacheDirty[slot] = false;
        return slot;
    }

    /** 
     * Flush one cache slot to disk if dirty.
      */
    private void flushCacheSlot(int slot) throws IOException {
        if (!cacheDirty[slot]) {
            return;
        }
        long bytePos = (long) cacheBlockId[slot] * BLOCK_SIZE;
        fileB.seek(bytePos);
        fileB.write(pool, READ_BUF_SIZE + slot * BLOCK_SIZE, BLOCK_SIZE);
        diskWrites++;
        cacheDirty[slot] = false;
    }

    /** 
     * Flush all dirty blocks and clear cache metadata. 
     */
    private void flushAllCache() throws IOException {
        for (int slot = 0; slot < CACHE_BLOCKS; slot++) {
            flushCacheSlot(slot);
            cacheBlockId[slot] = -1;
        }
    }

    /**
     * Store an int value into the shared pool at the specified byte
     *
     * @param arr the destination byte array 
     * @param pos index of the most-significant byte 
     * @param val 32-bit integer to encode
     */
    private static void putIntBigEndian(byte[] arr, int pos, int val) {
        arr[pos]     = (byte)(val >>> 24);
        arr[pos + 1] = (byte)(val >>> 16);
        arr[pos + 2] = (byte)(val >>> 8);
        arr[pos + 3] = (byte) val;
    }
}
