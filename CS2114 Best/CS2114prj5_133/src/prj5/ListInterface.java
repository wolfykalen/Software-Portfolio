// Project 5 2025 Spring
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- KalenDaco _____

package prj5;

/**
 * Interface for list operations.
 * 
 * @param <T>
 *            the type of elements stored in this list
 * @author kalendaco
 * @version Apr 22, 2025
 */
public interface ListInterface<T>
{
    /**
     * Checks if there is at least one element in the list.
     * 
     * @return true if the list is not empty, false otherwise
     */
    boolean hasNext();


    /**
     * Removes and returns the last element from this list.
     * 
     * @return the last element from this list, or null if this list is empty
     */
    T remove();


    /**
     * Removes all elements from this list.
     */
    void clear();


    /**
     * Checks if this list contains no elements.
     * 
     * @return true if this list contains no elements
     */
    boolean isEmpty();
}
