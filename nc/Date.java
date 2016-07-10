package nc;

public class Date<T extends Dictionary> extends Ymd {

    T dict;
    protected int totalDays;

    public Date(Ymd ymd, T dict) throws OutOfBoundError, MonthExceededError, DayExceededError {
        super(ymd);
        this.dict = dict;
        checkBounds();
        validate();
        totalDays = difference(dict.min());
    }

    public int totalDays() {
        return totalDays;
    }

    public int week() {
        return (totalDays+6)%7+1;
    }

    public int daysInMonth() throws OutOfBoundError {
        return dict.get(y, m);
    }

    public int daysInYear() throws OutOfBoundError {
        return dict.get(y);
    }

    public Date<T> addition(int no) throws OutOfBoundError, MonthExceededError, DayExceededError {
        if (no == 0)
            return new Date<T>(new Ymd(y, m, d), dict);

        int y = this.y;
        int m = this.m;
        int d = this.d;

        // Get number of days 'n' till end of year
        int n = dict.get(y, m) - d + 1;
        for (int i = m + 1; i <= 12; ++i)
            n += dict.get(y, i);

        // If 'no' exceeds current year
        if (n <= no) {

            // Get year
            ++y;
            while (n + dict.get(y) <= no) {
                n += dict.get(y);
                ++y;
            }
            // Get month
            m = 1;
            while (n + dict.get(y, m) <= no && m < 12 ) {
                n += dict.get(y, m);
                ++m;
            }
            // Get day
            d = 1;
            d += no - n;

        } else {

            // Get number of days 'n' till end of month
            n = dict.get(y, m) - d + 1;

            // If 'no' exceeds current month
            if (n < no) {
                // Get month
                ++m;
                while (n + dict.get(y, m) <= no) {
                    n += dict.get(y, m);
                    ++m;
                }
                // Get day
                d = no - n + 1;
            } else {
                // Get day
                d += no;
            }
        }
        return new Date<T>(new Ymd(y, m, d), dict);
    }

    public Date<T> difference(int no) throws OutOfBoundError, MonthExceededError, DayExceededError {
        if (no == 0)
            return new Date<T>(new Ymd(y, m, d), dict);

        int y = this.y;
        int m = this.m;
        int d = this.d;

        // Get number of days 'n' till beginning of year
        int n = d;
        for (int i = m - 1; i >= 1; --i)
            n += dict.get(y, i);

        // If 'no' exceeds current year
        if (n <= no ) {

            // Get year
            --y;
            while (n + dict.get(y) <= no) {
                n += dict.get(y);
                --y;
            }
            // Get month
            m = 12;
            while (n + dict.get(y, m) <= no && m > 1) {
                n += dict.get(y, m);
                --m;
            }
            // Get date
            d = dict.get(y, m) - (no - n);

        } else {

            // Get number of days 'n' till beginning of month
            n = d;

            // If 'no' exceeds current month
            if (n <= no) {
                // Get month
                --m;
                while (n + dict.get(y, m) <= no) {
                    n += dict.get(y, m);
                    --m;
                }
                // Get date
                d = dict.get(y, m) - (no - n);
            } else {
                // Get date
                d = d - no;
            }

        }
        return new Date<T>(new Ymd(y, m, d), dict);
    }

    public int difference(Ymd sub) throws OutOfBoundError {
        int sign = 1;
        int y, m, d;
        int y1, m1, d1;

        if (this.isGreaterThan(sub)) {
            y = this.y; m = this.m; d = this.d;
            y1 = sub.y; m1 = sub.m; d1 = sub.d;
            sign = 1;
        } else {
            y1 = this.y; m = this.m; d = this.d;
            y = sub.y; m1 = sub.m; d1 = sub.d;
            sign = -1;
        }

        int no = 0;
        // If the years differ
        if (y != y1) {

            // Get number of days 'no' till end of year
            no = dict.get(y1, m1) - d1 + 1;
            for (int i = (m1 + 1); i <= 12; ++i)
                no += dict.get(y1, i);

            // Get number of days till the required year
            for (int i = y1 + 1; i < y; i++)
                no += dict.get(i);

            // Get number of days till the required month
            for (int i = 1; i < m; ++i)
                no += dict.get(y, i);

            // Get number of days till the required date
            no += d;
            no--;

        }
        // If the months differ
        else if (m != m1) {

            // Get number of days 'no' till end of month
            no = dict.get(y, m1) - d1 + 1 ;

            // Get number of days till the required month
            for (int i = m1 + 1; i < m; ++i)
                no += dict.get(y, i);

            // Get number of days till the required date
            no += d;
            no--;

        } else {

            // Get number of days till the required date
            no = d - d1;

        }

        return sign * no;
    }

    protected void checkBounds() throws OutOfBoundError {
        if (this.isGreaterThan(dict.max()) || this.isLessThan(dict.min()))
            throw new OutOfBoundError();
    }

    protected void validate() throws OutOfBoundError, MonthExceededError, DayExceededError{
        if (m>12)
            throw new MonthExceededError();
        if (d>dict.get(y, m))
            throw new DayExceededError();
    }

    public <M extends Dictionary>
    Date<M> convertTo(M z) throws OutOfBoundError, MonthExceededError, DayExceededError {
        return new Date<M>(z.min(), z).addition(totalDays());
    }

}
