package ngrams;

import java.lang.reflect.Array;
import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        int currentYear = startYear;
        while (currentYear <= endYear) {
            if (ts != null && ts.containsKey(currentYear)) {
                this.put(currentYear, ts.get(currentYear));
            }
            currentYear += 1;
        }
        // TODO: Fill in this constructor.
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> yearsList = new ArrayList<>(navigableKeySet());
        return yearsList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        List<Integer> yearList = years();
        for (int element : yearList) {
            dataList.add(get(element));
        }
        return dataList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries unsummedTimeSeries = new TimeSeries();
        if (ts == null && this == null) {
            return unsummedTimeSeries;
        }
        int i = MIN_YEAR;
        while (i <= MAX_YEAR) {
            if (containsKey(i) && ts.containsKey(i)) {
                unsummedTimeSeries.put(i, get(i) + ts.get(i));
            } else if (containsKey(i)) {
                unsummedTimeSeries.put(i, get(i));
            } else if (ts.containsKey(i)) {
                unsummedTimeSeries.put(i, ts.get(i));
            }
            i++;
        }
        return unsummedTimeSeries;
    }



    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    //in the beginning quotientTimeSeries has all of ts so if it doesnt contain our year throw an error.
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries quotientTimeSeries = new TimeSeries();
        quotientTimeSeries.putAll(ts);
        return dividedByHelper(quotientTimeSeries);
    }
    private TimeSeries dividedByHelper(TimeSeries quotientTimeSeries) {
        List<Integer> currentYears = years();

        TimeSeries completedSeries = new TimeSeries();
        for (int year: currentYears) {
            if (!quotientTimeSeries.containsKey(year)) {
                throw new IllegalArgumentException("nooooo");
            }
            else {
                double quotient = get(year) / quotientTimeSeries.get(year);
                completedSeries.put(year, quotient);
            }
        }
        return completedSeries;

    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
