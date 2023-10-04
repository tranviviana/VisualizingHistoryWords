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
//      clonedTime = (TimeSeries) ts.subMap(startYear, true, endYear, true);
        int currentYear = startYear;
        while (currentYear <= endYear) {
            if (ts.containsKey(currentYear)) {
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
        List<Integer> yearsList = new ArrayList<>();
        yearsList.addAll(navigableKeySet());
        return yearsList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        List<Integer> yearList = years();
        for (double element : yearList) {
            dataList.add(element);
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
        if (ts.firstKey() == null && firstKey() == null) {
            return null;
        }
        unsummedTimeSeries.putAll(ts);
        return addingSecond(unsummedTimeSeries);
    }
    private TimeSeries addingSecond(TimeSeries unsummedTimeSeries) {
        List<Integer> currentYears = years();
        for (int year: currentYears) {
            if (unsummedTimeSeries.containsKey(year)) {
                double summedValue = get(year) + unsummedTimeSeries.get(year);
                unsummedTimeSeries.put(year, summedValue);

            }
            else {
                unsummedTimeSeries.put(year, get(year));
            }
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
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        return null;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
