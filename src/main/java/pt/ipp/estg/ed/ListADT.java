package pt.ipp.estg.ed;

import java.util.Iterator;

/**
 * A list is a linear abstract data type that is more flexible than the stack and the queue 
 * meaning that you can add, remove and search elements on the list without restrictions. <p>
 * There will be two base types of lists implemented: <p>
 * <ul>
 *  <li>Ordered</li>
 *  <li>Unordered</li>
 * </ul>
 * For this interface, it's only specified the common operations between both lists leaving the 
 * unique methods for the ordered and the unordered lists in the 
 * {@code OrderedListADT} and {@code UnorderedListADT} respectively
 * 
 * The common operations for a list include: <p>
 * <ul>
 *  <li>{@link #removeFirst()}: Removes an element from the front of this list</li>
 *  <li>{@link #removeLast()}: Removes an element from the rear of this list</li>
 *  <li>{@link #remove(Object)}: Removes a specified element from this list</li>
 *  <li>{@link #first()}: Returns the element from the front of this list</li>
 *  <li>{@link #last()}: Retrusn the element from the rear of this list</li>
 *  <li>{@link #contains(Object)}: Checks if the list contains a specified element</li>
 *  <li>{@link #isEmpty()}: Checks if this list is empty</li>
 *  <li>{@link #size()}: Returns the number of elements present in this list</li>
 *  <li>{@link #iterator()}: Returns an iterator over the elements in this list</li>
 *  <li>{@link #toString()}: Returns a string representation of this list</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>ListADT</h3>
 * @param <T> the type of elements in this list
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see StackADT
 * @see QueueADT
 * @see OrderedListADT
 * @see UnorderedListADT
 * @see Iterable
 * @see Iterator
 */
public interface ListADT<T> extends Iterable<T> {
    /**
     * Removes an element from the front of this list.
     * 
     * @return the element from the front of this list
     */
    T removeFirst();

    /**
     * Removes an element from the rear of this list.
     * 
     * @return the element from the rear of this list
     */
    T removeLast();

    /**
     * Removes a specified element from this list.
     * 
     * @param target the target to remove
     * @return the removed element
     */
    T remove(T target);

    /**
     * Returns the element from the front of this list.
     * 
     * @return the element from the front of this list
     */
    T first();

    /**
     * Returns the element from the rear of this list.
     * 
     * @return the element from the rear of this list
     */
    T last();

    /**
     * Checks if this list contains a specified element.
     * 
     * @param target the target to search
     * @return true if this list contains the target, false otherwise
     */
    boolean contains(T target);

    /**
     * Checks if this list is empty.
     * 
     * @return true if this list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements present in this list.
     * 
     * @return the number of elements present in this list
     */
    int size();

    /**
     * Returns an iterator over the elements in this list.
     * 
     * @return an iterator over the elements in this list
     */
    @Override
    Iterator<T> iterator();

    /**
     * Returns a string representation of this list.
     * 
     * @return a string representation of this list
     */
    @Override
    String toString();
}
