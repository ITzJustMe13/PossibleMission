package com.possiblemission.datastructures.abstractdatatypes.extended;

import com.possiblemission.datastructures.abstractdatatypes.graphs.undirected.UndirectedMatrixGraph;

public class ExtendedUndirectedMatrixGraph<T> extends UndirectedMatrixGraph<T> implements ExtendedGraphADT<T> {
    @Override
    public Object[] getVertexes() {
        Object[] returnArray = new Object[size()];
        for (int i = 0; i < size(); i++) {
            returnArray[i] = vertices[i];
        }
        return returnArray;
    }
}
