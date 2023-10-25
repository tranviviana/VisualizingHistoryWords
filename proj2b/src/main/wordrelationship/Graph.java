package main.wordrelationship;

import org.checkerframework.checker.units.qual.A;

import java.util.*;


//ADJACENCY LIST APPROACH

//map id to word
//map word to connection
public class Graph<T> {
    private Collection<Integer> childrenIds;
    private HashMap<Integer, Collection<Integer>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, Collection<String>> connectionToDefinition;
    private HashMap<String, Collection<Integer>> connectionToID;

    public Graph() {
        this.adjacentChildren = new HashMap<>();
        this.connectionToDefinition = new HashMap<>();
        this.connectionToID = new HashMap<>();
    }

    //adds the name of the word to accessible key
    public void addDefinitionSingle(int id, String word) {
        if (!connectionToDefinition.get(id).contains(word)) {
            connectionToDefinition.get(id).add(word);
        }
        addIDSingle(word, id);
    }
    //each word is connected to a certain id or multiple ids
    //adds the string's id to its occurences
    public void addIDSingle(String word, int id) {
        if (!connectionToID.get(word).contains(id)) {
            connectionToID.get(word).add(id);
        }
    }
    //adds the name of WORDSSSS to accessible key
    public void addDefinitionMultiple(int id, Collection<String> words) {
        connectionToDefinition.put(id, words);
        for (String word : words) {
            addIDSingle(word, id);
        }
    }

    private String getChildren(String parentNode) {
        Collection<Integer> occurrences = connectionToID.get(parentNode);
        Collection<Integer> totalIds = new ArrayList<>();
        List<String> allWords = new ArrayList<>();
        for (int sibling : occurrences) {
            totalIds.addAll(adjacentChildren.get(sibling));
        }
        totalIds.addAll(occurrences);
        for (int id : totalIds) {
            allWords.addAll(connectionToDefinition.get(id));
        }
        Collections.sort(allWords);
        return allWords.toString();
    }

}

