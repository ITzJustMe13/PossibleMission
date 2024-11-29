package dev.zZUST.nodes;

/**
 * A binary tree node is an auxiliary object used in a linked implementation of a binary tree abstract data type.
 * It contains an {@link #element}, a pointer to the {@link #left} and {@link #right} children 
 * of this binary tree node. <p>
 * 
 * The available constructors are the following: <p>
 * <ul>
 *  <li>{@link #BinaryTreeNode()}: Constructs an empty binary tree node</li>
 *  <li>{@link #BinaryTreeNode(Object)}: Constructs a binary tree node with an element</li>
 *  <li>
 *      {@link #BinaryTreeNode(Object, BinaryTreeNode, BinaryTreeNode)}: 
 *      Constrcuts a binary tree node with an element, a reference to the left and right children of this binary tree node
 *  </li>
 * </ul>
 * 
 * The operations for this {@code BinaryTreeNode} include: <p>
 * <ul>
 *  <li>{@link #getElement()}: Returns the element stored in this binary tree node</li>
 *  <li>{@link #setElement(Object)}: Sets the element stored in this binary tree node</li>
 *  <li>{@link #getLeft()}: Returns the reference stored in this binary tree node left pointer</li>
 *  <li>{@link #setLeft(BinaryTreeNode)}: Sets the reference stored in this binary tree node left pointer</li>
 *  <li>{@link #getRight()}: Returns the reference stored in this binary tree node right pointer</li>
 *  <li>{@link #setRight(BinaryTreeNode)}: Sets the reference stored in this binary tree node right pointer</li>
 *  <li>{@link #getNumChildren()}: Returns the number of children of this binary tree node</li>
 *  <li>{@link #hashCode()}: Returns the hash code value for this binary tree node</li>
 *  <li>{@link #equals(Object)}: Compares this binary tree node with the specified object for equality</li>
 *  <li>{@link #toString()}: Returns a string representation of this binary tree node</li>
 * </ul>
 * 
 * <h3>BinaryTreeNode</h3>
 * @param <T> the type of element stored in this binary tree node
 * @since 1.0
 * @version 1.0
 * @author zZUST
 */
public class BinaryTreeNode<T> {
    /**
     * The prime number used to create an hash code for this binary tree node and all it's subclasses
     */
    protected static final int prime = 31;
    /**
     * The element stored in this binary tree node
     */
    protected T element;
    /**
     * A pointer to the left child of this binary tree node
     */
    protected BinaryTreeNode<T> left;
    /**
     * A pointer to the right child of this binary tree node
     */
    protected BinaryTreeNode<T> right;

    /**
     * Constructs an empty binary tree node.
     */
    public BinaryTreeNode() {
        this(null, null, null);
    }

    /**
     * Constructs a binary tree node with an element.
     * 
     * @param element the element to be stored in this binary tree node
     */
    public BinaryTreeNode(T element) {
        this(element, null, null);
    }

    /**
     * Constrcuts a binary tree node with an element, a reference to the left and right 
     * children of this binary tree node.
     * 
     * @param element the element to be stored in this binary tree node
     * @param left the reference to the left child of this binary tree node
     * @param right the reference to the right child of this binary tree node
     */
    public BinaryTreeNode(T element, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        setElement(element);
        setLeft(left);
        setRight(right);
    }

    /**
     * Returns the element stored in this binary tree node.
     * 
     * @return the element stored in this binary tree node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this binary tree node.
     * 
     * @param element the element to be stored in this binary tree node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the reference stored in this binary tree node left pointer.
     * 
     * @return the reference stored in this binary tree node left pointer
     */
    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the reference stored in this binary tree node left pointer.
     * 
     * @param left the reference to be stored in this binary tree node left pointer
     */
    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    /**
     * Returns the reference stored in this binary tree node right pointer.
     * 
     * @return the reference stored in this binary tree node right pointer
     */
    public BinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets the reference stored in this binary tree node right pointer.
     * 
     * @param right the reference to be stored in this binary tree node right pointer
     */
    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    /**
     * Returns the number of children of this binary tree node.
     *
     * @return The number of children of this binary tree node.
     */
    public int getNumChildren() {
        int numChildren = 0;
        if (left != null) numChildren = 1 + left.getNumChildren();
        if (right != null) numChildren = numChildren + 1 + right.getNumChildren();

        return numChildren;
    }

    /**
     * Returns the hash code value for this binary tree node.
     * The hash code is calculated based on the element, the left and right children of this binary tree node.
     * 
     * @return the hash code value for this binary tree node
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());

        return result;
    }

    /**
     * Compares this binary tree node with the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the specified object is equal to this binary tree node, false otherwise
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BinaryTreeNode<T> other = (BinaryTreeNode<T>) obj;
        if (element == null && other.element != null || element != null && !element.equals(other.element)) return false;
        if (left == null && other.left != null || left != null && !left.equals(other.left)) return false;
        if (right == null && other.right != null || right != null && !right.equals(other.right)) return false;
        
        return true;
    }

    /**
     * Returns a string representation of this binary tree node.
     * 
     * @return a string representation of this binary tree node
     */
    @Override
    public String toString() {
        return element.toString();
    }
}
