package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco
import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 * Test class for the Node class within LinkedStack.
 * extends LinkedStack to gain access to the private Node class
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class NodeTest 
    extends LinkedStack<Disk> 
{
    // ----------------------------------------------------------
    /**
     * Tests the Node class functionality.
     * 1. A Node can be created with data
     * 2. The getData method returns the correct data
     * 3. The getNextNode method returns null for a new node
     * 4. The setNextNode method properly sets the next node
     * 5. The getNextNode method returns the correct next node after setting
     */
    @Test
    public void testNode() 
    {
        Disk disk = new Disk(30);
        Node node = new Node(disk);
        assertEquals(disk, node.getData());
        assertNull(node.getNextNode());

        Disk nextDisk = new Disk(20);
        Node nextNode = new Node(nextDisk);
        node.setNextNode(nextNode);
        assertEquals(nextNode, node.getNextNode());
    }
}