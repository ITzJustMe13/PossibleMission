package dev.zZUST.abstractdatatypes.stacks;

import dev.zZUST.exceptions.EmptyCollectionException;
import dev.zZUST.nodes.LinearNode;
import pt.ipp.estg.ed.StackADT;

/**
 * Singly-linked list implementation of the {@code StackADT} interface.
 * 
 * <h3>LinkedStack</h3>
 * @param <T> the type of element stored in this stack
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see StackADT
 * @see LinearNode
 */
public class LinkedStack<T> implements StackADT<T> {
    /**
     * The top node of this stack
     */
    protected LinearNode<T> top;
    /**
     * The number of elements in this stack
     */
    protected int size;

    /**
     * Constructs an empty LinkedStack object.
     */
    public LinkedStack() {
        top = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(T element) throws NullPointerException {
        if (element == null) throw new NullPointerException("Element is null");

        top = new LinearNode<>(element, top);
        size++;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this stack is empty
     */
    @Override
    public T pop() throws EmptyCollectionException {
        T result = peek();
        top = top.getNext();
        size--;

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this stack is empty
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Stack is empty");

        return top.getElement();
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
    public String toString() {
        return (new StringBuilder("[" + reverseToString(top) + "]")).toString();
    }

    /**
     * Recursively converts the elements of this stack to a string in reverse order.
     * 
     * @param currentNode the current node
     * @return a string representation of the elements of this stack in reverse order
     */
    private String reverseToString(LinearNode<T> currentNode) {
        if (currentNode == null) return "";

        return reverseToString(currentNode.getNext()) + ((currentNode.getNext() == null) ? "" : ", ") + currentNode.getElement();
    }
}
