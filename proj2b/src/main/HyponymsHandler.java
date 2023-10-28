package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


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
        List<String> kWords = new ArrayList<>();
        StringBuilder response = new StringBuilder();
        PriorityQueue<Double> quantity = new PriorityQueue<>();
        HashMap<Double, List<String>> quantityToString = new HashMap<>();

        for (String word : allWords) {
            //adding to priority queue backwards
            Double totalSum = totalFreq(ngm.countHistory(word, startYear, endYear));
            if (totalSum != 0) {
                quantity.add(-1 * totalSum);
                if (!quantityToString.containsKey(-1*totalSum)) {
                    quantityToString.put(-1*totalSum, List.of(word));
                }
                else {
                    List<String> addWord = new ArrayList<>(quantityToString.get(-1 * totalSum));
                    addWord.add(word);
                    quantityToString.put(-1*totalSum, addWord);
                }
            }
        }
        if (quantity.size() < k) {
            response.append(quantityToString.values());
        }
        else {
            while (k != 0) {
                Double maximum = quantity.remove();
                List<String> maximumList = new ArrayList<>(quantityToString.get(maximum));
                String maximumWord = maximumList.get(0);
                maximumList.remove(0);
                quantityToString.put(maximum, maximumList);
                k--;
                kWords.add(maximumWord);
            }
            Collections.sort(kWords);
            response.append(kWords);
        }

        return response.toString();
    }
    public Double totalFreq(TimeSeries historyOfWord) {

        Double sum = 0.0;
        for(Double value : historyOfWord.values()) {
            sum += value;
        }
        return sum;
    }
    }

