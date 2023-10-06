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
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0/2563919231.0);

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
        TimeSeries fishCount = ngm.countHistory("airport", 1850, 2009);
        assertThat(fishCount.get(2007)).isWithin(1E-10).of(175702.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("airport", 1850, 2009);
        assertThat(fishWeight.get(2007)).isWithin(1E-7).of(175702.0/28307904288.0);

        TimeSeries dogCount = ngm.countHistory("request", 1850, 2008);
        assertThat(dogCount.get(2006)).isWithin(1E-10).of(677820.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("airport");
        fishAndDog.add("request");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 2007, 2007);

        double expectedFishPlusDogWeight1865 = (175702.0 + 697645.0) / 28307904288.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
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
        TimeSeries requestAirport = requestCount.plus(airportRequest);
        assertThat(airportRequest.get(2007)).isWithin(1E-10).of(175702.0 + 697645.0);
        assertThat(requestAirport.get(2007)).isWithin(1E-10).of(175702.0 + 697645.0);
    }
    @Test
    public void testWeightHistory() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");
        TimeSeries weightedAirplane = ngm.weightHistory("airplane", 2007, 2008);
        weightedAirplane.years();
        List<Integer> yearsList = new ArrayList<>
                (Arrays.asList(2007, 2008));
        List<Double> resultingHistory = new ArrayList<>
                (Arrays.asList(175702.0 / 28307904288.0 , 173294.0/ 28752030034.0));
        for (int i = 0; i < yearsList.size(); i += 1) {
            assertThat(weightedAirplane.data().get(i)).isWithin(1E-10).of(resultingHistory.get(i));
            assertThat(weightedAirplane.years().get(i)).isEqualTo(yearsList.get(i));
        }
    }

}
