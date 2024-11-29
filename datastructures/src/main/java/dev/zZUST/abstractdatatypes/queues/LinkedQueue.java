package dev.zZUST.abstractdatatypes.queues;

import dev.zZUST.exceptions.EmptyCollectionException;
import dev.zZUST.nodes.LinearNode;
import pt.ipp.estg.ed.QueueADT;

/**
 * Singly-linked list implementation of the {@code QueueADT} interface.
 * 
 * <h3>LinkedQueue</h3>
 * @param <T> the type of element stored in this queue
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see QueueADT
 * @see LinearNode
 */
public class LinkedQueue<T> implements QueueADT<T> {
    /**
     * The front node of this queue
     */
    protected LinearNode<T> front;
    /**
     * The front node of this queue
     */
    protected LinearNode<T> rear;
    /**
     * The number of elements in this stack
     */
    protected int size;

    /**
     * Constructs an empty LinkedQueue object.
     */
    public LinkedQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     */
    @Override
    public void enqueue(T element) throws NullPointerException {
        if (element == null) throw new NullPointerException("Element is null");

        LinearNode<T> newNode = new LinearNode<>(element);
        if (rear == null) front = newNode;
        else rear.setNext(newNode);
        rear = newNode;
        size++;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        T result = first();
        front = front.getNext();
        if (front == null) rear = null;
        size--;

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Queue is empty");

        return front.getElement();
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
        StringBuilder result = new StringBuilder("[");
        LinearNode<T> currentNode = front;
        while (currentNode != null) {
            result.append(currentNode.getElement()).append((currentNode.getNext() != null) ? ", " : "");

            currentNode = currentNode.getNext();
        }
        result.append("]");

        return result.toString();
    }
}
