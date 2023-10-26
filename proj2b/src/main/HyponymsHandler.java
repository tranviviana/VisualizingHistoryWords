package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;

import java.util.ArrayList;
import java.util.List;


public class HyponymsHandler extends NgordnetQueryHandler {
    private final HyponymsGraph hg;
    private final NGramMap ngm;

    public HyponymsHandler(HyponymsGraph hg, NGramMap ngm) {
        this.hg = hg;
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<String> unfilteredHyponym = new ArrayList<>();
        List<Integer> weightQuantity = new ArrayList<>();
        StringBuilder response = new StringBuilder();
        unfilteredHyponym.addAll(hg.hyponyms(words));
        for (String hyponymWord : unfilteredHyponym) {
            weightQuantity.add(ngm.weightHistory(hyponymWord, q.startYear(), q.endYear()).);
        }
        return response.toString();
    }
}
