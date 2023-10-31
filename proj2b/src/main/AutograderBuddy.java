package main;

import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        return new HyponymsHandler(new HyponymsGraph(hyponymFile, synsetFile), new NGramMap(wordFile, countFile));
    }

}
