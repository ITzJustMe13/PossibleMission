package pt.ipp.estg.ed;

import java.util.Iterator;

/**
 * A graph is a non-linear abstract data type that consists of vertexes and edges or connections between those vertexes. <p>
 * There will be two base types of graphs implemented: <p>
 * <ul>
 *  <li>Undirected</li>
 *  <li>Directed</li>
 * </ul>
 * The difference between undirected and directed graphs is that if an edge is added between two vertexes 
 * it will be bidirectional and unidirectional, respectively. <p>
 * For this graph implementation, the edges don't have weights associated to them. <p>
 * 
 * The common operations for a graph include: <p>
 * <ul>
 *  <li>{@link #addVertex(Object)}: Adds a vertex to this graph</li>
 *  <li>{@link #removeVertex(Object)}: Removes a vertex from this graph</li>
 *  <li>{@link #addEdge(Object, Object)}: Adds an edge between two vertexes</li>
 *  <li>{@link #removeEdge(Object, Object)}: Removes an edge between two vertexes</li>
 *  <li>{@link #isConnected()}: Checks if this graph is connected</li>
 *  <li>{@link #isEmpty()}: Checks if this graph is empty</li>
 *  <li>{@link #size()}: Returns the number of elements present in this graph</li>
 *  <li>{@link #iteratorBFS(Object)}: Returns an iterator over the elements in this graph by performing a breadth-first-search traversal</li>
 *  <li>{@link #iteratorDFS(Object)}: Returns an iterator over the elements in this graph by performing a depth-first-search traversal</li>
 *  <li>{@link #iteratorShortestPath(Object, Object)}: Returns an iterator over the elements that constitute the shortest path between two vertexes including those two vertexes</li>
 *  <li>{@link #toString()}: Returns a string representation of this graph</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>GraphADT</h3>
 * @param <T> the type of elements in this graph
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see NetworkADT
 * @see Iterator
 */
public interface GraphADT<T> {
    /**
     * Adds a vertex to this graph.
     * 
     * @param vertex the vertex to be added
     */
    void addVertex(T vertex);

    /**
     * Removes a vertex from this graph.
     * 
     * @param target the target to remove
     */
    void removeVertex(T target);

    /**
     * Adds an edge between two vertexes.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    void addEdge(T vertex1, T vertex2);

    /**
     * Removes an edge between two vertexes.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    void removeEdge(T vertex1, T vertex2);

    /**
     * Checks if this graph is connected.
     * 
     * @return true if this graph is connected, false otherwise
     */
    boolean isConnected();

    /**
     * Checks if this graph is empty.
     * 
     * @return true if this graph is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements present in this graph.
     * 
     * @return the number of elements present in this graph
     */
    int size();

    /**
     * Returns an iterator over the elements in this graph by performing a breadth-first-search traversal.
     * 
     * @param startVertex the start vertex
     * @return an iterator over the elements in this graph
     */
    Iterator<T> iteratorBFS(T startVertex);

    /**
     * Returns an iterator over the elements in this graph by performing a depth-first-search traversal.
     * 
     * @param startVertex the start vertex
     * @return an iterator over the elements in this graph
     */
    Iterator<T> iteratorDFS(T startVertex);

    /**
     * Returns an iterator over the elements that constitute the shortest path 
     * between two vertexes including those two vertexes
     * 
     * @param startVertex the start vertex
     * @param targetVertex the target vertex
     * @return an iterator over the elements that constitute the shortest path 
     * between two vertexes including those two vertexes
     */
    Iterator<T> iteratorShortestPath(T startVertex, T targetVertex);

    /**
     * Returns a string representation of this graph.
     * 
     * @return a string representation of this graph
     */
    @Override
    String toString();
}
