package main.wordrelationship;

import java.util.Collection;
import java.util.HashMap;

//map id to word
//map word to connection
public class Graph<T> {
    private HashMap<Integer, Collection<Integer>> adjacentChildren;
    private HashMap<Integer, String> connectionToDefinition;

    public Graph() {
        adjacentChildren = new HashMap<>();
        connectionToDefinition = new HashMap<>();

    }
    public void nameOfVertex(int id, String word) {
        connectionToDefinition.put(id,word);
    }
    public void addEdge(int id, int child) {
        Collection<Integer> currentChildren = adjacentChildren.remove(id);
        currentChildren.add(child);
        adjacentChildren.put(id, currentChildren);
    }
}

