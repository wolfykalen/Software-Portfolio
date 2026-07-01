// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- KalenDaco _____

package prj5;

import java.util.*;

/**
 * A doubly-linked list that maintains a head and tail node. allows for
 * efficient addition and removal of elements from both ends of the list
 * 
 * @param <T>
 *            the type of elements stored in this list
 * @author kalendaco
 * @version Apr 22, 2025
 */
public class DoublyLinkedList<T>
    implements Iterable<T>
{
    private prj5.DLNode<T> headNode;
    private prj5.DLNode<T> tailNode;
    private int size;

    /**
     * Creates an empty doubly-linked list.
     */
    public DoublyLinkedList()
    {
        headNode = null;
        tailNode = null;
        size = 0;
    }


    /**
     * Checks if there is at least one element in the list.
     * 
     * @return true if the list is not empty, false otherwise
     */
    public boolean hasNext()
    {
        return headNode != null;
    }


    /**
     * Adds a new element to the end of the list.
     * 
     * @param data
     *            the element to add to the list
     * @return true as specified by the Collection.add() contract
     */
    public boolean add(T data)
    {
        prj5.DLNode<T> newNode = new prj5.DLNode<>(data);
        if (headNode == null)
        {
            headNode = newNode;
            tailNode = newNode;
        }
        else
        {
            tailNode.setNext(newNode);
            newNode.setPrevious(tailNode);
            tailNode = newNode;
        }
        size++;
        return true;
    }


    /**
     * Removes all elements from this list.
     */
    public void clear()
    {
        headNode = null;
        tailNode = null;
        size = 0;
    }


    /**
     * Gets the head node of the list.
     * 
     * @return the head node
     */
    public DLNode<T> getHead()
    {
        return headNode;
    }


    /**
     * Gets the tail node of the list.
     * 
     * @return the tail node
     */
    public DLNode<T> getTail()
    {
        return tailNode;
    }


    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list
     */
    public int size()
    {
        return size;
    }


    /**
     * Checks if this list contains no elements.
     * 
     * @return true if this list contains no elements
     */
    public boolean isEmpty()
    {
        return size == 0;
    }


    /**
     * Removes and returns the last element from this list.
     * 
     * @return the last element from this list, or null if this list is empty
     */
    public T remove()
    {
        if (isEmpty())
        {
            return null;
        }
        T data = tailNode.getData();
        if (headNode == tailNode)
        {
            headNode = null;
            tailNode = null;
        }
        else
        {
            tailNode = tailNode.getPrevious();
            tailNode.setNext(null);
        }
        size--;
        return data;
    }


    /**
     * Returns a string representation of this list.
     * 
     * @return a string representation of this list
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        prj5.DLNode<T> current = headNode;
        while (current != null)
        {
            sb.append(current.getData());
            if (current.getNext() != null)
            {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        return sb.toString();
    }


    /**
     * Sorts this list using the comparator.
     * 
     * @param comparator
     *            the comparator to determine the order of the list
     */
    public void sort(Comparator<T> comparator)
    {
        if (headNode == null || headNode.getNext() == null)
        {
            return;
        }

        boolean swapped;
        do
        {
            swapped = false;
            prj5.DLNode<T> current = headNode;
            while (current != null && current.getNext() != null)
            {
                if (comparator.compare(
                    current.getData(),
                    current.getNext().getData()) > 0)
                {
                    prj5.DLNode<T> next = current.getNext();
                    prj5.DLNode<T> nextNext = next.getNext();
                    prj5.DLNode<T> prev = current.getPrevious();

                    next.setPrevious(current.getPrevious());
                    current.setNext(nextNext);
                    if (nextNext != null)
                    {
                        nextNext.setPrevious(current);
                    }
                    if (prev != null)
                    {
                        prev.setNext(next);
                    }
                    else
                    {
                        headNode = next;
                    }
                    current.setPrevious(next);
                    next.setNext(current);

                    if (current == tailNode)
                    {
                        tailNode = next;
                    }

                    swapped = true;
                }
                current = current.getNext();
            }
        }
        while (swapped);
    }


    /**
     * Sorts an array of by reach engagement rate in descending order.
     * 
     * @param dataArray
     *            the array of Data objects to sort
     * @return a new array containing the sorted Data objects
     */
    public Data[] sortReachEngRate(Data[] dataArray)
    {
        if (dataArray == null || dataArray.length <= 1)
        {
            return dataArray;
        }

        DoublyLinkedList<Data> list = new DoublyLinkedList<>();
        for (Data data : dataArray)
        {
            list.add(data);
        }

        list.sort(new ComparatorReachEngRate());

        Data[] sortedArray = new Data[dataArray.length];
        prj5.DLNode<Data> current = list.headNode;
        int index = 0;
        while (current != null)
        {
            sortedArray[index++] = current.getData();
            current = current.getNext();
        }
        return sortedArray;
    }


    /**
     * Sorts an array of by traditional engagement rate in descending order.
     * 
     * @param dataArray
     *            the array of Data objects to sort
     * @return a new array containing the sorted Data objects
     */
    public Data[] sortTraditionalEngRate(Data[] dataArray)
    {
        if (dataArray == null || dataArray.length <= 1)
        {
            return dataArray;
        }

        DoublyLinkedList<Data> list = new DoublyLinkedList<>();
        for (Data data : dataArray)
        {
            list.add(data);
        }

        list.sort(new ComparatorTradEngRate());

        Data[] sortedArray = new Data[dataArray.length];
        prj5.DLNode<Data> current = list.headNode;
        int index = 0;
        while (current != null)
        {
            sortedArray[index++] = current.getData();
            current = current.getNext();
        }
        return sortedArray;
    }


    /**
     * Sorts an array of Data objects by channel name in ascending order.
     * 
     * @param dataArray
     *            the array of Data objects to sort
     * @return a new array containing the sorted Data objects
     */
    public Data[] sortName(Data[] dataArray)
    {
        if (dataArray == null || dataArray.length <= 1)
        {
            return dataArray;
        }

        DoublyLinkedList<Data> list = new DoublyLinkedList<>();
        for (Data data : dataArray)
        {
            list.add(data);
        }

        list.sort(new ComparatorName());

        Data[] sortedArray = new Data[dataArray.length];
        prj5.DLNode<Data> current = list.headNode;
        int index = 0;
        while (current != null)
        {
            sortedArray[index++] = current.getData();
            current = current.getNext();
        }
        return sortedArray;
    }


    /**
     * NEED TO COMMENT
     */
    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>() {
            private DLNode<T> current = headNode;

            @Override
            public boolean hasNext()
            {
                return current != null;
            }


            @Override
            public T next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                }
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }
}
