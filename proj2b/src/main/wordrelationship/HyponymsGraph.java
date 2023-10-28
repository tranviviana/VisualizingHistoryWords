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
            List<String> definition = List.of(splitLine[1].split(" "));
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
            wordToId.put(word, List.of(id));
        }
        wordToId.get(word).add(id);
    }
    //achieves the graph method and correlates the nodes to one another
    public void buildRelations(In relationshipFile) {
        while (relationshipFile.hasNextLine()) {
            String nextLine = relationshipFile.readLine();
            String[] splitLine = nextLine.split(",");
            for (int i = 1; i < splitLine.length; i += 1) {
                synsetGraph.addEdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]));
            }
        }
    }
    //converts ids to the names and alphabetizes
    public List<String> idToNames(List<Integer> ids) {
        List<String> totalNames = new ArrayList<>();
        for (int i : ids) {
            if (!compareItems(totalNames, i)) {
                totalNames.add(idToWord.get(i).toString());
            }
        }
        Collections.sort(totalNames);
        return totalNames;
    }
    //might need to optimize in the future
    //helper function that checks that a name already exists in the list
    private boolean compareItems (List<String> nameList, int currentId) {
        for (String name : nameList) {
            for (String currentNames : idToWord.get(currentId)) {
                if (name.equals(currentNames)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> hyponyms(List<String> words) {
        List<Integer> idsOfParents = new ArrayList<>();
        List<Integer> idsOfEveryone = new ArrayList<>();
        for (String word : words) {
            idsOfParents.addAll(wordToId.get(word));
        }
        for (int parent : idsOfParents) {
            idsOfEveryone.addAll(synsetGraph.allChildren(parent));
        }
        return idToNames(idsOfEveryone);
    }

//    public void buildRelationship(In relationFile) {
//        while (relationFile.hasNextLine()) {
//            String nextLine = relationFile.readLine();
//            String[] splitLine = nextLine.split(",");
//            List<Integer> children = new ArrayList<>();
//
//            int id = Integer.parseInt(splitLine[0]);
//            for (int i = 1; i < splitLine.length; i += 1) {
//                children.add(Integer.parseInt(splitLine[i]));
//            }
//            id
//        }
//    }
//    private Graph synsetsGraph;
//
//    public HyponymsGraph(String hyponymsFiles, String synsetsFiles) {
//        //this.synsetsGraph = new Graph();
//        In relationIn = new In(hyponymsFiles);
//        relationshipCreator(relationIn);
//        In synIn = new In(synsetsFiles);
//        definitionCreator(synIn);
//
//    }
//
//    private void relationshipCreator(In relationIn) {
//
//        while (relationIn.hasNextLine()) {
//            String nextLine = relationIn.readLine();
//            String[] split = nextLine.split(",");
//            int i = 1;
//            int sizeSplit = split.length;
//            while (i < sizeSplit) {
//                synsetsGraph.individualAddRelationships((Integer.parseInt(split[0])), Integer.parseInt(split[i]));
//                i++;
//            }
//
//        }
//
//
//
//    }
//    private void definitionCreator(In synIn) {
//        while (synIn.hasNextLine()) {
//            String nextLine = synIn.readLine();
//            String[] splitLine = nextLine.split(",");
//            int id = Integer.parseInt(splitLine[0]);
//            String[] definition = splitLine[1].split(" ");
//            for (String word : definition) {
//                synsetsGraph.addDefinitionSingle(id, word);
//            }
//        }
//    }
//    public List<String> hyponyms(List<String> parents) {
//        return synsetsGraph.getSimilarFamily(parents);
//    }

}
