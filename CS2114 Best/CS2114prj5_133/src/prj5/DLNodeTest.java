// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Anh Truong (anht)

package prj5;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * test class for DLNode
 * 
 * @author ANH
 * @version Apr 21, 2025
 */
public class DLNodeTest
    extends TestCase
{
    // ~ Fields ................................................................
    private DLNode<String> node;
    private DLNode<String> node1;

    // ~ Set up ..........................................................
    /**
     * initializes DLNode
     */
    public void setUp()
    {
        node = new DLNode<String>(null);
        node1 = new DLNode<String>(
            new DLNode<String>("A"),
            "B",
            new DLNode<String>("C"));
    }


    // ~Public Methods ........................................................
    /**
     * test getData() method
     */
    public void testGetData()
    {
        assertNull(node.getData());
        assertEquals("B", node1.getData());
    }


    // ----------------------------------------------------------
    /**
     * tests setData() method
     */
    public void testSetData()
    {
        node.setData("B");
        assertEquals("B", node.getData());
    }


    // ----------------------------------------------------------
    /**
     * tests getNext() method
     */
    public void testGetNext()
    {
        assertEquals("C", (node1.getNext()).getData());
    }


    // ----------------------------------------------------------
    /**
     * tests setNext() method
     */
    public void testSetNext()
    {
        node.setNext(new DLNode<String>("B"));
        assertEquals("B", (node.getNext()).getData());
    }


    // ----------------------------------------------------------
    /**
     * tests getPrevious() method
     */
    public void testGetPrevious()
    {
        assertEquals("A", (node1.getPrevious()).getData());
    }


    // ----------------------------------------------------------
    /**
     * tests setPrevious() method
     */
    public void testSetPrevious()
    {
        node.setPrevious(new DLNode<String>("A0"));
        assertEquals("A0", (node.getPrevious()).getData());
    }
}
