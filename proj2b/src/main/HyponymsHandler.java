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
        int endYear = q.endYear();
        int k = q.k();
        TreeMap<Double, String> quantityToResponse = new TreeMap<>();
        StringBuilder response = new StringBuilder();
        List<Double> quantity = new ArrayList<>();
        NavigableSet<Double> descendedKey;

        if (k == 0) {
            return response.append(hg.hyponyms(words)).toString();
        }
        List<String> unfilteredResponse = hg.hyponyms(words);
        List<String> filteredResponse = new ArrayList<>();
        for (String hyponymWord : unfilteredResponse) {
            quantity.add(totalFrequency(ngm.countHistory(hyponymWord, startYear, endYear)));
        }
        int i = 0;
        for (Double quant : quantity) {
            quantityToResponse.put(quant, unfilteredResponse.get(i));
            i++;
        }
        i = 1;
        descendedKey = quantityToResponse.descendingKeySet();
        for (Double key : descendedKey) {
            filteredResponse.add(quantityToResponse.get(key));
            if (i == k) {
                break;
            }
            i++;
        }
        Collections.sort(filteredResponse);
        response.append(filteredResponse);
        if (response.isEmpty()) {
            response.append("[]");
        }
        return response.toString();
    }
    public double totalFrequency(TimeSeries wordWeightHistory) {
        double totalValue = 0.0;
        for (int k : wordWeightHistory.keySet()) {
            totalValue += wordWeightHistory.get(k);
        }
        return totalValue;

    }

}
