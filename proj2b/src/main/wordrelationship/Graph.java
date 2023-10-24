package main.wordrelationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


//ADJACENCY LIST APPROACH

//map id to word
//map word to connection
public class Graph<T> {
    //connecting to children
    int minimumID = 0;
    int maxmiumID;
    private HashMap<Collection<String>, Collection<String>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, Collection<String>> connectionToDefinition;

    public Graph(int size) {
        adjacentChildren = new HashMap<>();
        connectionToDefinition = new HashMap<>();
        maxmiumID = size - 1;
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

   /* ??????? how to add multiple children?????? */
    public Collection<String> getChildren (String parent) {
        Collection<String> allWords = new ArrayList<>();
        for (int counter = 0; counter < maxmiumID; counter ++) {
            if (getNodeName(counter).contains(parent)) {
                allWords = allWords.(connectionToDefinition.get(counter))
            }
        }
        return alphabetize(adjacentChildren.get(parent), parent);
    }
    //need to alphabetize and what if the name is 2 words
    private Collection<String> alphabetize(Collection<String> childrenUnordered, String parent) {
        Collection<String> allWords = new ArrayList<>();
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

