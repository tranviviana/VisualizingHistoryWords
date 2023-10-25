package main.wordrelationship;
import edu.princeton.cs.algs4.In;
import java.util.Collection;

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
    private Graph synsetsGraph;

    public HyponymsGraph(String hyponymsFiles, String synsetsFiles) {
        this.synsetsGraph = new Graph();
        In relationIn = new In(hyponymsFiles);
        relationshipCreator(relationIn);
        In synIn = new In(synsetsFiles);
        definitionCreator(synIn);

    }

    private void relationshipCreator(In relationIn) {
        String nextLine = relationIn.readLine();
        while (relationIn.hasNextLine()) {
            String[] splitLine = nextLine.split(",");
            int i = 1;
            int sizeSplit = splitLine.length;
            while (i < sizeSplit) {
                synsetsGraph.individualAddRelationships((Integer.parseInt(splitLine[0])), Integer.parseInt(splitLine[i]));
                i++;
            }
            nextLine = relationIn.readLine();
        }


    }
    private void definitionCreator(In synIn) {
        String nextLine = synIn.readLine();
        while (synIn.hasNextLine()) {
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String [] definition = splitLine[1].split(" ");
            for (String word : definition) {
                synsetsGraph.addDefinitionSingle(id, word);
            }
            nextLine = synIn.readLine();
        }
    }
    public Collection<String> hyponyms (String parent) {
        return synsetsGraph.getFamily(parent);
    }

}
