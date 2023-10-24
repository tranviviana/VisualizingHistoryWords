package main.wordrelationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

//map id to word
//map word to connection
public class Graph<T> {
    //connecting to children
    private HashMap<String, Collection<String>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, String> connectionToDefinition;

    public Graph() {
        adjacentChildren = new HashMap<>();
        connectionToDefinition = new HashMap<>();
    }
    public void nameOfVertex(int id, String word) {
        connectionToDefinition.put(id,word);
    }
    public void addEdge(int id, int child) {
        String parentName = connectionToDefinition.get(id);
        String childName = connectionToDefinition.get(child);
        Collection<String> currentChildren = adjacentChildren.remove(parentName);
        currentChildren.add(childName);
        adjacentChildren.put(parentName, currentChildren);
    }
    public String getNodeName (int id) {
        return connectionToDefinition.get(id);
    }
    public Collection<String> getChildren (String parent) {
        return alphabetize(adjacentChildren.get(parent), parent);
    }
    private Collection<String> alphabetize(Collection<String> childrenUnordered, String parent) {
        List<String> allWords = new ArrayList<>();
        
    }
    public boolean existenceOfWord (String word) {
        return connectionToDefinition.containsValue(word);
    }

}

