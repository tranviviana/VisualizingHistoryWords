import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;
import ngrams.NGramMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static edu.princeton.cs.algs4.StdOut.print;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String MEDIUMWORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";
    public static final String funkyCSV = "data/ngrams/frequency_tester.csv";


    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String funkytotals = "data/ngrams/funkytotal_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    public static final String funky20 = "data/wordnet/synsets20.txt";
    public static final String funky20hyponyms = "data/wordnet/hyponyms20.txt";

    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testFirstEmpty() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("bob", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testSecondEmpty() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "bob");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void testMultipleWords() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change", "transition");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[jump, leap, saltation, transition]";
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void testbottomSimilarities() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("jump", "conversion", "mutation");
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }


    // TODO: Add more unit tests (including edge case tests) here.

    // TODO: Create similar unit test files for the k != 0 cases.
    @Test
    public void testCake() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "cake");
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[biscuit, cake, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testPopularity() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "cake");
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        NGramMap ngm = new NGramMap(WORDS_FILE, TOTAL_COUNTS_FILE);
        print(ngm.countHistory("biscuit",1950,1990).get(1990));
    }
    @Test
    public void testEmbezzlement() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("deed", "embezzlement");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019 , 6);
        String actual = studentHandler.handle(nq);
        String expected = "[raid]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testStatus() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("status", "strait");
        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980 , 7);
        String actual = studentHandler.handle(nq);
        String expected = "[pass]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testGenus() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("genus");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019 , 9);
        String actual = studentHandler.handle(nq);
        String expected = "[genus]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testBeing() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                MEDIUMWORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("being", "terpsichorean");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019 , 2);
        String actual = studentHandler.handle(nq);
        String expected = "[dancer]";
        assertThat(actual).isEqualTo(expected);
    }
//    @Test
//    public void test20() {
//        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
//                funkyCSV, funkytotals, funky20, funky20hyponyms);
//        List<String> words = List.of("AAAA");
//        NgordnetQuery nq = new NgordnetQuery(words, 1400, 202020,3);
//        String actual = studentHandler.handle(nq);
//        String expected = "[DDDD, EEEE, FFFF]";
//        assertThat(actual).isEqualTo(expected);
//    }


}
