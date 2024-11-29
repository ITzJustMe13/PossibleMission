package dev.zZUST.abstractdatatypes.lists.ordered;

import dev.zZUST.abstractdatatypes.lists.ArrayList;
import pt.ipp.estg.ed.OrderedListADT;

/**
 * Resizable-array implementation of the {@code UnorderedListADT} interface 
 * using the {@code ArrayList} abstract class as a base code.
 * 
 * <h3>UnorderedArrayList</h3>
 * @param <T> the type of element stored in this ordered list
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see OrderedListADT
 * @see ArrayList
 */
public class OrderedArrayList<T> extends ArrayList<T> implements OrderedListADT<T> {
    /**
     * Constructs an empty OrderedArrayList with a default capacity.
     */
    public OrderedArrayList() {
        super();
    }

    /**
     * Constructs an empty OrderedArrayList with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    public OrderedArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the element is null
     * @throws ClassCastException if the element isn't comparable
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(T element) throws NullPointerException, ClassCastException {
        if (element == null) throw new NullPointerException("Element is null");

        if (size() == array.length) expandCapacity();
        int addingIndex = size();
        Comparable<T> comparableElement = (Comparable<T>) element;
        for (int i = 0; i < size(); i++) {
            if (comparableElement.compareTo(array[i]) <= 0) {
                for (int j = size(); j > i; j--) array[j] = array[j - 1];
                addingIndex = i;
                break;
            }
        }
        array[addingIndex] = element;
        size++;
        modCount++;
    }
}
