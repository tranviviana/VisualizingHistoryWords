package ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private TimeSeries wordsPerYear;
    private HashMap mapOfAllTimes;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFileName, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsPerYear = new TimeSeries();
        totalWordCounter(countsFilename);
        In in = new In(wordsFileName);
        mapOfAllTimes = new HashMap();
        inputtingIndividualWords(in);


    }
    /*creates a time-series for a specific word*/
    private void inputtingIndividualWords(In in) {
        String nextLine = in.readLine();
        String[] splitLine = nextLine.split("\t");
        String previousWord = splitLine[0];
        while (in.hasNextLine()) {
            TimeSeries wordTimeSeries = new TimeSeries();
            while (splitLine[0].equals(previousWord)) {
                wordTimeSeries.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
                previousWord = splitLine[0];
                if (!in.hasNextLine()) {
                    break;
                }
                nextLine = in.readLine();
                splitLine = nextLine.split("\t");
            }
            mapOfAllTimes.put(previousWord, wordTimeSeries);
            previousWord = splitLine[0];
        }
    }



    /*creates a timeseries that associates years to the quantity of words from that year*/
    private void totalWordCounter(String countsFileName){
        In in = new In(countsFileName);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            wordsPerYear.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries associatedTimeSeries = (TimeSeries) mapOfAllTimes.get(word);
        return new TimeSeries(associatedTimeSeries, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.'
        TimeSeries associatedTimeSeries = new TimeSeries();
        associatedTimeSeries.putAll((TimeSeries) mapOfAllTimes.get(word));
        return associatedTimeSeries;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries associatedTimeSeries = new TimeSeries();
        associatedTimeSeries.putAll(wordsPerYear);
        return associatedTimeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return (countHistory(word, startYear, endYear).dividedBy(totalCountHistory()));
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        weightHistory(word, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
        return null;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    //has a size method
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries summedTimeSeries = new TimeSeries();
        for (String word : words) {
            summedTimeSeries = summedTimeSeries.plus(countHistory(word, startYear, endYear) );
        }
        return summedTimeSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
