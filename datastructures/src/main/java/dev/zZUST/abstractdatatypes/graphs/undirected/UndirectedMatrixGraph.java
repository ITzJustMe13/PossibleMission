package dev.zZUST.abstractdatatypes.graphs.undirected;

import dev.zZUST.abstractdatatypes.graphs.MatrixGraph;
import pt.ipp.estg.ed.GraphADT;

/**
 * Resizable-matrix implementation of an undirected {@code GraphADT} 
 * using the {@code MatrixGraph} abstract class as a base code.
 * 
 * <h3>UndirectedMatrixGraph</h3>
 * @param <T> the type of element stored in this undirected graph
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see GraphADT
 * @see MatrixGraph
 */
public class UndirectedMatrixGraph<T> extends MatrixGraph<T> {
    /**
     * Constructs an empty UndirectedMatrixGraph with a default capacity.
     */
    public UndirectedMatrixGraph() {
        super();
    }

    /**
     * Constructs an empty UndirectedMatrixGraph with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    public UndirectedMatrixGraph(int initialCapacity) {
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
        if (vertex1Index != -1 && vertex2Index != -1) {
            adjacencyMatrix[vertex1Index][vertex2Index] = true;
            adjacencyMatrix[vertex2Index][vertex1Index] = true;
        }
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
        if (vertex1Index != -1 && vertex2Index != -1) {
            adjacencyMatrix[vertex1Index][vertex2Index] = false;
            adjacencyMatrix[vertex2Index][vertex1Index] = false;
        }
    }
}
