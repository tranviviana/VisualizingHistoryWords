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
        int k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> allWords = hg.hyponyms(words);
        ArrayList<String> kWords = new ArrayList<>();
        StringBuilder response = new StringBuilder();
        PriorityQueue<Double> quantity = new PriorityQueue<>();
        HashMap<Double, ArrayList<String>> quantityToString = new HashMap<>();
        if (k == 0) {
            response.append(allWords);
            return response.toString();
        }

        for (String word : allWords) {
            //adding to priority queue backwards
            Double totalSum = totalFreq(ngm.countHistory(word, startYear, endYear));
            if (totalSum != 0.0) {
                quantity.add(-1 * totalSum);
                if (!quantityToString.containsKey(-1 * totalSum)) {
                    ArrayList<String> associatedWord = new ArrayList<>();
                    associatedWord.add(word);
                    quantityToString.put(-1 * totalSum, associatedWord);
                } else {
                    quantityToString.get(-1 * totalSum).add(word);
                    quantityToString.put(-1 * totalSum, quantityToString.get(-1 * totalSum));
                }
            }
        }
        while (k != 0 && !quantity.isEmpty()) {
            Double maximum = quantity.remove();
            String maximumWord = quantityToString.get(maximum).remove(0);
            quantityToString.put(maximum, quantityToString.get(maximum));
            kWords.add(maximumWord);
            k--;
        }
        Collections.sort(kWords);
        response.append(kWords);
        return response.toString();
    }
    public Double totalFreq(TimeSeries historyOfWord) {
        Double sum = 0.0;
        for (Double value : historyOfWord.values()) {
            sum += value;
        }
        return sum;
    }
}

