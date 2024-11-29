package dev.zZUST.exceptions;

/**
 * Unchecked exception that can be thrown by some methods 
 * of a collection indicating that it has no elements. <p>
 * 
 * The available constructors are the following: <p>
 * <ul>
 *  <li>{@link #EmptyCollectionException()}: Constructs a new EmptyCollectionException with a default message</li>
 *  <li>{@link #EmptyCollectionException(String)}: Constructs a new EmptyCollectionException with a custom message</li>
 * </ul>
 * 
 * <h3>EmptyCollectionException</h3>
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see RuntimeException
 */
public class EmptyCollectionException extends RuntimeException {
    /**
     * The default message of this exception
     */
    protected static final String DEFAULT_MESSAGE = "Collection is empty";

    /**
     * Constructs a new EmptyCollectionException with a default message.
     */
    public EmptyCollectionException() {
        this(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new EmptyCollectionException with a custom message.
     * 
     * @param message the exception message
     */
    public EmptyCollectionException(String message) {
        super(message);
    }
}
