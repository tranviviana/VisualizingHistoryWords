package main.wordrelationship;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


//ADJACENCY LIST APPROACH

//map id to word
//map word to connection
public class Graph<T> {
    //connecting to children
    private final int minimumID = 0;
    private int maxmiumID;
    /*childrenIds are a set of numbers that we are going to have to put in tandem with nodeNames to get the total list we are looking for*/
    /*nodeNames looks at one node SPECIFICALLY*/
    /*stringIds looks at the associated words and returns the ids that it shows up in*/
    private ArrayList<Integer> childrenIds;
    private ArrayList<String> nodeNames;
    private ArrayList<Integer> stringIds;
    private HashMap<Integer, Collection<Integer>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, Collection<String>> connectionToDefinition;
    private HashMap<String, Collection<Integer>> connectionToID;

    public Graph(int size) {
        adjacentChildren = new HashMap<>();
        connectionToDefinition = new HashMap<>();
        connectionToID = new HashMap();
        maxmiumID = size - 1;
    }
    public String nameOfVertex(int id) {
        return connectionToDefinition.get(id).toString();

    }
    public void addEdge(int id, int child) {
        Collection<String> parentName = connectionToDefinition.get(id);
        Collection<String> childName = connectionToDefinition.get(child);
        Collection<String> currentChildren = adjacentChildren.remove(parentName);
        currentChildren.add(childName.toString());
        adjacentChildren.put(parentName, currentChildren);
    }

   /* ??????? how to add multiple children?????? */
    public Collection<String> getChildren (String parent) {
        throw new UnsupportedOperationException();
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

