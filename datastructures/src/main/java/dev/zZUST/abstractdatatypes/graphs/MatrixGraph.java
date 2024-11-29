package dev.zZUST.abstractdatatypes.graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;

import dev.zZUST.abstractdatatypes.lists.unordered.UnorderedArrayList;
import dev.zZUST.abstractdatatypes.queues.LinkedQueue;
import dev.zZUST.abstractdatatypes.stacks.LinkedStack;
import dev.zZUST.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.GraphADT;
import pt.ipp.estg.ed.QueueADT;
import pt.ipp.estg.ed.StackADT;
import pt.ipp.estg.ed.UnorderedListADT;

/**
 * Resizable-matrix implementation of the {@code GraphADT} interface.
 * 
 * <h3>MatrixGraph</h3>
 * @param <T> the type of element stored in this graph
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see GraphADT
 */
public abstract class MatrixGraph<T> implements GraphADT<T> {
    /**
     * The default capacity of this graph vertices array and adjacency matrix
     */
    protected static final int DEFAULT_CAPACITY = 10;
    /**
     * The array containing the vertices of this graph
     */
    protected T[] vertices;
    /**
     * The number of vertices in this graph
     */
    protected int size;
    /**
     * The adjacency matrix of this graph
     */
    protected boolean[][] adjacencyMatrix;

