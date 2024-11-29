package dev.zZUST.nodes;

/**
 * A linear node is an auxiliary object used to implement a single linked abstract data type. 
 * It contains an {@link #element} and a pointer to the {@link #next} linear node. <p>
 * 
 * The available constructors are the following: <p>
 * <ul>
 *  <li>{@link #LinearNode()}: Constructs an empty linear node</li>
 *  <li>{@link #LinearNode(Object)}: Constructs a linear node with an element</li>
 *  <li>{@link #LinearNode(Object, LinearNode)}: Constructs a linear node with an element and a reference to the next linear node</li>
 * </ul>
 * 
 * The operations for this {@code LinearNode} include: <p>
 * <ul>
 *  <li>{@link #getElement()}: Returns the element stored in this linear node</li>
 *  <li>{@link #setElement(Object)}: Sets the element stored in this linear node</li>
 *  <li>{@link #getNext()}: Returns the reference stored in this linear node next pointer</li>
 *  <li>{@link #setNext(LinearNode)}: Sets the reference stored in this linear node next pointer</li>
 *  <li>{@link #hashCode()}: Returns the hash code value for this linear node</li>
 *  <li>{@link #equals(Object)}: Compares this linear node with the specified object for equality</li>
 *  <li>{@link #toString()}: Returns a string representation of this linear node</li>
 * </ul>
 * 
 * <h3>LinearNode</h3>
 * @param <T> the type of element stored in this linear node
 * @since 1.0
 * @version 1.0
 * @author zZUST
 */
public class LinearNode<T> {
    /**
     * The prime number used to create an hash code for this linear node and all it's subclasses
     */
    protected static final int prime = 31;
    /**
     * The element stored in this linear node
     */
    protected T element;
    /**
     * The pointer to the next linear node
     */
    protected LinearNode<T> next;

    /**
     * Constructs an empty linear node.
     */
    public LinearNode() {
        this(null, null);
    }

    /**
     * Constructs a linear node with an element.
     * 
     * @param element the element to be stored in this linear node
     */
    public LinearNode(T element) {
        this(element, null);
    }

    /**
     * Constrcuts a linear node with an element and a reference to the next linear node.
     * 
     * @param element the element to be stored in this linear node
     * @param next the reference of the next linear node
     */
    public LinearNode(T element, LinearNode<T> next) {
        setElement(element);
        setNext(next);
    }

    /**
     * Returns the element stored in this linear node.
     * 
     * @return the element stored in this linear node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this linear node.
     * 
     * @param element the element to be stored in this linear node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the reference stored in this linear node next pointer.
     * 
     * @return the reference stored in this linear node next pointer
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Sets the reference stored in this linear node next pointer.
     * 
     * @param next the reference to be stored in this linear node next pointer
     */
    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the hash code value for this linear node.
     * The hash code is calculated based on the element and next linear node of this linear node.
     * 
     * @return the hash code value for this linear node
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
        result = prime * result + ((next == null) ? 0 : next.hashCode());

        return result;
    }

    /**
     * Compares this linear node with the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the specified object is equal to this linear node, false otherwise
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LinearNode<T> other = (LinearNode<T>) obj;
        if (element == null && other.element != null || element != null && !element.equals(other.element)) return false;
        if (next == null && other.next != null || next != null && !next.equals(other.next)) return false;
        
        return true;
    }

    /**
     * Returns a string representation of this linear node.
     * 
     * @return a string representation of this linear node
     */
    @Override
    public String toString() {
        return element.toString();
    }
}
