package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco
import static org.junit.Assert.*;
import java.util.EmptyStackException;
import org.junit.Test;
// -------------------------------------------------------------------------
/**
 * This tests the LinkedStack class to verify that all methods are 
 * functioning as intended 
 *  @author kalendaco
 *  @version Mar 25, 2025
 */
public class LinkedStackTest 
{

    // ----------------------------------------------------------
    /**
     * Tests the push and pop methods of the LinkedStack.
     */
    @Test
    public void testPushAndPop() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        Disk disk = new Disk(30);
        stack.push(disk);
        assertEquals(disk, stack.pop());
    }

    // ----------------------------------------------------------
    /**
     * Tests the peek method of the LinkedStack.
     */
    @Test
    public void testPeek() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        Disk disk = new Disk(30);
        stack.push(disk);
        assertEquals(disk, stack.peek());
    }

    // ----------------------------------------------------------
    /**
     * Tests the isEmpty method of the LinkedStack.
     */
    @Test
    public void testIsEmpty() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        assertTrue(stack.isEmpty());
        stack.push(new Disk(30));
        assertFalse(stack.isEmpty());
    }

    // ----------------------------------------------------------
    /**
     * Tests the pop method of the LinkedStack when the stack is empty.
     */
    @Test
    public void testPopEmptyStack() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        try 
        {
            stack.pop();
            fail("Expected EmptyStackException to be thrown");
        } 
        catch (EmptyStackException e) 
        {
            // Test passes if this is reached
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the clear method of the LinkedStack.
     */
    @Test
    public void testClear() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        Disk disk = new Disk(30);
        stack.push(disk);
        assertFalse(stack.isEmpty());
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ----------------------------------------------------------
    /**
     * Tests the peek method of the LinkedStack when the stack is empty.
     */
    @Test
    public void testPeekEmptyStack() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        try 
        {
            stack.peek();
            fail("Expected EmptyStackException to be thrown");
        } 
        catch (EmptyStackException e) 
        {
            // Test passes if this is reached
        }
    }

    // ----------------------------------------------------------
    /**
     * Tests the toString method of the LinkedStack.
     */
    @Test
    public void testToString() 
    {
        LinkedStack<Disk> stack = new LinkedStack<>();
        Disk disk1 = new Disk(30);
        Disk disk2 = new Disk(20);
        stack.push(disk1);
        stack.push(disk2);
        assertEquals("[20, 30]", stack.toString());
    }
}

