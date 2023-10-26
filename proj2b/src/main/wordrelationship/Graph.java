package main.wordrelationship;

import com.google.common.collect.Sets;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.*;


//ADJACENCY LIST APPROACH

//map id to word
//map word to connection
public class Graph {
    private HashMap<Integer, List<Integer>> adjacentChildren;
    //each "node" is a name
    private HashMap<Integer, List<String>> connectionToDefinition;
    private HashMap<String, List<Integer>> connectionToId;
    private List<Integer> totalIds;

    public Graph() {
        this.adjacentChildren = new HashMap<>();
        this.connectionToDefinition = new HashMap<>();
        this.connectionToId = new HashMap<>();
        this.totalIds = new ArrayList<>();
    }

    //adds the name of the word to accessible key
    public void addDefinitionSingle(int id, String word) {
        if (!connectionToDefinition.containsKey(id)) {
            List<String> definitionList = new ArrayList<>();
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
            List<Integer> idList = new ArrayList<>();
            idList.add(id);
            connectionToId.put(word, idList);
        }
        if (!connectionToId.get(word).contains(id)) {
            connectionToId.get(word).add(id);
        }
    }

    public void individualAddRelationships(int mainNumber, int number) {
        if (!adjacentChildren.containsKey(mainNumber)) {
            List<Integer> childrenIds = new ArrayList<>();
            childrenIds.add(number);
            adjacentChildren.put(mainNumber, childrenIds);

        }
        if (!adjacentChildren.get(mainNumber).contains(number)) {
            adjacentChildren.get(mainNumber).add(number);
        }
    }
    //.contains is not it. need to check that not equal to each other
    //if more than one find the tallest family and then the shortest family and do retain all in that
    public List<String> getSimilarFamily (List<String> parents) {
        List<String> initialFamily = getFamily(parents.get(0));
        parents = removeRepeats(parents);
        int maximumSize = 0;
        int minimumSize = getFamily(parents.get(0)).size();
        List<String> minimumFamily = initialFamily;
        List<String> maximumFamily = initialFamily;
        for (String i : parents) {
            if (getFamily(i).size() > maximumSize) {
                maximumFamily = getFamily(i);
                maximumSize = getFamily(i).size();
            } else if (getFamily(i).size() < minimumSize) {
                minimumFamily = getFamily(i);
                minimumSize = getFamily(i).size();
            }
            else {
                maximumFamily.retainAll(getFamily(i));
            }
        }
        maximumFamily.retainAll(minimumFamily);
        return maximumFamily;


    }

    //looks at all the occurences and works down from their
    //works for individual words
    private List<String> getFamily(String parentNode) {
        List<Integer> occurrences = new ArrayList<>();
        if (connectionToId.get(parentNode) == null) {
            return new ArrayList<>();
        }
        occurrences.addAll(connectionToId.get(parentNode));
        for (int sibling : occurrences) {
            getChildren(sibling);
        }
        return getNames(totalIds);
    }
    //working down from all the occurences nodes
    private void getChildren(int siblingId) {
        totalIds.add(siblingId);
        if (adjacentChildren.get(siblingId) == null) {
            return;
        }
        for (int grandchild : adjacentChildren.get(siblingId)) {
            getChildren(grandchild);
        }
    }

    //convert all the ids to names and sorts them
    private List<String> getNames(List<Integer> totalIds) {
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
    //creates an array of unique items
    private List<String> removeRepeats(List<String> allWords) {
        List<String> uniqueWords = new ArrayList<>();
        for (int i = 0; i < allWords.toArray().length; i++) {
            if (!uniqueWords.contains(allWords.get(i))) {
                uniqueWords.add(allWords.get(i));
            }
        }
        return uniqueWords;
    }
}


