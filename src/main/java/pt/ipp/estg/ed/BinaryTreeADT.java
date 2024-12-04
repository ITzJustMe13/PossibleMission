package pt.ipp.estg.ed;

import java.util.Iterator;

/**
 * A binary tree is a non-linear abstract data type that, based of the concept of a tree, 
 * every node, with the exception of the root node, has exactly one parent and, at max, 
 * two children.<p>
 * There will be two base types of binary trees implemented: <p>
 * <ul>
 *  <li>Search</li>
 *  <li>Minheap</li>
 * </ul>
 * For this interface, it's only specified the common operations between both binary trees leaving the 
 * unique methods for the search and the min-heap binary trees in the 
 * {@code BinarySearchTreeADT} and {@code HeapADT} respectively
 * 
 * The common operations for a binary tree include: <p>
 * <ul>
 *  <li>{@link #getRoot()}: Returns the element from the root of this binary tree</li>
 *  <li>{@link #find(Object)}: Returns a specified element if it is found in this binary tree</li>
 *  <li>{@link #contains(Object)}: Checks if this binary tree contains a specified element</li>
 *  <li>{@link #isEmpty()}: Checks if this binary tree is empty</li>
 *  <li>{@link #size()}: Returns the number of elements present in this binary tree</li>
 *  <li>{@link #iteratorPreOrder()}: Returns an iterator over the elements in this binary tree by performing a pre-order traversal</li>
 *  <li>{@link #iteratorInOrder()}: Returns an iterator over the elements in this binary tree by performing an in-order traversal</li>
 *  <li>{@link #iteratorPostOrder()}: Returns an iterator over the elements in this binary tree by performing a post-order traversal</li>
 *  <li>{@link #iteratorLevelOrder()}: Returns an iterator over the elements in this binary tree by performing a level-order traversal</li>
 *  <li>{@link #toString()}: Returns a string representation of this binary tree</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>BinaryTreeADT</h3>
 * @param <T> the type of elements in this binary tree
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see BinarySearchTreeADT
 * @see HeapADT
 * @see Iterator
 */
public interface BinaryTreeADT<T> {
    /**
     * Returns the element from the root of this binary tree.
     * 
     * @return the element from the root of this binary tree
     */
    T getRoot();

    /**
     * Returns a specified element if it is found in this binary tree.
     * 
     * @param target the target to search
     * @return the element if it is found in this binary tree
     */
    T find(T target);

    /**
     * Checks if this binary tree contains a specified element.
     * 
     * @param target the target to search
     * @return true if this binary tree contains the target, false otherwise
     */
    boolean contains(T target);

    /**
     * Checks if this binary tree is empty.
     * 
     * @return true if this binary tree is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements present in this binary tree.
     * 
     * @return the number of elements present in this binary tree
     */
    int size();

    /**
     * Returns an iterator over the elements in this binary tree 
     * by performing a pre-order traversal.
     * 
     * @return an iterator over the elements in this binary tree
     */
    Iterator<T> iteratorPreOrder();

    /**
     * Returns an iterator over the elements in this binary tree 
     * by performing an in-order traversal.
     * 
     * @return an iterator over the elements in this binary tree
     */
    Iterator<T> iteratorInOrder();

    /**
     * Returns an iterator over the elements in this binary tree 
     * by performing a post-order traversal.
     * 
     * @return an iterator over the elements in this binary tree
     */
    Iterator<T> iteratorPostOrder();

    /**
     * Returns an iterator over the elements in this binary tree 
     * by performing a level-order traversal.
     * 
     * @return an iterator over the elements in this binary tree
     */
    Iterator<T> iteratorLevelOrder();

    /**
     * Returns a string representation of this binary tree.
     * 
     * @return a string representation of this binary tree
     */
    @Override
    String toString();
}
