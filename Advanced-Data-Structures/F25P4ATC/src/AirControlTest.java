import java.util.Random;
import student.TestCase;

/**
 * @author {Kalendaco}
 * @version {1}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     *
     * @throws Exception
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }


    // ----------------------------------------------------------
    /**
     * Test syntax Sample Input/Output
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise",
            0, 100, 20, 10, 50, 50, 5000, 99.29)));

        assertFuzzyEquals(
            "Rocket Enterprise 0 100 20 10 50 50 5000 99.29",
            w.delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4",
            w.print("Air1"));
        assertNull(w.print("air1"));

        assertFuzzyEquals(
            "I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "5 Bintree nodes printed\r\n",
                w.printbintree());

        assertFuzzyEquals(
            "Node has depth 3, Value (null)\r\n"
                + "Node has depth 3, "
                + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "Node has depth 1, "
                + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "Node has depth 2, "
                + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "Node has depth 2, "
                + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "4 skiplist nodes printed\r\n",
                w.printskiplist());

        assertFuzzyEquals(
            "Found these records in the range a to z\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
                w.rangeprint("a", "z"));
        assertFuzzyEquals(
            "Found these records in the range a to l\r\n",
            w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));

        assertFuzzyEquals(
            "The following collisions exist in the database:\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
                + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n",
                w.collisions());

        assertFuzzyEquals(
            "The following objects intersect (0 0 0 1024 1024 1024):\r\n"
                + "In Internal node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "In Internal node (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "Airplane Air1 0 10 1 20 2 30 USAir 717 4\r\n"
                + "Balloon B1 10 11 11 21 12 31 hot_air 15\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "Drone Air2 100 1010 101 924 2 900 Droners 3\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "5 nodes were visited in the bintree\r\n",
                w.intersect(0, 0, 0, 1024, 1024, 1024));
    }


    // ----------------------------------------------------------
    /**
     * Test syntax Check various forms of bad input parameters
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, null, 1, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 0, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 1, 0)));
        assertFalse(w.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", -1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, -1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, -1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 0, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 0, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 0, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, "hot", -1)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, -1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, 1, -1.1)));
        assertFalse(w.add(
            new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1, 1)));
        assertNull(w.delete(null));
        assertNull(w.print(null));
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("a", null));
        assertNull(w.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, -1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, -1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, -1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, -1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, -1));
        assertNull(w.intersect(2000, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 2000, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 2000, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 2000, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 2000, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, 2000));
        assertNull(w.intersect(1000, 1, 1, 1000, 1, 1));
        assertNull(w.intersect(1, 1000, 1, 1, 1000, 1));
        assertNull(w.intersect(1, 1, 1000, 1, 1, 1000));
    }


    // ----------------------------------------------------------
    /**
     * Test boundary conditions for bounding box validation.
     *
     * @throws Exception
     */
    public void testBoundaryConditions() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        // Test valid boundary origin at 0
        assertTrue(w.add(new Balloon("b1", 0, 0, 0, 10, 10, 10, "hot", 5)));

        // Test valid boundary origin at max (1023)
        assertTrue(w.add(new Balloon("b2", 1023, 1023, 1023, 1, 1, 1, "hot", 5)));

        // Test valid boundary minimum width (1)
        assertTrue(w.add(new Balloon("b3", 100, 100, 100, 1, 1, 1, "hot", 5)));

        // Test valid boundary maximum width (1024) with origin at 0
        assertTrue(w.add(new Balloon("b4", 0, 0, 0, 1024, 1024, 1024, "hot", 5)));

        // Test valid boundary origin + width = 1024 exactly
        assertTrue(w.add(new Balloon("b5", 500, 500, 500, 524, 524, 524, "hot", 5)));

        // Test invalid origin at 1024 
        assertFalse(w.add(new Balloon("bad1", 1024, 0, 0, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad2", 0, 1024, 0, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad3", 0, 0, 1024, 1, 1, 1, "hot", 5)));

        // Test invalid width at 1025 
        assertFalse(w.add(new Balloon("bad4", 0, 0, 0, 1025, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad5", 0, 0, 0, 1, 1025, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad6", 0, 0, 0, 1, 1, 1025, "hot", 5)));

        // Test invalid origin + width > 1024
        assertFalse(w.add(new Balloon("bad7", 1023, 0, 0, 2, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad8", 0, 1023, 0, 1, 2, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("bad9", 0, 0, 1023, 1, 1, 2, "hot", 5)));

        // Test invalid empty name
        assertFalse(w.add(new Balloon("", 0, 0, 0, 10, 10, 10, "hot", 5)));

        // Test valid Bird with number = 1 
        assertTrue(w.add(new Bird("bird1", 10, 10, 10, 10, 10, 10, "Sparrow", 1)));

        // Test valid Drone with numEngines = 1
        assertTrue(w.add(new Drone("drone1", 20, 20, 20, 10, 10, 10, "DJI", 1)));

        // Test valid AirPlane with flightNum = 1 and numEngines = 1
        assertTrue(w.add(new AirPlane("plane1", 30, 30, 30, 10, 10, 10, "Delta", 1, 1)));

        // Test valid Balloon with ascentRate = 0
        assertTrue(w.add(new Balloon("balloon1", 40, 40, 40, 10, 10, 10, "hot", 0)));

        // Test valid Rocket with ascentRate = 0 and trajectory = 0
        assertTrue(w.add(new Rocket("rocket1", 50, 50, 50, 10, 10, 10, 0, 0)));

        // Test Rocket with ascentRate = 0 
        assertTrue(w.add(new Rocket("rocket2", 60, 60, 60, 10, 10, 10, 0, 5.0)));

        // Test Rocket with trajectory = 0 
        assertTrue(w.add(new Rocket("rocket3", 70, 70, 70, 10, 10, 10, 100, 0)));

        // Test each origin at 0 
        assertTrue(w.add(new Balloon("ox0", 0, 500, 500, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("oy0", 500, 0, 500, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("oz0", 500, 500, 0, 10, 10, 10, "hot", 5)));

        // Test each origin at 1023 
        assertTrue(w.add(new Balloon("ox1023", 1023, 500, 500, 1, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("oy1023", 500, 1023, 500, 10, 1, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("oz1023", 500, 500, 1023, 10, 10, 1, "hot", 5)));

        // Test each width at 1 
        assertTrue(w.add(new Balloon("xw1", 600, 600, 600, 1, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("yw1", 610, 610, 610, 10, 1, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("zw1", 620, 620, 620, 10, 10, 1, "hot", 5)));

        // Test each width at 1024 
        assertTrue(w.add(new Balloon("xw1024", 0, 700, 700, 1024, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("yw1024", 700, 0, 700, 10, 1024, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("zw1024", 700, 700, 0, 10, 10, 1024, "hot", 5)));

        // Test each origin + width = 1024 
        assertTrue(w.add(new Balloon("xsum1024", 1014, 800, 800, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("ysum1024", 800, 1014, 800, 10, 10, 10, "hot", 5)));
        assertTrue(w.add(new Balloon("zsum1024", 800, 800, 1014, 10, 10, 10, "hot", 5)));
    }


    // ----------------------------------------------------------
    /**
     * Test empty Check various returns from commands on empty database
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
                w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n",
            w.rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n",
            w.collisions());
        assertFuzzyEquals(
            "The following objects intersect (1, 1, 1, 1, 1, 1)\n" +
                "1 nodes were visited in the bintree\n",
                w.intersect(1, 1, 1, 1, 1, 1));
    }


    // ----------------------------------------------------------
    /**
     * Test WorldDB with non-null Random and valid operations.
     *
     * @throws Exception
     */
    public void testWorldDBOperations() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(12345);
        WorldDB w = new WorldDB(rnd);
        assertNotNull(w);

        Balloon b = new Balloon("test1", 10, 10, 10, 10, 10, 10, "hot", 5);
        assertTrue(w.add(b));

        assertFuzzyEquals("Balloon test1 10 10 10 10 10 10 hot 5", w.print("test1"));

        String range = w.rangeprint("a", "z");
        assertTrue(range.contains("test1"));

        assertFuzzyEquals("Balloon test1 10 10 10 10 10 10 hot 5", w.delete("test1"));
    }


    // ----------------------------------------------------------
    /**
     * Test EmptyNode count increments.
     *
     * @throws Exception
     */
    public void testEmptyNodeAndValidation() throws Exception {
        WorldDB w = new WorldDB(null);
        String dump = w.printbintree();
        assertTrue(dump.contains("1 Bintree nodes printed"));
        String intersect = w.intersect(0, 0, 0, 10, 10, 10);
        assertTrue(intersect.contains("1 nodes were visited"));
        Random rnd = new Random();
        rnd.setSeed(12345);
        WorldDB w2 = new WorldDB(rnd);
        assertTrue(w2.add(new Balloon("b1", 100, 100, 100, 10, 10, 10, "hot", 5)));
        String dump2 = w2.printbintree();
        assertTrue(dump2.contains("1 Bintree nodes printed"));
        String intersect2 = w2.intersect(100, 100, 100, 10, 10, 10);
        assertTrue(intersect2.contains("1 nodes were visited"));
    }
}