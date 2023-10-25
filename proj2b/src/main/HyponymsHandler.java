package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.wordrelationship.Graph;
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
        for (String word : words) {
            response.append(hg.hyponyms(word));
        }
        return response.toString();
    }
}
