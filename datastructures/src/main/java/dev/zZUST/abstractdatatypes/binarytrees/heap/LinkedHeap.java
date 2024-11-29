package dev.zZUST.abstractdatatypes.binarytrees.heap;

import dev.zZUST.abstractdatatypes.binarytrees.LinkedBinaryTree;
import dev.zZUST.exceptions.EmptyCollectionException;
import dev.zZUST.nodes.HeapNode;
import pt.ipp.estg.ed.BinarySearchTreeADT;
import pt.ipp.estg.ed.HeapADT;

/**
 * Linked implementation of the {@code HeapADT} interface
 * using the {@code LinkedBinaryTree} abstract class as a base code.
 * 
 * <h3>LinkedHeap</h3>
 * @param <T> the type of element stored in this minheap
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see BinarySearchTreeADT
 * @see LinkedBinaryTree
 */
public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {
    /**
     * The reference to the last node added in this minheap.
     */
    public HeapNode<T> lastNode;

    /**
     * Construct an empty LinkedHeap object.
     */
    public LinkedHeap() {
        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     * @throws ClassCastException if the element isn't comparable
     */
    @Override
    public void addElement(T element) {
        if (element == null) throw new NullPointerException("Element is null");
        if (!(element instanceof Comparable)) throw new ClassCastException("Element not comparable");

        HeapNode<T> newNode = new HeapNode<T>(element);
        if (root == null) root = newNode;
        else {
            HeapNode<T> nextParent = getNextParent();
            if (nextParent.getLeft() == null) nextParent.setLeft(newNode);
            else nextParent.setRight(newNode);
            newNode.setParent(nextParent);
        }
        lastNode = newNode;
        size++;
        if (size() > 1) heapifyAdd();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this minheap is empty
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        T result = findMin();
        if (size() == 1) {
            root = null;
            lastNode = null;
        } else {
            HeapNode<T> newLastNode = getNewLastNode();
            if (lastNode.getParent().getLeft() == lastNode) lastNode.getParent().setLeft(null);
            else lastNode.getParent().setRight(null);
            root.setElement(lastNode.getElement());
            lastNode = newLastNode;
            heapifyRemove();
        }
        size--;

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this minheap is empty
     */
    @Override
    public T findMin() {
        if (isEmpty()) throw new EmptyCollectionException("Heap is empty");

        return root.getElement();
    }

    /**
     * Returns the next parent node where a new element can be added in this linked heap.
     *
     * @return The next parent node where a new element can be added.
     */
    private HeapNode<T> getNextParent() {
        HeapNode<T> result = lastNode;
        while (result != root && result.getParent().getLeft() != result) result = result.getParent();
        if (result != root) {
            if (result.getParent().getRight() == null) result = result.getParent();
            else {
                result = result.getParent().getRight();
                while (result.getLeft() != null) result = result.getLeft();
            }
        } else while (result.getLeft() != null) result = result.getLeft();

        return result;
    }

    /**
     * Performs heapify operation after adding a new element to the heap.
     * This method ensures that the heap property is maintained by comparing the new element with its parent.
     * If the new element is smaller than its parent, it is swapped with its parent and the process is repeated until the heap property is satisfied.
     */
    @SuppressWarnings("unchecked")
    private void heapifyAdd() {
        HeapNode<T> nextNode = lastNode;
        T temp = nextNode.getElement();
        while (nextNode != root && ((Comparable<T>) temp).compareTo(nextNode.getParent().getElement()) < 0) {
            nextNode.setElement(nextNode.getParent().getElement());
            nextNode = nextNode.getParent();
        }
        nextNode.setElement(temp);
    }

    /**
     * Returns the new last node in this linked minheap.
     *
     * @return the new last node in this linked minheap
     */
    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = lastNode;
        while (result != root && result.getParent().getLeft() == result) result = result.getParent();
        if (result != root) result = result.getParent().getLeft();
        while (result.getRight() != null) result = result.getRight();

        return result;
    }

    /**
     * Performs the heapify operation after removing an element from this minheap.
     * This operation ensures that the minheap property is maintained by moving elements down this heap.
     */
    @SuppressWarnings("unchecked")
    private void heapifyRemove() {
        T temp;
        HeapNode<T> currentNode = (HeapNode<T>) root;
        HeapNode<T> leftNode = currentNode.getLeft();
        HeapNode<T> rightNode = currentNode.getRight();
        HeapNode<T> nextNode;
        if (leftNode == null && rightNode == null) nextNode = null;
        else if (leftNode == null) nextNode = rightNode;
        else if (rightNode == null) nextNode = leftNode;
        else if (((Comparable<T>) leftNode).compareTo(rightNode.getElement()) < 0) nextNode = leftNode;
        else nextNode = rightNode;
        temp = currentNode.getElement();
        while (nextNode != null && ((Comparable<T>) nextNode.getElement()).compareTo(temp) < 0) {
            currentNode.setElement(nextNode.getElement());
            currentNode = nextNode;
            leftNode = currentNode.getLeft();
            rightNode = currentNode.getRight();
            if (leftNode == null && rightNode == null) nextNode = null;
            else if (leftNode == null) nextNode = rightNode;
            else if (rightNode == null) nextNode = leftNode;
            else if (((Comparable<T>) leftNode.getElement()).compareTo(rightNode.getElement()) < 0) nextNode = leftNode;
            else nextNode = rightNode;
        }
        currentNode.setElement(temp);
    }
}
