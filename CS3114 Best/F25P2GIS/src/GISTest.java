import java.io.IOException;
import student.TestCase;

/**
 * @author {Kalendaco}
 * @version {1}
 */
public class GISTest extends TestCase {

    private GIS it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new GISDB();
    }

    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testRefClearInit()
        throws IOException
    {
        assertTrue(it.clear());
    }


    /**
     * Print testing for empty trees
     * @throws IOException
     */
    public void testRefEmptyPrints()
        throws IOException
    {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }


    /**
     * Print bad input checks
     * @throws IOException
     */
    public void testRefBadInput()
        throws IOException
    {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
    }


    /**
     * Insert some records and check output requirements for various commands
     * @throws IOException
     */
    public void testRefOutput()
        throws IOException
    {
        assertTrue(it.insert("Chicago", 100, 150));
        assertTrue(it.insert("Atlanta", 10, 500));
        assertTrue(it.insert("Tacoma", 1000, 100));
        assertTrue(it.insert("Baltimore", 0, 300));
        assertTrue(it.insert("Washington", 5, 350));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1  Atlanta (10, 500)\n"
            + "2    Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n"
            + "3      L (11, 500)\n"
            + "2    L (101, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    Washington (5, 350)\n", it.print());
        assertFuzzyEquals("2    Baltimore (0, 300)\n"
            + "3      Washington (5, 350)\n"
            + "1  Atlanta (10, 500)\n"
            + "2    L (11, 500)\n"
            + "0Chicago (100, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    L (101, 150)\n", it.debug());
        assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
        assertFuzzyEquals("L", it.info(101, 150));
        assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
        assertFuzzyEquals("2\nChicago", it.delete(100, 150));
        assertFuzzyEquals("L (101, 150)\n"
                + "Atlanta (10, 500)\n"
                + "Baltimore (0, 300)\n"
                + "Washington (5, 350)\n"
                + "L (11, 500)\n5", it.search(0, 0, 2000));
        assertFuzzyEquals("Baltimore (0, 300)\n5", it.search(0, 300, 0));
    }
    
    /**
     * Test insertion at boundary coordinates.
     */
    public void testBoundaryCoordinates() {
        assertTrue(it.insert("MinPoint", 0, 0));
        assertTrue(it.insert("MaxPoint", GISDB.MAXCOORD, GISDB.MAXCOORD));
        assertEquals("MinPoint", it.info(0, 0));
        assertEquals("MaxPoint", it.info(GISDB.MAXCOORD, GISDB.MAXCOORD));
    }

    /**
     * Test duplicate coordinate insertion.
     */
    public void testDuplicateCoordinates() {
        assertTrue(it.insert("CityA", 10, 20));
        assertFalse("Should not allow duplicate coordinates", 
            it.insert("CityB", 10, 20));
        assertEquals("CityA", it.info(10, 20));
    }

    /**
     * Test multiple cities with same name.
     */
    public void testMultipleCitiesSameName() {
        assertTrue(it.insert("Twin", 1, 1));
        assertTrue(it.insert("Twin", 2, 2));
        assertTrue(it.insert("Twin", 3, 3));
        
        String result = it.info("Twin");
        assertTrue(result.contains("(1, 1)"));
        assertTrue(result.contains("(2, 2)"));
        assertTrue(result.contains("(3, 3)"));
        
        // Delete all "Twin" cities
        it.delete("Twin");
        assertEquals("", it.info("Twin"));
    }

    /**
     * Test search with radius 0 (should only match exact point).
     */
    public void testSearchZeroRadius() {
        it.insert("Center", 100, 100);
        it.insert("Nearby", 101, 101);
        
        String result = it.search(100, 100, 0);
        assertEquals("Center (100, 100)\n2", result);
    }

    /**
     * Test search with large radius.
     */
    public void testSearchLargeRadius() {
        it.insert("Center", 100, 100);
        it.insert("Near", 101, 101);
        it.insert("Far", 200, 200);
        
        String result = it.search(100, 100, 150);
        assertTrue(result.contains("Center (100, 100)"));
        assertTrue(result.contains("Near (101, 101)"));
        assertTrue(result.contains("Far (200, 200)"));
    }

    /**
     * Test clear operation.
     */
    public void testClear() {
        it.insert("ToClear", 50, 50);
        assertTrue(it.clear());
        assertEquals("", it.info(50, 50));
        assertTrue(it.print().isEmpty());
        assertTrue(it.debug().isEmpty());
    }

    /**
     * Test negative radius search.
     */
    public void testNegativeRadius() {
        assertEquals("", it.search(0, 0, -1));
    }

    /**
     * Test empty tree operations.
     */
    public void testEmptyTreeOperations() {
        assertEquals("", it.print());
        assertEquals("", it.debug());
        assertEquals("", it.info(0, 0));
        assertEquals("", it.info("NoCity"));
        assertEquals("0", it.search(0, 0, 10));
    }

    /**
     * Test insertion with invalid coordinates.
     */
    public void testInvalidCoordinates() {
        assertFalse(it.insert("Invalid", -1, 0));
        assertFalse(it.insert("Invalid", 0, -1));
        assertFalse(it.insert("Invalid", GISDB.MAXCOORD + 1, 0));
        assertFalse(it.insert("Invalid", 0, GISDB.MAXCOORD + 1));
    }

    /**
     * Test deletion by name with multiple matches.
     */
    public void testDeleteMultipleSameName() {
        it.insert("Multi", 1, 1);
        it.insert("Multi", 2, 2);
        it.insert("Multi", 3, 3);
        
        String result = it.delete("Multi");
        assertTrue(result.contains("(1, 1)"));
        assertTrue(result.contains("(2, 2)"));
        assertTrue(result.contains("(3, 3)"));
        
        assertEquals("", it.info("Multi"));
    }

    /**
     * Test deletion by coordinates.
     */
    public void testDeleteByCoordinates() {
        it.insert("ToDelete", 99, 99);
        String result = it.delete(99, 99);
        assertEquals("1\nToDelete", result);
        assertEquals("", it.info(99, 99));
    }

    /**
     * Test search with exact coordinate match.
     */
    public void testSearchExactMatch() {
        it.insert("Exact", 100, 100);
        String result = it.search(100, 100, 0);
        assertEquals("Exact (100, 100)\n1", result);
    }

    /**
     * Test search with no matches.
     */
    public void testSearchNoMatches() {
        it.insert("Lonely", 100, 100);
        String result = it.search(200, 200, 5);
        assertEquals("0", result);
    }
    
    
    /**
     * Tests that inserting a null city throws an IllegalArgumentException.
     */
    public void testInsertNullCity() {
        KDTree tree = new KDTree();
        boolean exceptionThrown = false;
        try 
        {
            tree.insert(null);
        } 
        catch 
        (IllegalArgumentException e) 
        {
            exceptionThrown = true;
        }
        assertTrue("Should throw IllegalArgumentException when "
            + "inserting null city", exceptionThrown);
    }

    /**
     * Tests deleting a city that doesn't exist in the tree.
     */
    public void testDeleteCityNonExistentCity() {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 1, 1));
        
        KDTree.DeleteResult result = tree.delete(99, 99);
        assertNotNull(result);
        assertNull(result.deleted);
        
        assertNotNull(tree.find(1, 1));
    }

    /**
     * Tests deleting from an empty tree.
     */
    public void testDeleteCityEmptyTree() {
        KDTree tree = new KDTree();
        KDTree.DeleteResult result = tree.delete(1, 1);
        assertNotNull(result);
        assertNull(result.deleted);
    }

    /**
     * Tests deleting a city with multiple nodes.
     */
    public void testDeleteCitMultipleNodes() {
        KDTree tree = new KDTree();
        tree.insert(new City("B", 2, 2));
        tree.insert(new City("A", 1, 1));
        tree.insert(new City("C", 3, 3));
        
        KDTree.DeleteResult result = tree.delete(2, 2);
        assertNotNull(result);
        assertEquals("B", result.deleted.getName());
        
        assertNull(tree.find(2, 2));
        assertNotNull(tree.find(1, 1));
        assertNotNull(tree.find(3, 3));
    }
    
    /**
     * Tests city comparison .
     */
    public void testCityComparison() {
        KDTree tree = new KDTree();
        City city1 = new City("A", 1, 2);
        City city2 = new City("A", 1, 2);
        City city3 = new City("B", 1, 2);
        City city4 = new City("A", 2, 2);
        City city5 = new City("A", 1, 3);
        
        tree.insert(new City("A", 5, 5));
        assertFalse("Should not allow duplicate coordinates", 
                   tree.insert(new City("B", 5, 5)));
                   
        tree = new KDTree();
        tree.insert(city1);
        assertNotNull("Should find city with same coordinates", 
                    tree.find(1, 2));
        assertEquals("Found city should match original", 
                    "A", tree.find(1, 2).getName());
        
        tree = new KDTree();
        tree.insert(new City("A", 1, 2));
        assertNull("Should not find city with different coordinates", 
                  tree.find(9, 9));
    }
    
    /**
     * Tests node deletion 
     */
    public void testDeleteNodeScenarios() {
        KDTree tree1 = new KDTree();
        tree1.insert(new City("A", 2, 2));  
        tree1.insert(new City("B", 3, 3));  
        tree1.delete(2, 2);  
        assertNull("Root should be deleted", tree1.find(2, 2));
        assertNotNull("Right child should become new root", tree1.find(3, 3));
        
        KDTree tree2 = new KDTree();
        tree2.insert(new City("B", 2, 2));  
        tree2.insert(new City("A", 1, 1));  
        tree2.delete(2, 2);  
        assertNull("Root should be deleted", tree2.find(2, 2));
        assertNotNull("Left child should become new root", tree2.find(1, 1));
        
        KDTree tree3 = new KDTree();
        tree3.insert(new City("B", 2, 2));  
        tree3.insert(new City("A", 1, 1));  
        tree3.insert(new City("C", 3, 3));  
        tree3.delete(2, 2);  
        assertNull("Root should be deleted", tree3.find(2, 2));
        assertNotNull("Left child should remain", tree3.find(1, 1));
        assertNotNull("Right child should remain", tree3.find(3, 3));
    }

    /**
     * Tests extracting max 
     */
    public void testExtractMaxThroughDeletion() {
        KDTree tree = new KDTree();
        tree.insert(new City("B", 2, 2));
        tree.insert(new City("A", 1, 1));
        tree.insert(new City("D", 4, 4));
        tree.insert(new City("C", 3, 3));
        
        tree.delete(4, 4);
        assertNull("Node D should be deleted", tree.find(4, 4));
        assertNotNull("Other nodes should remain", tree.find(1, 1));
        assertNotNull("Other nodes should remain", tree.find(2, 2));
        assertNotNull("Other nodes should remain", tree.find(3, 3));
    }
    
    /**
     * Test range search with points on the boundary
     */
    public void testRangeSearchBoundary() throws IOException {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 10, 10));
        tree.insert(new City("B", 20, 20));
        tree.insert(new City("C", 30, 30));
        
        KDTree.SearchResult result = tree.rangeSearch(10, 10, 0);
        assertEquals(1, result.cities.size());
        assertEquals("A", result.cities.get(0).getName());
        
        result = tree.rangeSearch(10, 10, 100);
        assertEquals(3, result.cities.size());
    }

    /**
     * Test findMin 
     */
    public void testFindMinThroughDeletion() {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 30, 10));
        tree.insert(new City("B", 20, 20));
        tree.insert(new City("C", 10, 30));
        
        tree.delete(20, 20);
        assertNull(tree.find(20, 20));
        assertNotNull(tree.find(10, 30));
        assertNotNull(tree.find(30, 10));
    }

    /**
     * Test find with various scenarios
     */
    public void testFindScenarios() {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 10, 10));
        tree.insert(new City("B", 20, 20));
        tree.insert(new City("C", 10, 30));  
        
        City found = tree.find(10, 10);
        assertNotNull(found);
        assertEquals("A", found.getName());
        
        assertNull(tree.find(15, 15));
        
        found = tree.find(10, 30);
        assertNotNull(found);
        assertEquals("C", found.getName());
    }

    /**
     * Test delete with different node configurations
     */
    public void testDeleteScenarios() {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 20, 20));  
        tree.insert(new City("B", 10, 10));  
        tree.insert(new City("C", 30, 30));  
        tree.insert(new City("D", 5, 5));    
        
        tree.delete(5, 5);
        assertNull(tree.find(5, 5));
        
        tree.delete(10, 10);
        assertNull(tree.find(10, 10));
        
        tree.delete(20, 20);
        assertNull(tree.find(20, 20));
    }

    /**
     * Test edge cases for range search
     */
    public void testRangeSearchEdgeCases() {
        KDTree tree = new KDTree();
        tree.insert(new City("A", 10, 10));
        tree.insert(new City("B", 20, 20));
        tree.insert(new City("C", 30, 30));
        
        KDTree.SearchResult result = tree.rangeSearch(10, 10, 0);
        assertEquals(1, result.cities.size());
        
        result = tree.rangeSearch(10, 10, 1000);
        assertEquals(3, result.cities.size());
        
        result = tree.rangeSearch(100, 100, 1);
        assertEquals(0, result.cities.size());
    }

    /**
     * Test findMin edge cases through public methods
     */
    public void testFindMinEdgeCases() {
        KDTree tree = new KDTree();
        tree.delete(1, 1);  
        
        tree.insert(new City("A", 10, 10));
        tree.delete(10, 10);  
        assertNull(tree.find(10, 10));
        
        tree.insert(new City("B", 5, 5));
        tree.insert(new City("A", 10, 10));
        tree.delete(5, 5);  
        assertNull(tree.find(5, 5));
        assertNotNull(tree.find(10, 10));
    }
    
    /**
     * Tests that inserting a null city throws an IllegalArgumentException.
     */
    public void testBSTInsertNullCity() {
        BST bst = new BST();
        boolean exceptionThrown = false;
        try {
            bst.insert(null);
        } 
        catch 
        (IllegalArgumentException e) 
        {
            exceptionThrown = true;
        }
        assertTrue("Should throw IllegalArgumentException when "
            + "inserting null city", 
                  exceptionThrown);
    }

    /**
     * Tests deleting a city that doesn't exist in the tree.
     */
    public void testBSTDeleteNonExistentCity() {
        BST bst = new BST();
        City city = new City("Test1", 1, 2);
        bst.insert(city);
        assertFalse("Should return false when deleting non-existent city", 
                   bst.deleteCity(new City("Test2", 3, 4)));
    }

    /**
     * Tests deleting from an empty tree.
     */
    public void testBSTDeleteFromEmptyTree() {
        BST bst = new BST();
        assertFalse("Should return false when deleting from empty tree", 
                   bst.deleteCity(new City("Test", 1, 2)));
    }

    /**
     * Tests node deletion scenarios 
     */
    public void testBSTDeleteNodeScenarios() {
        BST bst = new BST();
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 1, 1));
        bst.deleteCity(new City("B", 2, 2));
        assertFalse("Should have deleted node with only left child", 
                   bst.deleteCity(new City("B", 2, 2)));

        bst = new BST();
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("B", 2, 2));
        bst.deleteCity(new City("A", 1, 1));
        assertFalse("Should have deleted node with only right child", 
                   bst.deleteCity(new City("A", 1, 1)));

        // Test node with both children
        bst = new BST();
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("C", 3, 3));
        bst.deleteCity(new City("B", 2, 2));
        assertFalse("Should have deleted node with both children", 
                   bst.deleteCity(new City("B", 2, 2)));
    }

    /**
     * Tests the extractMax method through deletion.
     */
    public void testBSTExtractMaxThroughDeletion() {
        BST bst = new BST();
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("D", 4, 4));
        bst.insert(new City("C", 3, 3));
        bst.deleteCity(new City("D", 4, 4));
        assertFalse("Should have deleted max node", 
                   bst.deleteCity(new City("D", 4, 4)));
    }

    /**
     * Tests collecting cities by name in preorder.
     */
    public void testBSTCollectPreorderByName() {
        BST bst = new BST();
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 3, 3));
        
        bst.deleteCity(new City("A", 1, 1));
        assertFalse("Should have deleted first A", 
            bst.deleteCity(new City("A", 1, 1)));
        
        bst.deleteCity(new City("A", 3, 3));
        assertFalse("Should have deleted second A", 
            bst.deleteCity(new City("A", 3, 3)));
    }

    /**
     * Tests the inorder formatted output.
     */
    public void testBSTInorderFormatted() {
        BST bst = new BST();
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("C", 3, 3));
        String result = bst.inorderFormatted();
        assertTrue("Should contain city A", result.contains("A (1, 1)"));
        assertTrue("Should contain city B", result.contains("B (2, 2)"));
        assertTrue("Should contain city C", result.contains("C (3, 3)"));
    }

    /**
     * Tests deleting all cities with the same name.
     */
    public void testBSTDeleteAllByName() {
        BST bst = new BST();
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("A", 2, 2));
        bst.insert(new City("B", 3, 3));
        
        bst.deleteAllByName("A");
        assertFalse("Should have deleted first A", 
            bst.deleteCity(new City("A", 1, 1)));
        assertFalse("Should have deleted second A", 
            bst.deleteCity(new City("A", 2, 2)));
        assertTrue("Should not have deleted B", 
            bst.deleteCity(new City("B", 3, 3)));
    }

    /**
     * Tests the repeatSpaces method through inorder formatting.
     */
    public void testBSTRepeatSpaces() {
        BST bst = new BST();
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("B", 2, 2));
        String result = bst.inorderFormatted();
        assertTrue("Should properly format with indentation", 
                  result.contains("1  A (1, 1)") || 
                  result.contains("1  B (2, 2)"));
    }
    
    /**
     * Tests the deleteCity method with null input.
     */
    public void testBSTDeleteCityNullInput() {
        BST bst = new BST();
        boolean exceptionThrown = false;
        try {
            bst.deleteCity(null);
        } 
        catch 
        (IllegalArgumentException e) 
        {
            exceptionThrown = true;
        }
        assertTrue("Should throw IllegalArgumentException when "
            + "deleting null city", exceptionThrown);
    }

    /**
     * Tests deleteAllByName with edge cases.
     */
    public void testBSTDeleteAllByNameEdgeCases() {
        BST bst = new BST();
        bst.deleteAllByName("Nonexistent");
        bst.insert(new City("A", 1, 1));
        bst.deleteAllByName("A");
        assertFalse("Should delete the only city", 
                   bst.deleteCity(new City("A", 1, 1)));
        
        bst = new BST();
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("A", 2, 2));
        bst.insert(new City("B", 3, 3));
        bst.deleteAllByName("A");
        assertFalse("Should delete all A cities", 
                   bst.deleteCity(new City("A", 1, 1)));
        assertFalse("Should delete all A cities", 
                   bst.deleteCity(new City("A", 2, 2)));
        assertTrue("Should not delete B city", 
                  bst.deleteCity(new City("B", 3, 3)));
    }
    /**
     * Tests city comparison through the delete operation.
     */
    public void testBSTCityComparisonThroughDelete() {
        BST bst = new BST();
        City city1 = new City("A", 1, 1);
        bst.insert(city1);
        
        assertTrue("Should delete city with same properties", 
                  bst.deleteCity(new City("A", 1, 1)));
                  
        bst.insert(city1);
        
        assertFalse("Should not delete with different name", 
                   bst.deleteCity(new City("B", 1, 1)));
        
        assertFalse("Should not delete with different x", 
                   bst.deleteCity(new City("A", 2, 1)));
        
        assertFalse("Should not delete with different y", 
                   bst.deleteCity(new City("A", 1, 2)));
    }

    /**
     * Tests indentation through inorder formatted output.
     */
    public void testBSTIndentationInOutput() {
        BST bst = new BST();
        bst.insert(new City("B", 2, 2));
        bst.insert(new City("A", 1, 1));
        bst.insert(new City("C", 3, 3));
        
        String result = bst.inorderFormatted();
        
        assertTrue("Output should contain proper indentation", 
                  result.contains("  ") || 
                  result.contains("\t") ||
                  result.matches(".*\\d+\\s+\\w+.*"));
    }
    
    /**
     * Test edge cases for coordinates near boundaries
     */
    public void testEdgeCaseCoordinates() {
        assertTrue(it.insert("Edge1", 1, 1));
        assertTrue(it.insert("Edge2", 
            GISDB.MAXCOORD - 1, GISDB.MAXCOORD - 1));        
        String result = it.search(0, 0, 2);
        assertTrue(result.contains("Edge1"));
        assertFalse(result.contains("Edge2"));
        
        result = it.search(GISDB.MAXCOORD, GISDB.MAXCOORD, 2);
        assertTrue(result.contains("Edge2"));
        assertFalse(result.contains("Edge1"));
    }

    /**
     * Test specific deletion scenarios including empty tree and tree structure
     */
    public void testDeleteSpecificScenarios() {
        assertTrue(it.clear());
        assertEquals("", it.delete(0, 0));
        
        assertTrue(it.insert("Root", 100, 100));
        assertTrue(it.insert("Left", 50, 50));
        assertTrue(it.insert("Right", 150, 150));
        
        String result = it.delete(100, 100);
        assertEquals("2\nRoot", result);
        
        assertFalse(it.print().contains("Root"));
        assertTrue(it.print().contains("Left"));
        assertTrue(it.print().contains("Right"));
    }
    
    /**
     * Tests arithmetic operations in distance calculations
     */
    public void testArithmeticOperations() {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 200));  
        assertTrue(it.insert("C", 100, 200));  
        assertTrue(it.insert("D", 200, 100));  
        
        String result = it.search(100, 100, 141);  
        assertTrue(result.contains("A"));
        assertFalse(result.contains("B"));
        assertTrue(result.contains("C") || result.contains("D"));  
        
        assertTrue(it.insert("Max", GISDB.MAXCOORD, GISDB.MAXCOORD));
        result = it.search(GISDB.MAXCOORD, GISDB.MAXCOORD, 1);
        assertTrue(result.contains("Max"));
    }

    /**
     * Tests boundary conditions in coordinate comparisons
     */
    public void testBoundaryComparisons() {
        assertTrue(it.clear());
        
        assertTrue(it.insert("Close1", 1000, 1000));
        assertTrue(it.insert("Close2", 1001, 1000));
        assertTrue(it.insert("Close3", 1000, 1001));
        assertTrue(it.insert("Close4", 1001, 1001));
        
        String result = it.search(1000, 1000, 0);
        assertTrue(result.contains("Close1 (1000, 1000)"));
        assertFalse(result.contains("Close2"));
        assertFalse(result.contains("Close3"));
        assertFalse(result.contains("Close4"));
        
        result = it.search(1000, 1000, 2);
        assertTrue(result.contains("Close1"));
        assertTrue(result.contains("Close2"));
        assertTrue(result.contains("Close3"));
        assertTrue(result.contains("Close4"));
        
        assertTrue(it.insert("Origin", 0, 0));
        assertTrue(it.insert("MaxCorner", GISDB.MAXCOORD, GISDB.MAXCOORD));
        
        result = it.search(0, 0, 1);
        assertTrue(result.contains("Origin (0, 0)"));
        
        result = it.search(GISDB.MAXCOORD, GISDB.MAXCOORD, 1);
        assertTrue(result.contains("MaxCorner "
            + "(" + GISDB.MAXCOORD + ", " + GISDB.MAXCOORD + ")"));
    }

    /**
     * Tests edge cases in tree operations
     */
    public void testTreeOperationEdgeCases() {
        assertTrue(it.insert("Single", 500, 500));
        String result = it.delete(500, 500);
        assertEquals("1\nSingle", result);
        
        assertTrue(it.insert("Parent", 100, 100));
        assertTrue(it.insert("Left", 50, 50));
        result = it.delete(100, 100);
        assertEquals("2\nParent", result);
        
        assertTrue(it.clear());
        assertTrue(it.insert("Parent", 100, 100));
        assertTrue(it.insert("Right", 150, 150));
        result = it.delete(100, 100);
        assertEquals("2\nParent", result);
    }
    
    /**
     * Test City constructor validation
     */
    public void testCityConstructorValidation() {
        try 
        {
            new City(null, 10, 20);
            fail("Expected IllegalArgumentException for null name");
        } 
        catch 
        (IllegalArgumentException e) 
        {
            assertNotNull("Exception message should not be null",
                e.getMessage());
            assertTrue("Exception message should indicate the error", 
                      e.getMessage().contains("City name cannot be "
                          + "null or empty"));
        }
        
        try 
        {
            new City("", 10, 20);
            fail("Expected IllegalArgumentException for empty name");
        } 
        catch 
        (IllegalArgumentException e) 
        {
            assertNotNull("Exception message should not be null"
                , e.getMessage());
            assertTrue("Exception message should indicate the error",
                      e.getMessage().contains("City name cannot be "
                          + "null or empty"));
        }
        
        try 
        {
            new City("   ", 10, 20);
            fail("Expected IllegalArgumentException for whitespace name");
        }
        catch 
        (IllegalArgumentException e) 
        {
            assertNotNull("Exception message should "
                + "not be null", e.getMessage());
            assertTrue("Exception message should indicate the error",
                      e.getMessage().contains("City name cannot "
                          + "be null or empty"));
        }
        
        City validCity = new City("ValidCity", 10, 20);
        assertNotNull("City object should be created", validCity);
        assertEquals("City name should match", "ValidCity", 
            validCity.getName());
        assertEquals("X coordinate should match", 10, validCity.getX());
        assertEquals("Y coordinate should match", 20, validCity.getY());
    }
    /**
     * Test delete method return value format and node visit count.
     */
    public void testDeleteReturnValue() {
        it.clear();
        assertTrue(it.insert("City1", 100, 100));
        assertTrue(it.insert("City2", 200, 200));
        
        String result = it.delete(100, 100);
        String[] parts = result.split("\n", 2);
        
        assertEquals("Should have exactly 2 lines", 2, parts.length);
        
        try {
            int visitCount = Integer.parseInt(parts[0]);
            assertTrue("Visit count should be >= 1", visitCount >= 1);
        } 
        catch (NumberFormatException e) 
        {
            fail("First line should be a number (node visit count)");
        }
        
        assertEquals("City name should match", "City1", parts[1]);
        
        assertEquals("", it.info(100, 100));
    }
}
