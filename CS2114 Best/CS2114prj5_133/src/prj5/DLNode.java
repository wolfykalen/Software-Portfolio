// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Anh Truong (anht)

package prj5;

// -------------------------------------------------------------------------
/**
 * Creates a doubly-linked node object with references to the previous and next
 * node
 * 
 * @author ANH
 * @version Apr 21, 2025
 * @param <T>
 *            generic type. represents the data portion of the node
 */
public class DLNode<T>
{
    // ~ Fields ................................................................
    private T data;
    private DLNode<T> next;
    private DLNode<T> previous;

    // ~ Constructors ..........................................................
    /**
     * creates a DLNode object. Initializes a node with data
     * 
     * @param data
     *            represents the data the node is initialized with
     */
    public DLNode(T data)
    {
        this.data = data;
    }


    // ----------------------------------------------------------
    /**
     * creates a DLNode object. Initializes a node with data, a previous node
     * object and a next node object
     * 
     * @param entry
     *            represents the data the node is initialized with
     * @param prevNode
     *            represents the previous node the object is initizalied with
     * @param nextNode
     *            represents the nextNode node the object is initizalied with
     */
    public DLNode(DLNode<T> prevNode, T entry, DLNode<T> nextNode)
    {
        this(entry);
        this.setNext(nextNode);
        this.setPrevious(prevNode);

    }


    // ~Public Methods ........................................................
    /**
     * gets the data of the current node
     * 
     * @return the data
     */
    public T getData()
    {
        return data;
    }


    // ----------------------------------------------------------
    /**
     * sets the data of the current node
     * 
     * @param data
     *            the data to set
     */
    public void setData(T data)
    {
        this.data = data;
    }


    // ----------------------------------------------------------
    /**
     * gets the next node
     * 
     * @return the next node
     */
    public DLNode<T> getNext()
    {
        return next;
    }


    // ----------------------------------------------------------
    /**
     * sets the next node
     * 
     * @param newNext
     *            the new node to set next
     */
    public void setNext(DLNode<T> newNext)
    {
        next = newNext;
    }


    // ----------------------------------------------------------
    /**
     * gets the previous node
     * 
     * @return the previous node
     */
    public DLNode<T> getPrevious()
    {
        return previous;
    }


    // ----------------------------------------------------------
    /**
     * sets the previous node
     * 
     * @param newPrevious
     *            the new previous node to set
     */
    public void setPrevious(DLNode<T> newPrevious)
    {
        previous = newPrevious;
    }
}
