package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.Graph;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;


public class HyponymsHandler extends NgordnetQueryHandler {
    private final NGramMap files;
    private Graph graphPrintable;
   public HyponymsHandler(NGramMap map) {
       files = map;
       graphPrintable = new Graph();
   }
    @Override
    public String handle(NgordnetQuery q) {
        new
        List<String> words = q.words();
        StringBuilder response = new StringBuilder();
        for (String word : words) {

        }
        return null;
    }
}
