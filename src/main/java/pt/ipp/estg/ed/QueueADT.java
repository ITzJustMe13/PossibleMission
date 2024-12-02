package pt.ipp.estg.ed;

/**
 * A queue is a linear abstract data type that follows the First In, First Out (FIFO) principle 
 * meaning that the first element added to this queue will be the first one to be removed from it. <p>
 * 
 * The common operations for a queue include: <p>
 * <ul>
 *  <li>{@link #enqueue(Object)}: Adds an element to the rear of this queue</li>
 *  <li>{@link #dequeue()}: Removes an element from the front of this queue</li>
 *  <li>{@link #first()}: Returns the element from the front of this queue</li>
 *  <li>{@link #isEmpty()}: Checks if this queue is empty</li>
 *  <li>{@link #size()}: Returns the number of elements present in this queue</li>
 *  <li>{@link #toString()}: Returns a string representation of this queue</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>QueueADT</h3>
 * @param <T> the type of elements in this queue
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 */
public interface QueueADT<T> {
    /**
     * Adds an element to the rear of this queue.
     * 
     * @param element the element to be added
     */
    void enqueue(T element);

    /**
     * Removes an element from the front of this queue.
     * 
     * @return the element from the front of this queue
     */
    T dequeue();

    /**
     * Returns the element from the front of this queue
     * 
     * @return the element from the front of this queue
     */
    T first();

    /**
     * Checks if this queue is empty.
     * 
     * @return true if this queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements present in this queue.
     * 
     * @return the number of elements present in this queue
     */
    int size();

    /**
     * Returns a string representation of this queue.
     * 
     * @return a string representation of this queue
     */
    @Override
    String toString();
}
