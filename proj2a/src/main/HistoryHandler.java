package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap files;
    public HistoryHandler(NGramMap map) {
        files = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        TimeSeries respectiveGraph = new TimeSeries();
        q.words() = words;
        q.startYear() = startYear();
        q.endYear() = endYear();
        for (String word : words) {
            respectiveGraph = files.weightHistory(word, startYear, endYear);
            lts.add(respectiveGraph);
            labels.add(word);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);
    }

}
