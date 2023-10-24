package main.wordrelationship;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {
    private final List<Integer> vertices;
    private int numberOfEdges;
    public Graph(Integer idNumbers) {
        vertices = new ArrayList<>(idNumbers);
        numberOfEdges = 0;
    }
    /* v is representative of the hypernym, w is representative of the hyponym w is pointing to v*/
    public void addEdge(int v, int w) {
        vertices.set(w, v);
        numberOfEdges++;
    }
    public List<Integer> getChildren(int v) {
        int i = 0;
        List<Integer> childrenIds = new ArrayList<>();
        while (i < vertices.size()) {
            if (vertices.get(i) == v) {
                childrenIds.add(i);
            }
            i++;
        }
        return childrenIds;
    }
    public int edgeReturn() {
        return numberOfEdges;
    }
    public int verticesReturn() {
        return vertices.size();
    }

}
