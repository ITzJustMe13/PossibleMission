package pt.ipp.estg.ed;

/**
 * An unordered list is a linear abstract data type that allows the addition in which
 * the order of it's elements is determined by the end user. <p>
 * 
 * The common operations for an unordered list include: <p>
 * <ul>
 *  <li>{@link #addToFront(Object)}: Adds an element to the front of this unordered list</li>
 *  <li>{@link #addToRear(Object)}: Adds an element to the rear of this unordered list</li>
 *  <li>{@link #addAfter(Object, Object)}: Adds an element after a certain target in this unordered list</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>UnorderedListADT</h3>
 * @param <T> the type of elements in this unordered list
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see ListADT
 */
public interface UnorderedListADT<T> extends ListADT<T> {
    /**
     * Adds an element to the front of this unordered list.
     * 
     * @param element the element to be added to the front of this unordered list
     */
    void addToFront(T element);

    /**
     * Adds an element to the rear of this unordered list.
     * 
     * @param element the element to be added to the rear of this unordered list
     */
    void addToRear(T element);

    /**
     * Adds an element after a certain target in this unordered list.
     * 
     * @param element the element to be added after the target
     * @param target the target to search
     */
    void addAfter(T element, T target);
}
