package dev.zZUST.abstractdatatypes.lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import dev.zZUST.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.ListADT;

/**
 * Resizable-array implementation of the {@code ListADT} interface.
 * 
 * <h3>ArrayList</h3>
 * @param <T> the type of element stored in this list
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see ListADT
 */
public abstract class ArrayList<T> implements ListADT<T> {
    /**
     * The default capacity of this list array
     */
    protected static final int DEFAULT_CAPACITY = 10;
    /**
     * The array containing the elements of this list
     */
    protected T[] array;
    /**
     * The number of elements in this list
     */
    protected int size;
    /**
     * The number of modifications made to this list
     */
    protected int modCount;

    /**
     * Constructs an empty ArrayList with a default capacity.
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty ArrayList with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        array = (T[]) new Object[(initialCapacity < 0) ? 0 : initialCapacity];
        size = 0;
        modCount = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        T result = first();
        for (int i = 0; i < size() - 1; i++) array[i] = array[i + 1];
        array[--size] = null;
        modCount++;

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        T result = last();
        array[--size] = null;
        modCount++;

        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the target is null
     * @throws EmptyCollectionException if this list is empty
     * @throws NoSuchElementException if the target isn't on this list
     */
    @Override
    public T remove(T target) throws NullPointerException, EmptyCollectionException, NoSuchElementException {
        if (target == null) throw new NullPointerException("Target is null");

        if (first().equals(target)) return removeFirst();
        if (last().equals(target)) return removeLast();
        for (int i = 1; i < size() - 1; i++) {
            if (array[i].equals(target)) {
                array[i] = null;
                for (int j = i; j < size() - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[--size] = null;
                modCount++;

                return target;
            }
        }
        
        throw new NoSuchElementException("Target not found");
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("List is empty");

        return array[0];
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EmptyCollectionException if this list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("List is empty");

        return array[size() - 1];
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the target is null
     */
    @Override
    public boolean contains(T target) {
        if (target == null) throw new NullPointerException("Target is null");

        for (int i = 0; i < size(); i++) if (array[i].equals(target)) return true;
        return false;
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
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size(); i++) result.append(array[i]).append((i < size() - 1) ? ", " : "");
        result.append("]");

        return result.toString();
    }

    /**
     * Expands the capacity of the array used to store the elements in this list.
     * If the current array length is 0 or 1, a new array with a capacity of the old length + 1 is created.
     * Otherwise, a new array with a capacity increased by half of the current array's length is created.
     * The elements from the current array are copied to the new array.
     */
    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        T[] newArray = (T[]) new Object[(array.length < 2) ? array.length + 1 : array.length + (array.length / 2)];
        for (int i = 0; i < size(); i++) newArray[i] = array[i];
        array = newArray;
    }

    /**
     * {@code Iterator} implementation for the {@code ArrayList} class
     */
    public class ArrayIterator implements Iterator<T> {
        /**
         * The current index of this list array
         */
        protected int currentIndex;
        /**
         * The expected number of modifications made to this list
         */
        protected int expectedModCount;
        /**
         * The flag indicating if it's ok to call the remove method
         */
        protected boolean okToRemove;

        /**
         * Constructs an empty ArrayIterator object.
         */
        public ArrayIterator() {
            currentIndex = 0;
            expectedModCount = modCount;
            okToRemove = false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        /**
         * {@inheritDoc}
         * 
         * @throws ConcurrentModificationException if this list has been altered
         */
        @Override
        public T next() throws ConcurrentModificationException {
            if (expectedModCount != modCount) throw new ConcurrentModificationException("List has been altered");

            okToRemove = true;
            return array[currentIndex++];
        }

        /**
         * {@inheritDoc}
         * 
         * @throws ConcurrentModificationException if this list has been altered
         */
        @Override
        public void remove() throws ConcurrentModificationException, IllegalStateException {
            if (expectedModCount != modCount) throw new ConcurrentModificationException("List has been altered");
            if (!okToRemove) throw new IllegalStateException("Invalid remove call");

            ArrayList.this.remove(array[--currentIndex]);
            expectedModCount++;
            okToRemove = false;
        }
    }
}
