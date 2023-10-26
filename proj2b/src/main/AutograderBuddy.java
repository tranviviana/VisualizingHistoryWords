package main;

import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        HyponymsGraph hg = new HyponymsGraph(hyponymFile, synsetFile);
        NGramMap ngm = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(hg, ngm);
    }

}