    /**
     * Constructs an empty MatrixGraph with a default capacity.
     */
    public MatrixGraph() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty MatrixGraph with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    @SuppressWarnings("unchecked")
    public MatrixGraph(int initialCapacity) {
        initialCapacity = (initialCapacity < 0) ? 0 : initialCapacity;
        vertices = (T[]) new Object[initialCapacity];
        size = 0;
        adjacencyMatrix = new boolean[initialCapacity][initialCapacity];
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the vertex is null
     */
    @Override
    public void addVertex(T vertex) throws NullPointerException {
        if (vertex == null) throw new NullPointerException("Vertex is null");

        if (size() == vertices.length) expandCapacity();
        vertices[size++] = vertex;
        for (int i = 0; i < size(); i++) {
            adjacencyMatrix[size() - 1][i] = false;
            adjacencyMatrix[i][size() - 1] = false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the target is null
     * @throws EmptyCollectionException if this graph is empty
     * @throws NoSuchElementException if the target isn't on this graph
     */
    @Override
    public void removeVertex(T target) throws NullPointerException, EmptyCollectionException, NoSuchElementException {
        if (target == null) throw new NullPointerException("Target is null");
        if (isEmpty()) throw new EmptyCollectionException("Graph is empty");

        for (int vertexIndex = 0; vertexIndex < size(); vertexIndex++) {
            if (vertices[vertexIndex].equals(target)) {
                size--;
                for (int i = vertexIndex; i < size(); i++) {
                    vertices[i] = vertices[i + 1];
                    for (int j = 0; j < size(); j++) adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
                }
                for (int j = vertexIndex; j < size(); j++) for (int i = 0; i < size(); i++) adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
                for (int i = 0; i <= size(); i++) {
                    adjacencyMatrix[size()][i] = false;
                    adjacencyMatrix[i][size()] = false;
                }
                return;
            }
        }

        throw new NoSuchElementException("Target not found");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected() {
        for (int i = 0; i < size(); i++) {
            Iterator<T> iterator = iteratorBFS(vertices[i]);
            int iteratorSize = 0;
            while (iterator.hasNext()) {
                iterator.next();
                iteratorSize++;
            }
            if (iteratorSize != size()) return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the start vertex is null
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) throws NullPointerException {
        if (startVertex == null) throw new NullPointerException("Start vertex is null");

        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        int startIndex = isVertexValid(startVertex);
        if (startIndex != -1) {
            QueueADT<Integer> queue = new LinkedQueue<>();
            boolean[] visitedVertices = new boolean[size()];
            for (int i = 0; i < size(); i++) visitedVertices[i] = (i == startIndex);
            queue.enqueue(startIndex);
            while (!queue.isEmpty()) {
                int currentIndex = queue.dequeue();
                unorderedList.addToRear(vertices[currentIndex]);
                for (int i = 0; i < size(); i++) {
                    if (adjacencyMatrix[currentIndex][i] && !visitedVertices[i]) {
                        queue.enqueue(i);
                        visitedVertices[i] = true;
                    }
                }
            }
        }

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the start vertex is null
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) throws NullPointerException {
        if (startVertex == null) throw new NullPointerException("Start vertex is null");

        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        int startIndex = isVertexValid(startVertex);
        if (startIndex != -1) {
            StackADT<Integer> stack = new LinkedStack<>();
            boolean[] visitedVertices = new boolean[size()];
            for (int i = 0; i < size(); i++) visitedVertices[i] = (i == startIndex);
            stack.push(startIndex);
            unorderedList.addToRear(startVertex);
            while (!stack.isEmpty()) {
                int currentIndex = stack.peek();
                boolean found = false;
                for (int i = 0; i < size() && !found; i++) {
                    if (adjacencyMatrix[currentIndex][i] && !visitedVertices[i]) {
                        stack.push(i);
                        unorderedList.addToRear(vertices[i]);
                        visitedVertices[i] = true;
                        found = true;
                    }
                }
                if (!found && !stack.isEmpty()) stack.pop();
            }
        }

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the start vertex and/or target vertex are null
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws NullPointerException {
        if (startVertex == null) throw new NullPointerException("Start vertex is null");
        if (targetVertex == null) throw new NullPointerException("Target vertex is null");

        UnorderedListADT<T> unorderedList = new UnorderedArrayList<>();
        int startIndex = isVertexValid(startVertex);
        int targetIndex = isVertexValid(targetVertex);
        if (startIndex != -1 && targetIndex != -1) {
            QueueADT<Integer> queue = new LinkedQueue<>();
            boolean[] visitedVertices = new boolean[size()];
            int[] previousVertex = new int[size()];
            for (int i = 0; i < size(); i++) {
                visitedVertices[i] = (i == startIndex);
                previousVertex[i] = -1;
            }
            queue.enqueue(startIndex);
            while (!queue.isEmpty()) {
                int currentIndex = queue.dequeue();
                if (currentIndex == targetIndex) break;
                for (int i = 0; i < size(); i++) {
                    if (adjacencyMatrix[currentIndex][i] && !visitedVertices[i]) {
                        queue.enqueue(i);
                        visitedVertices[i] = true;
                        previousVertex[i] = currentIndex;
                    }
                }
            }
            int pathIndex = targetIndex;
            while (pathIndex != -1) {
                unorderedList.addToFront(vertices[pathIndex]);
                pathIndex = previousVertex[pathIndex];
            }
            if (unorderedList.first() != startVertex) return new UnorderedArrayList<T>().iterator();
        }

        return unorderedList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (isEmpty()) result.append("Graph is empty");
        else {
            result.append("Vertices: [");
            for (int i = 0; i < size(); i++) result.append(vertices[i]).append((i == size() - 1) ? "]\n" : ", ");
            result.append("Adjacency Matrix:\n\t");
            for (int i = 0; i < size(); i++) result.append(vertices[i]).append("\t");
            result.append("\n");
            for (int i = 0; i < size(); i++) {
                result.append(vertices[i]).append("\t");
                for (int j = 0; j < size(); j++) result.append(adjacencyMatrix[i][j] ? "1" : "0").append("\t");
                result.append("\n");
            }
        }
        
        return result.toString();
    }

    /**
     * Expands the capacity of this graph by creating a new array of vertices and a new adjacency matrix.
     * The new capacity is calculated based on the current capacity of the graph.
     * If the current capacity is less than 2, the new capacity is set to the current capacity plus 1.
     * Otherwise, the new capacity is set to the current capacity plus half of the current capacity.
     * The elements of the old arrays are copied to the new arrays.
     */
    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        int newCapacity = (vertices.length < 2) ? vertices.length + 1 : vertices.length + (vertices.length / 2);
        T[] newVertices = (T[]) new Object[newCapacity];
        boolean[][] newAdjacencyMatrix = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < size(); i++) {
            newVertices[i] = vertices[i];
            for (int j = 0; j < size(); j++) newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
        }
        vertices = newVertices;
        adjacencyMatrix = newAdjacencyMatrix;
    }

    /**
     * Checks if a vertex is valid in this graph.
     *
     * @param vertex the vertex to check
     * @return the index of the vertex if it is valid, -1 otherwise
     */
    protected int isVertexValid(T vertex) {
        for (int i = 0; i < size(); i++) if (vertices[i].equals(vertex)) return i;

        return -1;
    }
}
