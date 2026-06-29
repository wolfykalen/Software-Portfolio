import java.util.Random;
import student.TestCase;

/**
 * Mutation coverage tests for AirControl project.
 * Tests edge cases and boundary conditions for mutation testing.
 *
 * @author {Kalendaco}
 * @version {1}
 */
public class AirControlMutationTest extends TestCase {

    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        // Nothing here
    }


    // ----------------------------------------------------------
    /**
     * Test SkipList operations for mutation coverage.
     *
     * @throws Exception
     */
    public void testSkipListMutations() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertTrue(w.add(new Balloon("a1", 10, 10, 10, 10, 10, 10, "hot", 5)));
        String skiplist1 = w.printskiplist();
        assertTrue(skiplist1.contains("1 skiplist nodes printed"));
        assertTrue(w.add(new Balloon("b1", 20, 20, 20, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("c1", 30, 30, 30, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("d1", 40, 40, 40, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("e1", 50, 50, 50, 10, 10, 10, "hot", 5)));
        String skiplist5 = w.printskiplist();
        assertTrue(skiplist5.contains("5 skiplist nodes printed"));
        assertNotNull(w.print("a1"));
        assertNotNull(w.print("c1"));
        assertNotNull(w.print("e1"));
        assertNull(w.print("z1"));
        assertNull(w.print("aa"));
        String range1 = w.rangeprint("a1", "a1");
        assertTrue(range1.contains("a1"));
        String range2 = w.rangeprint("a", "z");
        assertTrue(range2.contains("a1"));
        assertTrue(range2.contains("e1"));
        assertNotNull(w.delete("c1"));
        String skiplist4 = w.printskiplist();
        assertTrue(skiplist4.contains("4 skiplist nodes printed"));
        assertNull(w.delete("z1"));
        assertNotNull(w.delete("a1"));
        assertNotNull(w.delete("b1"));
        assertNotNull(w.delete("d1"));
        assertNotNull(w.delete("e1"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertTrue(w.add(new Balloon("dup1", 60, 60, 60, 10, 10, 10, "hot", 5)));
        assertFalse(w.add(new Balloon("dup1", 70, 70, 70, 10, 10, 10, "hot", 5)));
    }


    // ----------------------------------------------------------
    /**
     * Test LeafNode operations for mutation coverage.
     *
     * @throws Exception
     */
    public void testLeafNodeMutations() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("x1", 0, 0, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("x2", 100, 0, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("x3", 200, 0, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("x4", 300, 0, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("x5", 400, 0, 0, 50, 50, 50, "hot", 5)));

        assertTrue(w.add(new Balloon("y1", 0, 100, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("y2", 0, 200, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("y3", 0, 300, 0, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("y4", 0, 400, 0, 50, 50, 50, "hot", 5)));

        assertTrue(w.add(new Balloon("z1", 0, 0, 100, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("z2", 0, 0, 200, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("z3", 0, 0, 300, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("z4", 0, 0, 400, 50, 50, 50, "hot", 5)));

        String dump = w.printbintree();
        assertTrue(dump.contains("Bintree nodes printed"));

        String intersect1 = w.intersect(0, 0, 0, 100, 100, 100);
        assertNotNull(intersect1);
        assertTrue(intersect1.contains("nodes were visited"));

        String intersect2 = w.intersect(0, 0, 0, 500, 500, 500);
        assertNotNull(intersect2);

        assertTrue(w.add(new Balloon("overlap1", 10, 10, 10, 30, 30, 30, "hot", 5)));
        assertTrue(w.add(new Balloon("overlap2", 20, 20, 20, 30, 30, 30, "hot", 5)));
        String collisions = w.collisions();
        assertNotNull(collisions);

        assertNotNull(w.delete("x1"));
        assertNotNull(w.delete("x2"));
        assertNotNull(w.delete("x3"));
        assertNotNull(w.delete("x4"));
        assertNotNull(w.delete("x5"));
        assertNotNull(w.delete("y1"));
        assertNotNull(w.delete("y2"));
        assertNotNull(w.delete("y3"));
        assertNotNull(w.delete("y4"));
        assertNotNull(w.delete("z1"));
        assertNotNull(w.delete("z2"));
        assertNotNull(w.delete("z3"));
        assertNotNull(w.delete("z4"));
        assertNotNull(w.delete("overlap1"));
        assertNotNull(w.delete("overlap2"));

        String dump2 = w.printbintree();
        assertNotNull(dump2);
    }


    // ----------------------------------------------------------
    /**
     * Test InternalNode operations for mutation coverage.
     * Tests all three dimensions (X, Y, Z) for insert, remove, intersect.
     *
     * @throws Exception
     */
    public void testInternalNodeMutations() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("xLeft", 100, 500, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("xRight", 600, 500, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("xLeft2", 200, 500, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("xRight2", 700, 500, 500, 50, 50, 50, "hot", 5)));

        assertTrue(w.add(new Balloon("yTop", 500, 100, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("yBottom", 500, 600, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("yTop2", 500, 200, 500, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("yBottom2", 500, 700, 500, 50, 50, 50, "hot", 5)));

        assertTrue(w.add(new Balloon("zFront", 500, 500, 100, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("zBack", 500, 500, 600, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("zFront2", 500, 500, 200, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("zBack2", 500, 500, 700, 50, 50, 50, "hot", 5)));

        String dump = w.printbintree();
        assertTrue(dump.contains("Bintree nodes printed"));

        String intLeft = w.intersect(0, 0, 0, 512, 1024, 1024);
        assertNotNull(intLeft);
        assertTrue(intLeft.contains("xLeft"));

        String intRight = w.intersect(512, 0, 0, 512, 1024, 1024);
        assertNotNull(intRight);
        assertTrue(intRight.contains("xRight"));

        String intTop = w.intersect(0, 0, 0, 1024, 512, 1024);
        assertNotNull(intTop);
        assertTrue(intTop.contains("yTop"));

        String intBottom = w.intersect(0, 512, 0, 1024, 512, 1024);
        assertNotNull(intBottom);
        assertTrue(intBottom.contains("yBottom"));

        String intFront = w.intersect(0, 0, 0, 1024, 1024, 512);
        assertNotNull(intFront);
        assertTrue(intFront.contains("zFront"));

        String intBack = w.intersect(0, 0, 512, 1024, 1024, 512);
        assertNotNull(intBack);
        assertTrue(intBack.contains("zBack"));

        String collisions = w.collisions();
        assertNotNull(collisions);

        assertNotNull(w.delete("xLeft"));
        assertNotNull(w.delete("xRight"));
        assertNotNull(w.delete("xLeft2"));
        assertNotNull(w.delete("xRight2"));
        assertNotNull(w.delete("yTop"));
        assertNotNull(w.delete("yBottom"));
        assertNotNull(w.delete("yTop2"));
        assertNotNull(w.delete("yBottom2"));
        assertNotNull(w.delete("zFront"));
        assertNotNull(w.delete("zBack"));
        assertNotNull(w.delete("zFront2"));
        assertNotNull(w.delete("zBack2"));

        String dumpEmpty = w.printbintree();
        assertTrue(dumpEmpty.contains("1 Bintree nodes printed"));
    }


    // ----------------------------------------------------------
    /**
     * Test LeafNode edge cases for mutation coverage.
     *
     * @throws Exception
     */
    public void testLeafNodeEdgeCases() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("ov1", 100, 100, 100, 100, 100, 100, "hot", 5)));
        assertTrue(w.add(new Balloon("ov2", 110, 110, 110, 80, 80, 80, "hot", 5)));
        assertTrue(w.add(new Balloon("ov3", 120, 120, 120, 60, 60, 60, "hot", 5)));

        String dump1 = w.printbintree();
        assertTrue(dump1.contains("Leaf with 3 objects"));

        String int1 = w.intersect(100, 100, 100, 100, 100, 100);
        assertNotNull(int1);
        assertTrue(int1.contains("ov1"));
        assertTrue(int1.contains("ov2"));
        assertTrue(int1.contains("ov3"));

        String int2 = w.intersect(500, 500, 500, 10, 10, 10);
        assertNotNull(int2);
        assertFalse(int2.contains("ov1"));

        assertNotNull(w.delete("ov2"));
        String dump2 = w.printbintree();
        assertTrue(dump2.contains("Leaf with 2 objects"));

        assertNotNull(w.delete("ov1"));
        String dump3 = w.printbintree();
        assertTrue(dump3.contains("Leaf with 1 objects"));

        assertNotNull(w.delete("ov3"));
        String dump4 = w.printbintree();
        assertTrue(dump4.contains("1 Bintree nodes printed"));

        assertTrue(w.add(new Balloon("a1", 0, 0, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a2", 900, 0, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a3", 0, 900, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a4", 0, 0, 900, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a5", 900, 900, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a6", 900, 0, 900, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a7", 0, 900, 900, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("a8", 900, 900, 900, 10, 10, 10, "hot", 5)));

        String dump5 = w.printbintree();
        assertTrue(dump5.contains("Bintree nodes printed"));

        String coll1 = w.collisions();
        assertNotNull(coll1);

        assertTrue(w.add(new Balloon("c1", 450, 450, 450, 100, 100, 100, "hot", 5)));
        assertTrue(w.add(new Balloon("c2", 480, 480, 480, 100, 100, 100, "hot", 5)));
        String coll2 = w.collisions();
        assertNotNull(coll2);
        assertTrue(coll2.contains("c1") && coll2.contains("c2"));

        assertNotNull(w.delete("a1"));
        assertNotNull(w.delete("a2"));
        assertNotNull(w.delete("a3"));
        assertNotNull(w.delete("a4"));
        assertNotNull(w.delete("a5"));
        assertNotNull(w.delete("a6"));
        assertNotNull(w.delete("a7"));
        assertNotNull(w.delete("a8"));
        assertNotNull(w.delete("c1"));
        assertNotNull(w.delete("c2"));
    }


    // ----------------------------------------------------------
    /**
     * Test BintreeHelper intersection methods for mutation coverage.
     *
     * @throws Exception
     */
    public void testBintreeHelperMutations() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("adj1", 100, 100, 100, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("adj2", 150, 100, 100, 50, 50, 50, "hot", 5)));
        String coll1 = w.collisions();
        assertFalse(coll1.contains("adj1") && coll1.contains("adj2"));

        assertTrue(w.add(new Balloon("ovx1", 300, 100, 100, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("ovx2", 349, 100, 100, 50, 50, 50, "hot", 5)));
        String coll2 = w.collisions();
        assertTrue(coll2.contains("ovx1") && coll2.contains("ovx2"));

        assertTrue(w.add(new Balloon("ovy1", 100, 300, 100, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("ovy2", 100, 349, 100, 50, 50, 50, "hot", 5)));
        String coll3 = w.collisions();
        assertTrue(coll3.contains("ovy1") && coll3.contains("ovy2"));

        assertTrue(w.add(new Balloon("ovz1", 100, 100, 300, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("ovz2", 100, 100, 349, 50, 50, 50, "hot", 5)));
        String coll4 = w.collisions();
        assertTrue(coll4.contains("ovz1") && coll4.contains("ovz2"));

        String int1 = w.intersect(100, 100, 100, 50, 50, 50);
        assertNotNull(int1);
        assertTrue(int1.contains("adj1"));

        String int2 = w.intersect(150, 100, 100, 50, 50, 50);
        assertNotNull(int2);
        assertTrue(int2.contains("adj2"));

        String int3 = w.intersect(50, 100, 100, 50, 50, 50);
        assertNotNull(int3);
        assertFalse(int3.contains("adj1"));

        assertTrue(w.add(new Balloon("d1", 0, 0, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("d2", 1000, 0, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("d3", 0, 1000, 0, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("d4", 0, 0, 1000, 10, 10, 10, "hot", 5)));

        String dump = w.printbintree();
        assertTrue(dump.contains("Bintree nodes printed"));

        assertNotNull(w.delete("adj1"));
        assertNotNull(w.delete("adj2"));
        assertNotNull(w.delete("ovx1"));
        assertNotNull(w.delete("ovx2"));
        assertNotNull(w.delete("ovy1"));
        assertNotNull(w.delete("ovy2"));
        assertNotNull(w.delete("ovz1"));
        assertNotNull(w.delete("ovz2"));
        assertNotNull(w.delete("d1"));
        assertNotNull(w.delete("d2"));
        assertNotNull(w.delete("d3"));
        assertNotNull(w.delete("d4"));
    }
}
