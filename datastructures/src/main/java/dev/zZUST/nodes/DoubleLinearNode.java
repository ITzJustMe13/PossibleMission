package dev.zZUST.nodes;

/**
 * A double linear node is an auxiliary object used to implement a double linked abstract data type. 
 * It contains an {@link #element}, a pointer to the {@link #prev} double linear node and 
 * a pointer to the {@link #next} double linear node. <p>
 * 
 * The available constructors are the following: <p>
 * <ul>
 *  <li>{@link #DoubleLinearNode()}: Constructs an empty double linear node</li>
 *  <li>{@link #DoubleLinearNode(Object)}: Constructs a double linear node with an element</li>
 *  <li>
 *      {@link #DoubleLinearNode(Object, DoubleLinearNode, DoubleLinearNode)}: Constructs a double linear node with an element, 
 *      a reference to the prev double linear node and a reference to the next double linear node
 *  </li>
 * </ul>
 * 
 * The operations for this {@code DoubleLinearNode} include: <p>
 * <ul>
 *  <li>{@link #getPrev()}: Returns the reference stored in this double linear node prev pointer</li>
 *  <li>{@link #setPrev(DoubleLinearNode)}: Sets the reference stored in this double linear node prev pointer</li>
 *  <li>{@link #getNext()}: Returns the reference stored in this double linear node next pointer</li>
 *  <li>{@link #setNext(DoubleLinearNode)}: Sets the reference stored in this double linear node next pointer</li>
 *  <li>{@link #hashCode()}: Returns the hash code value for this double linear node</li>
 *  <li>{@link #equals(Object)}: Compares this double linear node with the specified object for equality</li>
 * </ul>
 * 
 * <h3>DoubleLinearNode</h3>
 * @param <T> the type of element stored in this double linear node
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see LinearNode
 */
public class DoubleLinearNode<T> extends LinearNode<T> {
    /**
     * The pointer to the prev double linear node
     */
    protected DoubleLinearNode<T> prev;

    /**
     * Constructs an empty double linear node.
     */
    public DoubleLinearNode() {
        this(null, null, null);
    }

    /**
     * Constructs a double linear node with an element.
     * 
     * @param element the element to be stored in this double linear node
     */
    public DoubleLinearNode(T element) {
        this(element, null, null);
    }

    /**
     * Constrcuts a double linear node with an element, a reference to the prev double linear node 
     * and a reference to the next double linear node.
     * 
     * @param element the element to be stored in this double linear node
     * @param prev the reference of the prev double linear node
     * @param next the reference of the next double linear node
     */
    public DoubleLinearNode(T element, DoubleLinearNode<T> prev, DoubleLinearNode<T> next) {
        super(element, next);
    }

    /**
     * Returns the reference stored in this double linear node prev pointer.
     * 
     * @return the reference stored in this double linear node prev pointer
     */
    public DoubleLinearNode<T> getPrev() {
        return prev;
    }

    /**
     * Sets the reference stored in this double linear node prev pointer.
     * 
     * @param prev the reference to be stored in this double linear node prev pointer
     */
    public void setPrev(DoubleLinearNode<T> prev) {
        this.prev = prev;
    }

    /**
     * Returns the reference stored in this double linear node next pointer.
     * 
     * @return the reference stored in this double linear node next pointer
     */
    public DoubleLinearNode<T> getNext() {
        return (DoubleLinearNode<T>) super.getNext();
    }

    /**
     * Sets the reference stored in this double linear node next pointer.
     * 
     * @param prev the reference to be stored in this double linear node next pointer
     */
    public void setNext(DoubleLinearNode<T> next) {
        super.setNext(next);
    }

    /**
     * Returns the hash code value for this double linear node.
     * The hash code is calculated based on the element, the prev double linear node 
     * and the next double linear node of this double linear node.
     * 
     * @return the hash code value for this double linear node
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = prime * result + ((prev == null) ? 0 : prev.hashCode());

        return result;
    }

    /**
     * Compares this double linear node with the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the specified object is equal to this double linear node, false otherwise
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj) || getClass() != obj.getClass()) return false;

        DoubleLinearNode<T> other = (DoubleLinearNode<T>) obj;
        if (prev == null && other.prev != null || prev != null && !prev.equals(other.prev)) return false;
        
        return true;
    }
}
