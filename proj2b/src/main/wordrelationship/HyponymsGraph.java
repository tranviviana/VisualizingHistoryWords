package main.wordrelationship;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */

public class HyponymsGraph {
    private HashMap<Integer, List<String>> idToWord;
    private HashMap<String, List<Integer>> wordToId;
    private Graph synsetGraph;
    private int sizeOfGroup;
    public HyponymsGraph(String hyponymsFiles, String synsetsFiles) {
        this.wordToId = new HashMap<>();
        this.idToWord = new HashMap<>();
        In relationIn = new In(hyponymsFiles);
        In idToWord = new In(synsetsFiles);
        connectIdToWord(idToWord);
        buildRelations(relationIn);
    }
    //creates the graph and reads the file to map the ids to the definitions
    //calls the reverse of it
    public void connectIdToWord(In definitionFile) {
        while (definitionFile.hasNextLine()) {
            String nextLine = definitionFile.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            List<String> definition = new ArrayList<>(List.of(splitLine[1].split(" ")));
            for (String word : definition) {
                connectWordToId(word, id);
            }
            idToWord.put(id, definition);
            sizeOfGroup += 1;
        }
        synsetGraph = new Graph(sizeOfGroup);
    }
    //builds the relationship of occurences, if a word occurs in more nodes, it is mapped in this map
    //if its not already mapped add it if it is adjust the bin to have the id as well
    public void connectWordToId(String word, int id) {
        if (!wordToId.containsKey(word)) {
            wordToId.put(word, new ArrayList<>(List.of(id)));
        }
        else {
            List<Integer> addingNewId = new ArrayList<>(wordToId.get(word));
            addingNewId.add(id);
            wordToId.put(word, addingNewId);
        }
    }
    //achieves the graph method and correlates the nodes to one another
    public void buildRelations(In relationshipFile) {
        while (relationshipFile.hasNextLine()) {
            String nextLine = relationshipFile.readLine();
            String[] splitLine = nextLine.split(",");
            for (int i = 1; i < splitLine.length; i += 1) {
                synsetGraph.addEdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[i]));
            }
        }
    }
    //converts ids to the names and alphabetizes
    public List<String> idToNames(List<Integer> ids) {
        List<String> totalNames = new ArrayList<>();
        for (int i : ids) {
            for (String word : idToWord.get(i)) {
                if (!totalNames.contains(word)) {
                    totalNames.add(word);
                }
            }
        }
        Collections.sort(totalNames);
        return totalNames;
    }
    public List<String> hyponyms(List<String> words) {
        List<List<String>> totalNames = new ArrayList<>();
        if (words.size() == 1) {
            return idToNames(synsetGraph.allChildrenList(wordToId.get(words.get(0))));
        }
        else {
            for (String word : words) {
                totalNames.add(idToNames(synsetGraph.allChildrenList(wordToId.get(word))));
            }
            for (int i = 1; i < totalNames.size(); i++) {
                totalNames.get(0).retainAll(totalNames.get(i));
            }
        }
        return totalNames.get(0);
    }





}
