package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;
public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap files;
    private TimeSeries timeSeriesPrinted;
    public HistoryTextHandler(NGramMap map) {
        files = map;
        timeSeriesPrinted = new TimeSeries();

    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();
        for (String word : words) {
            response.append(word).append(": ");
            timeSeriesPrinted = files.weightHistory(word, startYear, endYear);
            response.append(relativePopularityHistory(timeSeriesPrinted));
            response.append("\n");

        }
        return response.toString();
    }

    public String relativePopularityHistory(TimeSeries information) {
        return information.toString();
    }
}
