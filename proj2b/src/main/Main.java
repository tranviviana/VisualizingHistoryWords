package main;

import browser.NgordnetServer;
import main.wordrelationship.HyponymsGraph;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);


        String hyponymFile = "./data/wordnet/hyponyms11.txt";
        String synsetsFile = "./data/wordnet/synsets11.txt";
        HyponymsGraph hg = new HyponymsGraph(hyponymFile, synsetsFile);


        hns.startUp();
        hns.register("hyponyms", new HyponymsHandler(hg, ngm));
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
