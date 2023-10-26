package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.HyponymsGraph;

import java.util.List;


public class HyponymsHandler extends NgordnetQueryHandler {
    private final HyponymsGraph hg;

    public HyponymsHandler(HyponymsGraph hg) {
        this.hg = hg;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        StringBuilder response = new StringBuilder();
        response.append(hg.hyponyms(words));
        return response.toString();
    }
}
