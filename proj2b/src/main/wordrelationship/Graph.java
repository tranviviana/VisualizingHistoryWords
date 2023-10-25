package main.wordrelationship;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.*;


//ADJACENCY LIST APPROACH

//map id to word
//map word to connection
public class Graph {
    private HashMap<Integer, Collection<Integer>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, Collection<String>> connectionToDefinition;
    private HashMap<String, Collection<Integer>> connectionToId;
    private Collection<Integer> totalIds;
    public Graph() {
        this.adjacentChildren = new HashMap<>();
        this.connectionToDefinition = new HashMap<>();
        this.connectionToId = new HashMap<>();
        this.totalIds = new ArrayList<>();
    }

    //adds the name of the word to accessible key
    public void addDefinitionSingle(int id, String word) {
        if (!connectionToDefinition.containsKey(id)) {
            Collection<String> definitionList = new ArrayList<>();
            definitionList.add(word);
            connectionToDefinition.put(id, definitionList);
        }
        if (!connectionToDefinition.get(id).contains(word)) {
            connectionToDefinition.get(id).add(word);
        }
        addIDSingle(word, id);
    }
    //each word is connected to a certain id or multiple ids
    //adds the string's id to its occurrences
    private void addIDSingle(String word, int id) {
        if (!connectionToId.containsKey(word)) {
            Collection<Integer> idList = new ArrayList<>();
            idList.add(id);
            connectionToId.put(word, idList);
        }
        if (!connectionToId.get(word).contains(id)) {
            connectionToId.get(word).add(id);
        }
    }
    public void individualAddRelationships (int mainNumber, int number) {
        if (!adjacentChildren.containsKey(mainNumber)) {
            Collection<Integer> childrenIds = new ArrayList<>();
            childrenIds.add(number);
            adjacentChildren.put(mainNumber , childrenIds);

        }
        if (!adjacentChildren.get(mainNumber).contains(number)) {
            adjacentChildren.get(mainNumber).add(number);
        }
    }
    public Collection<String> getFamily(String parentNode) {
        Collection<Integer> occurrences = new ArrayList<>();
        occurrences.addAll(connectionToId.get(parentNode));
        for (int sibling : occurrences) {
            getChildren(sibling);
        }
        return getNames(totalIds);
    }
    private void  getChildren(int siblingId) {
        totalIds.add(siblingId);
        if (adjacentChildren.get(siblingId) == null) {
            return;
        }
        for (int grandchild: adjacentChildren.get(siblingId)) {
            getChildren(grandchild);
        }
    }
    //convert all the ids to names and sorts them
    private Collection<String> getNames(Collection<Integer> totalIds) {
        List<String> allWords = new ArrayList<>() {
        };
        for (int id : totalIds) {
            allWords.addAll(connectionToDefinition.get(id));
        }
        if (!allWords.isEmpty()) {
            Collections.sort(allWords);
        }
        return removeRepeats(allWords);
    }
    private Collection<String> removeRepeats(List<String> allWords) {
        Collection<String> uniqueWords = new ArrayList<>();
        for (int i = 0; i < allWords.toArray().length - 1; i += 1) {
            if (allWords.get(i).equals(allWords.get(i + 1))){
                uniqueWords.add(allWords.get(i + 1));
            }
            else {
                uniqueWords.add(allWords.get(i));
            }
        }
        return uniqueWords;

    }
}

