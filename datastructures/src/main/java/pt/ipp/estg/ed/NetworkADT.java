package pt.ipp.estg.ed;

/**
 * A network is a non-linear abstract data type that, like the graph, consists of vertexes and edges or connections between those vertexes. <p>
 * There will be two base types of network implemented: <p>
 * <ul>
 *  <li>Undirected</li>
 *  <li>Directed</li>
 * </ul>
 * The difference between undirected and directed networks is that if an edge is added between two vertexes 
 * it will be bidirectional and unidirectional, respectively. <p>
 * For this network implementation, the edges have weights associated to them. <p>
 * 
 * The common operations for a network include: <p>
 * <ul>
 *  <li>{@link #addEdge(Object, Object, double)}: Adds an edge between two vertexes with a specified weight</li>
 *  <li>{@link #shortestPathWeight(Object, Object)}: Returns the weight of the shortest path between two vertexes</li>
 * </ul>
 * 
 * This interface is a member of the 
 * <i>Data Structures Framework</i>
 * 
 * <h3>NetworkADT</h3>
 * @param <T> the type of elements in this network
 * @since 1.0
 * @version 2.0
 * @author ESTG-ED
 * @see GraphADT
 */
public interface NetworkADT<T> extends GraphADT<T> {
    /**
     * Adds an edge between two vertexes with a specified weight.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight the weight of the edge
     */
    void addEdge(T vertex1, T vertex2, double weight);

    /**
     * Returns the weight of the shortest path between two vertexes.
     * 
     * @param startVertex the start vertex
     * @param targetVertex the target vertex
     * @return the weight of the shortest path between two vertexes
     */
    double shortestPathWeight(T startVertex, T targetVertex);
}
