import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the NGramMap class.
 *  @author Josh Hug
 */
public class NGramMapTest {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(2005, 2006, 2007, 2008));
        List<Double> expectedCounts = new ArrayList<>
                (Arrays.asList(646179.0, 677820.0, 697645.0, 795265.0));

        TimeSeries request2005to2008 = ngm.countHistory("request");
        assertThat(request2005to2008.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2005to2008.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }

        expectedYears = new ArrayList<>
                (Arrays.asList(2006, 2007));
        expectedCounts = new ArrayList<>
                (Arrays.asList(677820.0, 697645.0));

        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertThat(request2006to2007.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2006to2007.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }
        List<Double> expectedTotalValues = new ArrayList<>
                (Arrays.asList(984.0, 117652.0, 328918.0, 20502.0));
        List<Integer> expectedTotalYears = new ArrayList<>
                (Arrays.asList(1470, 1472, 1475, 1476));

        TimeSeries yearlyCounts = ngm.totalCountHistory();
        for (int i = 0; i < expectedTotalValues.size(); i += 1) {
            assertThat(yearlyCounts.data().get(i)).isWithin(1E-10).of(expectedTotalValues.get(i));
            assertThat(yearlyCounts.years().get(i)).isEqualTo(expectedTotalYears.get(i));
        }
    }

    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/top_14377_words.csv",
                "./data/ngrams/total_counts.csv");

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertThat(fishCount.get(1865)).isWithin(1E-10).of(136497.0);
        assertThat(fishCount.get(1922)).isWithin(1E-10).of(444924.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0 / 2563919231.0);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertThat(dogCount.get(1865)).isWithin(1E-10).of(75819.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
    }

    @Test
    public void testOnSmallFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries airportCount = ngm.countHistory("airport", 1850, 2009);
        assertThat(airportCount.get(2007)).isWithin(1E-10).of(175702.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word airport in each year between 1850 and 2009.
        TimeSeries airportWeight = ngm.weightHistory("airport", 1850, 2009);
        assertThat(airportWeight.get(2007)).isWithin(1E-7).of(175702.0 / 28307904288.0);

        TimeSeries requestCount = ngm.countHistory("request", 1850, 2008);
        assertThat(requestCount.get(2006)).isWithin(1E-10).of(677820.0);

        List<String> airportAndRequest = new ArrayList<>();
        airportAndRequest.add("airport");
        airportAndRequest.add("request");
        TimeSeries airportRequestSummed = ngm.summedWeightHistory(airportAndRequest, 2007, 2008);
        TimeSeries randomSeries = new TimeSeries();
        List<Integer> resultingYears = (Arrays.asList(2007, 2008));
        List<Double> resultingWeight = (Arrays.asList((175702.0 + 697645.0) / 28307904288.0, (795265.0 + 173294.0) / 28752030034.0));
        for (int i = 0; i < airportRequestSummed.size(); i += 1) {
            assertThat(airportRequestSummed.data().get(i)).isWithin(1E-10).of(resultingWeight.get(i));
            assertThat(airportRequestSummed.years().get(i)).isEqualTo(resultingYears.get(i));
        }
    }

    @Test
    public void testAddingTwoWords() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries airportCount = ngm.countHistory("airport", 1850, 2009);
        assertThat(airportCount.get(2007)).isWithin(1E-10).of(175702.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);
        TimeSeries requestCount = ngm.countHistory("request", 1850, 2008);
        assertThat(requestCount.get(2006)).isWithin(1E-10).of(677820.0);
        TimeSeries airportRequest = airportCount.plus(requestCount);
        TimeSeries requestAirport = requestCount.plus(airportCount);
        assertThat(airportRequest.get(2007)).isWithin(1E-10).of(175702.0 + 697645.0);
        assertThat(requestAirport.get(2007)).isWithin(1E-10).of(175702.0 + 697645.0);
    }

    @Test
    public void testWeightHistory() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");
        TimeSeries weightedAirplane = ngm.weightHistory("airport", 2007, 2008);
        weightedAirplane.years();
        List<Integer> yearsList = new ArrayList<>
                (Arrays.asList(2007, 2008));
        List<Double> resultingHistory = new ArrayList<>
                (Arrays.asList(175702.0 / 28307904288.0, 173294.0 / 28752030034.0));
        for (int i = 0; i < yearsList.size(); i += 1) {
            assertThat(weightedAirplane.data().get(i)).isWithin(1E-10).of(resultingHistory.get(i));
            assertThat(weightedAirplane.years().get(i)).isEqualTo(yearsList.get(i));
        }
        TimeSeries weightedRequest = ngm.weightHistory("request");
        yearsList = (Arrays.asList(2005, 2006, 2007, 2008));
        resultingHistory = (Arrays.asList(646179.0 / 26609986084.0, 677820.0 / 27695491774.0, 697645.0 / 28307904288.0, 795265.0 / 28752030034.0));
        for (int i = 0; i < yearsList.size(); i += 1) {
            assertThat(weightedRequest.data().get(i)).isWithin(1E-10).of(resultingHistory.get(i));
            assertThat(weightedRequest.years().get(i)).isEqualTo(yearsList.get(i));
        }

    }

    @Test
    public void testSummedWeightHistory() {
        // creates an NGramMap from a small dataset
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");
    List<String> listOfString = (Arrays.asList("airport", "wandered"));
        TimeSeries airportAndWanderedWeighted = ngm.summedWeightHistory(listOfString,1900,2007);
        List<Integer> yearsList =(Arrays.asList(2005, 2006, 2007));
    List<Double> resultingHistory =(Arrays.asList(83769.0 / 26609986084.0 , 87688.0 / 27695491774.0 , (108634.0+175702) / 28307904288.0 ));
        for (int i = 0; i < yearsList.size();i +=1) {
        assertThat(airportAndWanderedWeighted.data().get(i)).isWithin(1E-10).of(resultingHistory.get(i));
        assertThat(airportAndWanderedWeighted.years().get(i)).isEqualTo(yearsList.get(i));
    }
}

}



