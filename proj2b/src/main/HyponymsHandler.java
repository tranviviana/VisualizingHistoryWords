package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;


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
        int startYear = q.startYear();
        int endYear = q.endYear();;
        int k = q.k();
        StringBuilder response = new StringBuilder();
        List<String> unfilteredResponse = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        List<Integer> originalIndices = new ArrayList<>();
        if (k == 0) {
            return response.append(hg.hyponyms(words)).toString();
        }
        unfilteredResponse.addAll(hg.hyponyms(words));
        for (String hyponymWord : unfilteredResponse) {
            quantity.add(totalFrequency(ngm.weightHistory(hyponymWord, startYear, endYear)));
            originalIndices.add(totalFrequency(ngm.weightHistory(hyponymWord, startYear, endYear)));
        }
        Collections.sort(quantity);
        if (quantity.size() > k) {
            int removalQuantity  = quantity.size() - k;
        }
        if (quantity.size() < k) {
            originalIndices.f
        }


        return response.toString();
    }
    public int totalFrequency (TimeSeries wordWeightHistory) {

    }
}
