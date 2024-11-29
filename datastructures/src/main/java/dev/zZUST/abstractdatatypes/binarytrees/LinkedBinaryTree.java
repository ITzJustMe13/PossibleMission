package dev.zZUST.abstractdatatypes.binarytrees;

import java.util.Iterator;
import java.util.NoSuchElementException;

import dev.zZUST.abstractdatatypes.lists.unordered.UnorderedArrayList;
import dev.zZUST.abstractdatatypes.queues.LinkedQueue;
import dev.zZUST.exceptions.EmptyCollectionException;
import dev.zZUST.nodes.BinaryTreeNode;
import pt.ipp.estg.ed.BinaryTreeADT;
import pt.ipp.estg.ed.QueueADT;
import pt.ipp.estg.ed.UnorderedListADT;

/**
 * Linked implementation of the {@code BinaryTreeADT} interface.
 * 
 * <h3>LinkedBinaryTree</h3>
 * @param <T> the type of element stored in this binary tree
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see BinaryTreeADT
 */
public abstract class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    /**
     * The root node of this binary tree
     */
    protected BinaryTreeNode<T> root;
    /**
     * The number of elements in this binary tree
     */
    protected int size;

    /**
     * Constructs an empty LinkedBinaryTree object.
     */
    public LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this binary tree is empty
     */
    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Binary tree is empty");

        return root.getElement();
    }

    /**
     * {@inheriDoc}
     * 
     * @throws NullPointerException if the target is null
     * @throws NoSuchElementException if the target isn't in this binary tree
     */
    @Override
    public T find(T target) throws NullPointerException, NoSuchElementException {
        if (target == null) throw new NullPointerException("Target is null");
        
        BinaryTreeNode<T> currentNode = findAgain(root, target);
        if (currentNode != null) return target;

        throw new NoSuchElementException("Target not found");
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the target is null
     */
    @Override
    public boolean contains(T target) throws NullPointerException {
        try {
            find(target);
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        preOrder(root, unorderedList);

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        inOrder(root, unorderedList);

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        postOrder(root, unorderedList);

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        QueueADT<BinaryTreeNode<T>> queue = new LinkedQueue<>();
        if (root != null) queue.enqueue(root);
        while (!queue.isEmpty()) {
            BinaryTreeNode<T> currentNode = queue.dequeue();
            unorderedList.addToRear(currentNode.getElement());
            if (currentNode.getLeft() != null) queue.enqueue(currentNode.getLeft());
            if (currentNode.getRight() != null) queue.enqueue(currentNode.getRight());
        }

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Iterator<T> iterator = iteratorLevelOrder();
        while (iterator.hasNext()) result.append(iterator.next()).append((iterator.hasNext()) ? ", " : "");
        result.append("]");

        return result.toString();
    }

    /**
     * Recursively searches for a target element in this binary tree starting from the given node.
     * 
     * @param currentNode the current node being checked
     * @param target the target to search
     * @return the node containing the target element, or null if not found
     */
    private BinaryTreeNode<T> findAgain(BinaryTreeNode<T> currentNode, T target) {
        if (currentNode == null) return null;
        if (currentNode.getElement().equals(target)) return currentNode;

        BinaryTreeNode<T> tempNode = findAgain(currentNode.getLeft(), target);
        if (tempNode == null) tempNode = findAgain(currentNode.getRight(), target);

        return tempNode;
    }

    /**
     * Performs a pre-order traversal of this binary tree,
     * adding each element to the unordered list.
     * 
     * @param currentNode the current node being visited
     * @param unorderedList the unordered list to add the elements to
     */
    private void preOrder(BinaryTreeNode<T> currentNode, UnorderedListADT<T> unorderedList) {
        if (currentNode == null) return;

        unorderedList.addToRear(currentNode.getElement());
        preOrder(currentNode.getLeft(), unorderedList);
        preOrder(currentNode.getRight(), unorderedList);
    }

    /**
     * Performs an in-order traversal of this binary tree,
     * adding each element to the unordered list.
     * 
     * @param currentNode the current node being visited
     * @param unorderedList the unordered list to add the elements to
     */
    private void inOrder(BinaryTreeNode<T> currentNode, UnorderedListADT<T> unorderedList) {
        if (currentNode == null) return;

        inOrder(currentNode.getLeft(), unorderedList);
        unorderedList.addToRear(currentNode.getElement());
        inOrder(currentNode.getRight(), unorderedList);
    }

    /**
     * Performs a post-order traversal of this binary tree,
     * adding each element to the unordered list.
     * 
     * @param currentNode the current node being visited
     * @param unorderedList the unordered list to add the elements to
     */
    private void postOrder(BinaryTreeNode<T> currentNode, UnorderedListADT<T> unorderedList) {
        if (currentNode == null) return;

        postOrder(currentNode.getLeft(), unorderedList);
        postOrder(currentNode.getRight(), unorderedList);
        unorderedList.addToRear(currentNode.getElement());
    }
}
