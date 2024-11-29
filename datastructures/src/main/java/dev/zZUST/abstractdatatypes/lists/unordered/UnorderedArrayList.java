package dev.zZUST.abstractdatatypes.lists.unordered;

import java.util.NoSuchElementException;

import dev.zZUST.abstractdatatypes.lists.ArrayList;
import pt.ipp.estg.ed.UnorderedListADT;

/**
 * Resizable-array implementation of the {@code UnorderedListADT} interface 
 * using the {@code ArrayList} abstract class as a base code.
 * 
 * <h3>UnorderedArrayList</h3>
 * @param <T> the type of element stored in this unordered list
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see UnorderedListADT
 * @see ArrayList
 */
public class UnorderedArrayList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    /**
     * Constructs an empty UnorderedArrayList with a default capacity.
     */
    public UnorderedArrayList() {
        super();
    }

    /**
     * Constructs an empty UnorderedArrayList with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    public UnorderedArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     */
    @Override
    public void addToFront(T element) throws NullPointerException {
        if (element == null) throw new NullPointerException("Element is null");

        if (size() == array.length) expandCapacity();
        for (int i = size(); i > 0; i--) array[i] = array[i - 1];
        array[0] = element;
        size++;
        modCount++;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     */
    @Override
    public void addToRear(T element) throws NullPointerException {
        if (element == null) throw new NullPointerException("Element is null");

        if (size() == array.length) expandCapacity();
        array[size++] = element;
        modCount++;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element or target are null
     */
    @Override
    public void addAfter(T element, T target) throws NullPointerException {
        if (element == null) throw new NullPointerException("Element is null");
        if (target == null) throw new NullPointerException("Target is null");

        if (size() == array.length) expandCapacity();
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(target)) {
                for (int j = size(); j > i + 1; j--) array[j] = array[j - 1];
                array[i + 1] = element;
                size++;
                modCount++;
                return;
            }
        }

        throw new NoSuchElementException("Target not found");
    }
}
