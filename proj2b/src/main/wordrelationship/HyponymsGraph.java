package main.wordrelationship;
import edu.princeton.cs.algs4.In;
import java.util.Collection;
import java.util.HashMap;

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
        this.synsetsGraph = new Graph<>();
        In relationIn = new In(hyponymsFiles);
        relationshipCreator(relationIn);
        In synIn = new In(synsetsFiles);
        definitionCreator(synIn);

    }

    private void relationshipCreator(In relationIn) {
        String nextLine = relationIn.readLine();
        while (relationIn.hasNextLine()) {
            String[] splitLine = nextLine.split(",");
            int i = 0;
            int sizeSplit = splitLine.length;
            while (i < sizeSplit) {
                i++;
                synsetsGraph.individualaddRelationships((Integer.parseInt(splitLine[0])), Integer.parseInt(splitLine[i]));
            }
        }


    }
    private void definitionCreator(In synIn) {
        String nextLine = synIn.readLine();
        while (synIn.hasNextLine()) {
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String [] definition = splitLine[1].split(" ");
            for (String word : splitLine) {
                synsetsGraph.addDefinitionSingle(id, word);
            }
        }
    }
    
}
