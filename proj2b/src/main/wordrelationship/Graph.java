package main.wordrelationship;

import org.checkerframework.checker.units.qual.A;

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
    private Collection<Integer> occurrences;
    public Graph() {
        this.adjacentChildren = new HashMap<>();
        this.connectionToDefinition = new HashMap<>();
        this.connectionToId = new HashMap<>();
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
    //adds the string's id to its occurences
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
//    //adds the name of WORDSSSS to accessible key
//    public void addDefinitionMultiple(int id, Collection<String> words) {
//        for (String word : words) {
//            addIDSingle(word, id);
//            addDefinitionSingle(id, word);
//        }
//    }
    //need to work till bottom***************************************************************************************** recursion?
    public Collection<String> getChildrenIds(Collection<String> parentNode) {
        if (parentNode == null) {
            return getChildrenNames(totalIds);
        }
        occurrences = connectionToId.get(parentNode);
        totalIds.addAll(occurrences);
        for (int sibling : occurrences) {
            Collection<String> siblingNames = connectionToDefinition.get(sibling);
            getChildrenIds(siblingNames);
        }

    }
    private Collection<String> getChildrenNames(Collection<Integer> totalIds) {
        List<String> allWords = new ArrayList<>();
        for (int id : totalIds) {
            allWords.addAll(connectionToDefinition.get(id));
        }
        if (!allWords.isEmpty()) {
            Collections.sort(allWords);
        }
        return allWords;
    }



    public void individualaddRelationships (int mainNumber, int number) {
        if (!adjacentChildren.containsKey(mainNumber)) {
            Collection<Integer> childrenIds = new ArrayList<>();
            childrenIds.add(number);
            adjacentChildren.put(mainNumber , childrenIds);

        }
        if (!adjacentChildren.get(mainNumber).contains(number)) {
            adjacentChildren.get(mainNumber).add(number);
        }
    }
//    public void addRelationships (int mainNumber, Collection<Integer> family) {
//        for (int number : family) {
//            individualaddRelationships(mainNumber, number);
//        }
//    }

}

