package towerofhanoi;

//Virginia Tech Honor Code Pledge:
//Project 3 Spring 2025
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
//-- Your name (kalendaco)

import stack.StackInterface;
import java.util.EmptyStackException;


// -------------------------------------------------------------------------
/**
 * Represents a linked stack data structure.
 * This class implements the StackInterface and the push, pop, and peek.
 * 
 * @param <T> 
 *      the type of elements stored in the stack
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class LinkedStack<T> implements StackInterface<T> 
{
    private Node topNode;
    private int size;

    // ----------------------------------------------------------
    /**
     * Create a new LinkedStack object.
     */
    public LinkedStack() 
    {
        topNode = null;
        size = 0;
    }

    // ----------------------------------------------------------
    /**
     * Returns the number of elements in the stack.
     * 
     * @return 
     *      the size of the stack
     */
    public int size() 
    {
        return size;
    }

    /**
     * Checks if the stack is empty.
     * 
     * @return 
     *      true if the stack contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    /**
     * Clears the stack by removing all elements.
     * Resets the top node to null and size to zero.
     */
    @Override
    public void clear() 
    {
        topNode = null;
        size = 0;
    }

    /**
     * Pushes a new element onto the top of the stack
     * 
     * @param 
     *      anEntry the element to be added to the stack
     */
    @Override
    public void push(T anEntry) 
    {
        Node newNode = new Node(anEntry, topNode);
        topNode = newNode;
        size++;
    }

    /**
     * Retrieves the top element of the stack without removing it
     * 
     * @return 
     *      the top element of the stack
     * @throws 
     *      EmptyStackException if the stack is empty
     */
    @Override
    public T peek() 
    {
        if (isEmpty()) 
        {
            throw new EmptyStackException();
        }
        return topNode.getData();
    }

    /**
     * Removes and returns the top element of the stack.
     * 
     * @return 
     *      the top element of the stack
     * @throws 
     *      EmptyStackException if the stack is empty
     */
    @Override
    public T pop() 
    {
        if (isEmpty()) 
        {
            throw new EmptyStackException();
        }
        T data = topNode.getData();
        topNode = topNode.getNextNode();
        size--;
        return data;
    }

    /**
     * Returns a string representation of the stack.
     * 
     * @return 
     *      a string representation of the stack
     */
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder("[");
        Node currentNode = topNode;
        while (currentNode != null) 
        {
            sb.append(currentNode.getData());
            if (currentNode.getNextNode() != null) 
            {
                sb.append(", ");
            }
            currentNode = currentNode.getNextNode();
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Represents a node in the linked stack.
     * Each node contains data and a reference to the next node.
     */
    public class Node 
    {
        private T data;
        private Node nextNode;

        // ----------------------------------------------------------
        /**
         * Creates a new Node with the specified data and no next node.
         * @param data the data to store in the node
         */
        public Node(T data) 
        {
            this.data = data;
            this.nextNode = null;
        }

        // ----------------------------------------------------------
        /**
         * Creates a new Node with the specified data and next node
         * @param data the data to store in the node
         * @param nextNode the next node in the list
         */
        public Node(T data, Node nextNode) 
        {
            this.data = data;
            this.nextNode = nextNode;
        }

        // ----------------------------------------------------------
        /**
         * Returns the data stored in this node.
         * @return the data stored in this node
         */
        public T getData() 
        {
            return data;
        }

        // ----------------------------------------------------------
        /**
         * Returns the next node in the list.
         * @return the next node, or null if this is the last node
         */
        public Node getNextNode() 
        {
            return nextNode;
        }

        // ----------------------------------------------------------
        /**
         * Sets the next node in the list. 
         * @param nextNode the next node to set
         */
        public void setNextNode(Node nextNode) 
        {
            this.nextNode = nextNode;
        }
    }
} 

