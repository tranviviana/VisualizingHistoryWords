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
    private Graph synsets;

    public HyponymsGraph(String hyponymsFiles, String synsetsFiles) {
        this.synsets = new Graph<>();
        In relationIn = new In(hyponymsFiles);
        relationshipCreator(relationIn);
        In synIn = new In(synsetsFiles);
        definitionCreator(synIn);

    }

    private void relationshipCreator(In relationIn) {
        String nextLine = relationIn.readLine();
        while (relationIn.hasNextLine()) {
            String[] splitLine = nextLine.split(",");
            int currentIndex = 1;
            int sizeSplit = splitLine.length;
            synsets.
        }


    }
    private void definitionCreator(In synIn) {

    }

}
