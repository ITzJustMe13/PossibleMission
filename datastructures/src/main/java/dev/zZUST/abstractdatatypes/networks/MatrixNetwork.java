package dev.zZUST.abstractdatatypes.networks;

import java.util.Iterator;
import java.util.NoSuchElementException;

import dev.zZUST.abstractdatatypes.lists.unordered.UnorderedArrayList;
import dev.zZUST.abstractdatatypes.queues.LinkedQueue;
import dev.zZUST.abstractdatatypes.stacks.LinkedStack;
import dev.zZUST.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.NetworkADT;
import pt.ipp.estg.ed.QueueADT;
import pt.ipp.estg.ed.StackADT;
import pt.ipp.estg.ed.UnorderedListADT;

/**
 * Resizable-matrix implementation of the {@code NetworkADT} interface.
 * 
 * <h3>MatrixNetwork</h3>
 * @param <T> the type of element stored in this network
 * @since 1.0
 * @version 1.0
 * @author zZUST
 * @see NetworkADT
 */
public abstract class MatrixNetwork<T> implements NetworkADT<T> {
    /**
     * The default capacity of this network vertices array and cost matrix
     */
    protected static final int DEFAULT_CAPACITY = 10;
    /**
     * The default edge weight for this network
     */
    protected static final int DEFAULT_EDGE_WEIGHT = 0;
    /**
     * The array containing the vertices of this network
     */
    protected T[] vertices;
    /**
     * The number of vertices in this network
     */
    protected int size;
    /**
     * The cost matrix of this network
     */
    protected double[][] costMatrix;

    /**
     * Constructs an empty MatrixNetwork with a default capacity.
     */
    public MatrixNetwork() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty MatrixNetwork with a specified initial capacity.
     * 
     * @param initialCapacity the initial capacity (Minimum value is 0)
     */
    @SuppressWarnings("unchecked")
    public MatrixNetwork(int initialCapacity) {
        initialCapacity = (initialCapacity < 0) ? 0 : initialCapacity;
        vertices = (T[]) new Object[initialCapacity];
        size = 0;
        costMatrix = new double[initialCapacity][initialCapacity];
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
            costMatrix[size() - 1][i] = Double.POSITIVE_INFINITY;
            costMatrix[i][size() - 1] = Double.POSITIVE_INFINITY;
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
                    for (int j = 0; j < size(); j++) costMatrix[i][j] = costMatrix[i + 1][j];
                }
                for (int j = vertexIndex; j < size(); j++) for (int i = 0; i < size(); i++) costMatrix[i][j] = costMatrix[i][j + 1];
                for (int i = 0; i <= size(); i++) {
                    costMatrix[size()][i] = Double.POSITIVE_INFINITY;
                    costMatrix[i][size()] = Double.POSITIVE_INFINITY;
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
                    if (costMatrix[currentIndex][i] != Double.POSITIVE_INFINITY && !visitedVertices[i]) {
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
                    if (costMatrix[currentIndex][i] != Double.POSITIVE_INFINITY && !visitedVertices[i]) {
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
            boolean[] visitedVertices = new boolean[size()];
            double[] distancesArray = new double[size()];
            int[] previousVertex = new int[size()];
            for (int i = 0; i < size(); i++) {
                visitedVertices[i] = (i == startIndex);
                distancesArray[i] = (i == startIndex) ? 0 : Double.POSITIVE_INFINITY;
                previousVertex[i] = -1;
            }
            for (int i = 0; i < size(); i++) {
                int closestVertex = -1;
                double smallestDistance = Double.POSITIVE_INFINITY;
                for (int j = 0; j < size(); j++) {
                    if (!visitedVertices[j] && distancesArray[j] < smallestDistance) {
                        closestVertex = j;
                        smallestDistance = distancesArray[j];
                    }
                }
                if (closestVertex == -1) break;
                visitedVertices[closestVertex] = true;
                for (int j = 0; j < size(); j++) {
                    if (!visitedVertices[j] && costMatrix[closestVertex][j] != Double.POSITIVE_INFINITY) {
                        double newDistance = distancesArray[closestVertex] + costMatrix[closestVertex][j];
                        if (newDistance < distancesArray[j]) {
                            distancesArray[j] = newDistance;
                            previousVertex[j] = closestVertex;
                        }
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
     * 
     * @throws NullPointerException if the start vertex and/or target vertex are null
     */
    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) throws NullPointerException {
        Iterator<T> iterator = iteratorShortestPath(startVertex, targetVertex);

        if (!iterator.hasNext()) return Double.POSITIVE_INFINITY;

        double distance = 0;
        T previousVertex = iterator.next();
        while (iterator.hasNext()) {
            T currentVertex = iterator.next();
            int previousIndex = isVertexValid(previousVertex);
            int currentIndex = isVertexValid(currentVertex);
            if (previousIndex != -1 && currentIndex != -1) distance += costMatrix[previousIndex][currentIndex];
            previousVertex = currentVertex;
        }

        return distance;
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
            result.append("Cost Matrix:\n\t");
            for (int i = 0; i < size(); i++) result.append(vertices[i]).append("\t");
            result.append("\n");
            for (int i = 0; i < size(); i++) {
                result.append(vertices[i]).append("\t");
                for (int j = 0; j < size(); j++) result.append(costMatrix[i][j] != Double.POSITIVE_INFINITY ? costMatrix[i][j] : "∞").append("\t");
                result.append("\n");
            }
        }
        
        return result.toString();
    }

    /**
     * Expands the capacity of this network by creating a new array of vertices and a new cost matrix.
     * The new capacity is calculated based on the current capacity of the network.
     * If the current capacity is less than 2, the new capacity is set to the current capacity plus 1.
     * Otherwise, the new capacity is set to the current capacity plus half of the current capacity.
     * The elements of the old arrays are copied to the new arrays.
     */
    @SuppressWarnings("unchecked")
    protected void expandCapacity() {
        int newCapacity = (vertices.length < 2) ? vertices.length + 1 : vertices.length + (vertices.length / 2);
        T[] newVertices = (T[]) new Object[newCapacity];
        double[][] newCostMatrix = new double[newCapacity][newCapacity];
        for (int i = 0; i < size(); i++) {
            newVertices[i] = vertices[i];
            for (int j = 0; j < size(); j++) newCostMatrix[i][j] = costMatrix[i][j];
        }
        vertices = newVertices;
        costMatrix = newCostMatrix;
    }

    /**
     * Checks if a vertex is valid in this network.
     *
     * @param vertex the vertex to check
     * @return the index of the vertex if it is valid, -1 otherwise
     */
    protected int isVertexValid(T vertex) {
        for (int i = 0; i < size(); i++) if (vertices[i].equals(vertex)) return i;

        return -1;
    }
}
