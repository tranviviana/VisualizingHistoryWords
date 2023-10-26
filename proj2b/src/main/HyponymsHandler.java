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
        List<String> unfilteredResponse = new ArrayList<>();

        if (k == 0) {
            return response.append(hg.hyponyms(words)).toString();
        }
        unfilteredResponse.addAll(hg.hyponyms(words));
        for (String hyponymWord : unfilteredResponse) {
            quantity.add(totalFrequency(ngm.weightHistory(hyponymWord, startYear, endYear)));
        }
        int i = 0;
        for (Double quant : quantity) {
            quantityToResponse.put(quant, unfilteredResponse.get(i));
            i++;
        }
        i = 0;
        quantity = (List<Double>) quantityToResponse.descendingKeySet();
        while (i < k && !quantityToResponse.isEmpty()) {
            response.append(quantityToResponse.get(quantity.get(i)));
            i++;
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
