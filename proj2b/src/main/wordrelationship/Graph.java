package main.wordrelationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

//map id to word
//map word to connection
public class Graph<T> {
    //connecting to children
    int minimumID = 0;
    int maxmiumID; 
    private HashMap<Collection<String>, Collection<String>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, Collection<String>> connectionToDefinition;

    public Graph() {
        adjacentChildren = new HashMap<>();
        connectionToDefinition = new HashMap<>();
    }
    public void nameOfVertex(int id, String word) {
        Collection<String> nameList = connectionToDefinition.remove(id);
        nameList.add(word);
        connectionToDefinition.put(id,nameList);
    }
    public void addEdge(int id, int child) {
        Collection<String> parentName = connectionToDefinition.get(id);
        Collection<String> childName = connectionToDefinition.get(child);
        Collection<String> currentChildren = adjacentChildren.remove(parentName);
        currentChildren.add(childName.toString());
        adjacentChildren.put(parentName, currentChildren);
    }
    public String getNodeName (int id) {
        return connectionToDefinition.get(id).toString();
    }
    public Collection<String> getChildren (String parent) {

        return alphabetize(adjacentChildren.get(parent), parent);
    }
    //need to alphabetize and what if the name is 2 words
    private Collection<String> alphabetize(Collection<String> childrenUnordered, String parent) {
        List<String> allWords = new ArrayList<>();
        for (String word : childrenUnordered) {
            allWords.add(word);
        }
        allWords.add(parent);
        return allWords;
    }
    public boolean existenceOfWord (String word) {
        return connectionToDefinition.containsValue(word);
    }

}

