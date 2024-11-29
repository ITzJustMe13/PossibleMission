package dev.zZUST.abstractdatatypes.graphs.directed;

import dev.zZUST.abstractdatatypes.graphs.MatrixGraph;
import pt.ipp.estg.ed.GraphADT;

/**
 * Resizable-matrix implementation of a directed {@code GraphADT} 
 * using the {@code MatrixGraph} abstract class as a base code.
 * 
 * <h3>DirectedMatrixGraph</h3>
 * @param <T> the type of element stored in this directed graph
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see GraphADT
 * @see MatrixGraph
 */
public class DirectedMatrixGraph<T> extends MatrixGraph<T> {
    /**
     * Constructs an empty DirectedMatrixGraph with a default capacity.
     */
    public DirectedMatrixGraph() {
        super();
    }

    /**
     * Constructs an empty DirectedMatrixGraph with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    public DirectedMatrixGraph(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the vertex1 and/or vertex2 are null
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws NullPointerException {
        if (vertex1 == null) throw new NullPointerException("Vertex 1 is null");
        if (vertex2 == null) throw new NullPointerException("Vertex 2 is null");

        int vertex1Index = isVertexValid(vertex1);
        int vertex2Index = isVertexValid(vertex2);
        if (vertex1Index != -1 && vertex2Index != -1) adjacencyMatrix[vertex1Index][vertex2Index] = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the vertex1 and/or vertex2 are null
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) throws NullPointerException {
        if (vertex1 == null) throw new NullPointerException("Vertex 1 is null");
        if (vertex2 == null) throw new NullPointerException("Vertex 2 is null");

        int vertex1Index = isVertexValid(vertex1);
        int vertex2Index = isVertexValid(vertex2);
        if (vertex1Index != -1 && vertex2Index != -1) adjacencyMatrix[vertex1Index][vertex2Index] = false;
    }
}
