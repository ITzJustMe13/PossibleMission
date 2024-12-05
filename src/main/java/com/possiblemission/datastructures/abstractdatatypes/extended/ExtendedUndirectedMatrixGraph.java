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

    @Override
    public Object[] getAdjentVertexes(Object vertex) {
        Object[] returnArray = new Object[size()];
        Object[] vertices = getVertexes();
        int count = 0;
        for(int i = 0; i < size(); i++){
            if (vertices[i] == vertex){
                for(int j = 0; j < size(); j++){
                    if(adjacencyMatrix[i][j]){
                        returnArray[count] = vertices[j];
                    }
                }
            }
        }
        return returnArray;
    }
}
