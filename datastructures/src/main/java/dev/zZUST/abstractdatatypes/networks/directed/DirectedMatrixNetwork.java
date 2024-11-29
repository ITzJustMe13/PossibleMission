package dev.zZUST.abstractdatatypes.networks.directed;

import dev.zZUST.abstractdatatypes.networks.MatrixNetwork;
import pt.ipp.estg.ed.NetworkADT;

/**
 * Resizable-matrix implementation of a directed {@code NetworkADT} 
 * using the {@code MatrixNetwork} abstract class as a base code.
 * 
 * <h3>DirectedMatrixNetwork</h3>
 * @param <T> the type of element stored in this directed network
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see NetworkADT
 * @see MatrixNetwork
 */
public class DirectedMatrixNetwork<T> extends MatrixNetwork<T> {
    /**
     * Constructs an empty DirectedMatrixNetwork with a default capacity.
     */
    public DirectedMatrixNetwork() {
        super();
    }

    /**
     * Constructs an empty DirectedMatrixNetwork with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    public DirectedMatrixNetwork(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the vertex1 and/or vertex2 are null
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws NullPointerException {
        addEdge(vertex1, vertex2, DEFAULT_EDGE_WEIGHT);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the vertex1 and/or vertex2 are null
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        if (vertex1 == null) throw new NullPointerException("Vertex 1 is null");
        if (vertex2 == null) throw new NullPointerException("Vertex 2 is null");

        int vertex1Index = isVertexValid(vertex1);
        int vertex2Index = isVertexValid(vertex2);
        if (vertex1Index != -1 && vertex2Index != -1) costMatrix[vertex1Index][vertex2Index] = weight;
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
        if (vertex1Index != -1 && vertex2Index != -1) costMatrix[vertex1Index][vertex2Index] = Double.POSITIVE_INFINITY;
    }
}
