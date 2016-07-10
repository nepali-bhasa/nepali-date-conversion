package nc;

public interface Dictionary {

    // get number of days in given month of a year
    public int get(int y, int m) throws OutOfBoundError;

    // get number of days in given year
    public int get(int y) throws OutOfBoundError;

    // get the minimum date of which data is available
    public Ymd min();

    // get the maximum date of which data is available
    public Ymd max();

}
