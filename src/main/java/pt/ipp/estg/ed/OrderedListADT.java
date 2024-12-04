package pt.ipp.estg.ed;

/**
 * An ordered list is a linear abstract data type that allows the addition in which
 * the order of it's elements is determined by 
 * some intrinsic characteristic of the elements. <p>
 * In order to add an element to this ordered list, the class of the element 
 * needs to implement the {@code Comparable} interface. <p>
 * 
 * The common operations for an ordered list include: <p>
 * <ul>
 *  <li>{@link #add(Comparable)}: Adds an element to this ordered list at the proper location</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>OrderedListADT</h3>
 * @param <T> the type of elements in this ordered list
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see Comparable
 * @see ListADT
 */
public interface OrderedListADT<T> extends ListADT<T> {
    /**
     * Adds an element to this ordered list at the proper location.
     * 
     * @param element the element to be added
     */
    void add(T element);
}
