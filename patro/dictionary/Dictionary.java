package patro.dictionary;
import patro.Ymd;
import patro.error.OutOfBoundError;

public interface Dictionary {

    // get number of days in given month of a year
    public int get(int y, int m);

    // get number of days in given year
    public int get(int y);

    // get if the current year is leap year or not
    public boolean isLeapYear(int y);

    // get the minimum date of which data is available
    public Ymd min();

    // get the maximum date of which data is available
    public Ymd max();

}
