package pt.ipp.estg.ed;

/**
 * A stack is a linear abstract data type that follows the Last In, First Out (LIFO) principle 
 * meaning that the last element added to this stack will be the first one to be removed from it. <p>
 * 
 * The common operations for a stack include: <p>
 * <ul>
 *  <li>{@link #push(Object)}: Adds an element to the top of this stack</li>
 *  <li>{@link #pop()}: Removes an element from the top of this stack</li>
 *  <li>{@link #peek()}: Returns the element from the top of this stack</li>
 *  <li>{@link #isEmpty()}: Checks if this stack is empty</li>
 *  <li>{@link #size()}: Returns the number of elements present in this stack</li>
 *  <li>{@link #toString()}: Returns a string representation of this stack</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>StackADT</h3>
 * @param <T> the type of elements in this stack
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 */
public interface StackADT<T> {
    /**
     * Adds an element to the top of this stack.
     * 
     * @param element the element to be added
     */
    void push(T element);

    /**
     * Removes an element from the top of this stack.
     * 
     * @return the element from the top of this stack
     */
    T pop();

    /**
     * Returns the element from the top of this stack.
     * 
     * @return the element from the top of this stack
     */
    T peek();

    /**
     * Checks if this stack is empty.
     * 
     * @return true if this stack is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements present in this stack.
     * 
     * @return the number of elements present in this stack
     */
    int size();

    /**
     * Returns a string representation of this stack.
     * 
     * @return a string representation of this stack
     */
    @Override
    String toString();
}
