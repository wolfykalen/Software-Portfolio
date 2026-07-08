// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of 
//those who do.
// -- Your name kalendaco)
package dailymixes;

import queue.QueueInterface;
import queue.EmptyQueueException;

/**
 * A circular array implementation of a queue data structure.
 * This implementation allows for dynamic resizing when needed.
 * 
 * @param <T> The type of elements stored in the queue
 * 
 *  @author kalendaco
 *  @version Apr 3, 2025
 */
public class ArrayQueue<T> 
    implements QueueInterface<T> 
{
    /**
     * Sets the default capacity 
     */
    public static final int DEFAULT_CAPACITY = 20;
    private T[] queue;
    private int size;
    private int enqueueIndex;
    private int dequeueIndex;

    /**
     * Creates a new ArrayQueue with default capacity.
     */
    public ArrayQueue() 
    {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a new ArrayQueue with the specified capacity.
     * 
     * @param capacity the initial capacity of the queue
     * @throws IllegalArgumentException if capacity is less than 0
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) 
    {
        if (capacity < 0) 
        {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        queue = (T[]) new Object[capacity + 1]; 
        size = 0;
        enqueueIndex = 0;
        dequeueIndex = 0;
    }

    /**
     * Gets the number of elements currently in the queue.
     * 
     * @return the size of the queue
     */
    public int getSize() 
    {
        return size;
    }

    /**
     * Gets the length of the underlying array.
     * one more than the actual capacity maintains one empty slot.
     * 
     * @return the length of the underlying array
     */
    public int getLengthOfUnderlyingArray() 
    {
        return queue.length;
    }

    /**
     * Checks if the queue is full.
     * 
     * @return true if the queue is full
     */
    public boolean isFull() 
    {
        return size == queue.length - 1;
    }

    /**
     * Ensures there is enough capacity in the queue to add a new element.
     * If necessary creates a new array with double the current capacity.
     */
    private void ensureCapacity() 
    {
        if (isFull()) 
        {

            @SuppressWarnings("unchecked")
            T[] newQueue = (T[]) new Object[queue.length * 2 + 1]; 
            int i = dequeueIndex;
            for (int j = 0; j < size; j++) 
            {
                newQueue[j] = queue[i];
                i = incrementIndex(i);
            }
            queue = newQueue;
            dequeueIndex = 0;
            enqueueIndex = size;
        }
    }

    /**
     * Increments the given index 
     * 
     * @param index the index to increment
     * @return the incremented index
     */
    private int incrementIndex(int index) 
    {
        return (index + 1) % queue.length;
    }

    /**
     * Clears the queue
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() 
    {
        queue = (T[]) new Object[DEFAULT_CAPACITY + 1]; 
        size = 0;
        enqueueIndex = 0;
        dequeueIndex = 0;
    }

    /**
     * Returns an array containing all elements in the queue in FIFO order.
     * 
     * @return an array of the queue's elements
     * @throws EmptyQueueException if the queue is empty
     */
    public Object[] toArray() 
    {
        if (size == 0) 
        {
            throw new EmptyQueueException();
        }
        Object[] array = new Object[size];
        int i = dequeueIndex;
        for (int j = 0; j < size; j++) 
        {
            array[j] = queue[i];
            i = incrementIndex(i);
        }
        return array;
    }

    /**
     * Returns a string representation of the queue.
     * Elements are shown in FIFO order 
     * 
     * @return a string representation of the queue
     */
    @Override
    public String toString() 
    {
        if (size == 0) 
        {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        int i = dequeueIndex;
        for (int j = 0; j < size; j++) 
        {
            if (j > 0) 
            {
                sb.append(", ");
            }
            sb.append(queue[i]);
            i = incrementIndex(i);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Compares this queue with another for equality.
     * 
     * @param obj the object to compare with
     * @return true if the queues are equal
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) 
        {
            return false;
        }
        ArrayQueue<?> other = (ArrayQueue<?>) obj;
        if (size != other.size) 
        {
            return false;
        }

        int i = dequeueIndex;
        int j = other.dequeueIndex;
        for (int k = 0; k < size; k++) 
        {
            if (!queue[i].equals(other.queue[j])) 
            {
                return false;
            }
            i = incrementIndex(i);
            j = other.incrementIndex(j);
        }
        return true;
    }

    /**
     * Adds an element to the end of the queue.
     * 
     * @param newEntry the element to add
     * @throws IllegalArgumentException if newEntry is null
     */
    @Override
    public void enqueue(T newEntry) 
    {
        if (newEntry == null) 
        {
            throw new IllegalArgumentException("Cannot enqueue null element");
        }
        ensureCapacity();
        queue[enqueueIndex] = newEntry;
        enqueueIndex = incrementIndex(enqueueIndex);
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return the element that was removed
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public T dequeue() 
    {
        if (size == 0) 
        {
            throw new EmptyQueueException();
        }
        T front = queue[dequeueIndex];
        queue[dequeueIndex] = null;
        dequeueIndex = incrementIndex(dequeueIndex);
        size--;
        return front;
    }

    /**
     * Gets the element at the front of the queue without removing it.
     * 
     * @return the element at the front
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public T getFront() 
    {
        if (size == 0) 
        {
            throw new EmptyQueueException();
        }
        return queue[dequeueIndex];
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return true if the queue is empty
     */
    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }
}