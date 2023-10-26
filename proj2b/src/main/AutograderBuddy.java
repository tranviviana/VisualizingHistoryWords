package main;

import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        HyponymsGraph hg = new HyponymsGraph(hyponymFile, synsetFile);
        return new HyponymsHandler(hg);
    }

}
