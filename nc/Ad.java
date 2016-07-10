package nc;

public class Ad implements Dictionary {

    private static int[][] data = {
        {31,28,31,30,31,30,31,31,30,31,30,31,365},
        {31,29,31,30,31,30,31,31,30,31,30,31,366}
    };

    private static Ymd min = new Ymd(1944, 1, 1);
    private static Ymd max = new Ymd(2034, 4, 12);

    public static boolean isLeapYear(int y) {
        return ((y%4 == 0) && (y%100 != 0)) || (y%400 == 0);
    }

    public int get(int y) throws OutOfBoundError {
        return get(y, 13);
    }

    public int get(int y, int m) throws OutOfBoundError {
        assertMonth(m);
        return data[isLeapYear(y)?1:0][m-1];
    }

    public Ymd max() {
        return max;
    }

    public Ymd min() {
        return min;
    }

    private static void assertMonth(int m) throws OutOfBoundError {
        if (m> 12+1 || m <= 0)
            throw new OutOfBoundError();
    }

}
