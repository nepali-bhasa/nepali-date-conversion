package com.bsadutil.dict;
import com.bsadutil.core.Ymd;
import com.bsadutil.error.OutOfBoundError;

public class Ad implements Dictionary {

    private static int[][] data = {
        {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 365},
        {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 366}
    };

    private static Ymd min = new Ymd(1944, 1, 1);
    private static Ymd max = new Ymd(2034, 4, 12);

    @Override
    public int get(int y) {
        return get(y, 13);
    }

    @Override
    public int get(int y, int m) {
        assertMonth(m);
        return Ad.data[isLeapYear(y) ? 1 : 0][m - 1];
    }

    @Override
    public boolean isLeapYear(int y) {
        return ((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0);
        // boolean isLeap = (get(y, 13) == 366);
        // return isLeap;
    }

    @Override
    public Ymd max() {
        return Ad.max;
    }

    @Override
    public Ymd min() {
        return Ad.min;
    }

    // validation for month
    private static void assertMonth(int m) {
        if (m > 12 + 1 || m <= 0)
            throw new OutOfBoundError();
    }

}
