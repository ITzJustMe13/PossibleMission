package pt.ipp.estg.ed;

/**
 * A heap is a non-linear abstract data type that are complete binary trees 
 * meaning that all the levels of the tree are filled except the lowest level nodes 
 * or leafs which are filled from as left as possible. <p>
 * There are two types of heaps: <p>
 * <ul>
 *  <li>Min-heap</li>
 *  <li>Max-heap</li>
 * </ul>
 * The difference between the two heaps is that every parent node is smaller/bigger or equal 
 * in comparison to the left and right child for the min-heap and max-heap respectively. <p>
 * 
 * The common operations for a min-heap include: <p>
 * <ul>
 *  <li>{@link #addElement(Object)}: Adds an element to this min-heap at the proper location</li>
 *  <li>{@link #removeMin()}: Removes the element from the root of this min-heap</li>
 *  <li>{@link #findMin()}: Returns the element from the root of this min-heap</li>
 * </ul> 
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>HeapADT</h3>
 * @param <T> the type of elements in this min-heap
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see BinaryTreeADT
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {
    /**
     * Adds an element to this min-heap at the proper location.
     * 
     * @param element the element to be added
     */
    void addElement(T element);

    /**
     * Removes the element from the root of this min-heap.
     * 
     * @return the element from the root of this min-heap
     */
    T removeMin();

    /**
     * Returns the element from the root of this min-heap.
     * 
     * @return the element from the root of this min-heap
     */
    T findMin();
}
