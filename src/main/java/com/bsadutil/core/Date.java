package com.bsadutil.core;
import com.bsadutil.dict.Dictionary;
import com.bsadutil.error.OutOfBoundError;
import com.bsadutil.error.MonthExceededError;
import com.bsadutil.error.DayExceededError;

public class Date<T extends Dictionary> extends Ymd {

    T dict;

    // the total no. of days from the minimum date available
    protected int totalDays;

    public Date(String ymd, T dict) {
        super(ymd);
        this.dict = dict;
        checkConversionBounds();
        checkBounds();
        this.totalDays = difference(dict.min());
    }

    public Date(int y, int m, int d, T dict) {
        super(y, m, d);
        this.dict = dict;
        this.checkConversionBounds();
        this.checkBounds();
        this.totalDays = difference(dict.min());
    }

    public Date(Ymd ymd, T dict) {
        super(ymd);
        this.dict = dict;
        checkConversionBounds();
        checkBounds();
        this.totalDays = difference(dict.min());
    }

    public int totalDays() {
        return totalDays;
    }

    // get the week number using totalDays
    public int week() {
        return (this.totalDays + 6) % 7 + 1;
    }

    // get total number of days in current month
    public int daysInMonth() {
        return this.dict.get(this.y, this.m);
    }

    // get total number of days in current year
    public int daysInYear() {
        return this.dict.get(this.y);
    }

    // addition of number of days
    public Date<T> addDays(int no) {
        // return a clone of itself
        if (no == 0) {
            return new Date<T>(this.y, this.m, this.d, this.dict);
        }

        // local variables that can be changed
        int y = this.y;
        int m = this.m;
        int d = this.d;

        // Get number of days 'n' till end of current year
        // 1. days remaining in current month
        int n = dict.get(y, m) - d + 1;
        // 2. days remaining in following months
        for (int i = m + 1; i <= 12; ++i)
            n += dict.get(y, i);

        // If 'no' exceeds number of days in current year
        if (n <= no) {
            // Increment year
            ++y;
            while (n + dict.get(y) <= no) {
                n += dict.get(y);
                ++y;
            }
            // Increment month
            m = 1;
            while (n + dict.get(y, m) <= no && m < 12 ) {
                n += dict.get(y, m);
                ++m;
            }
            // Increment day
            d = 1;
            d += no - n;
        } else {
            // Get number of days 'n' till end of month
            n = dict.get(y, m) - d + 1;
            // If 'no' exceeds number of days in current month
            if (n <= no) {
                // Increment month
                ++m;
                while (n + dict.get(y, m) <= no) {
                    n += dict.get(y, m);
                    ++m;
                }
                // Increment day
                d = no - n + 1;
            } else {
                // Get day
                d += no;
            }
        }
        return new Date<T>(y, m, d, this.dict);
    }

    public Date<T> subtractDays(int no) {
        if (no == 0)
            return new Date<T>(this.y, this.m, this.d, this.dict);

        int y = this.y;
        int m = this.m;
        int d = this.d;

        // Get number of days 'n' till beginning of year
        // 1. get number of days elapsed in current month
        int n = d;
        // 2. get number of days elapsed in previous months
        for (int i = m - 1; i >= 1; --i)
            n += dict.get(y, i);

        // If 'no' exceeds number of days elapsed in current year
        if (n <= no ) {
            // Decrement year
            --y;
            while (n + dict.get(y) <= no) {
                n += dict.get(y);
                --y;
            }
            // Decrement month
            m = 12;
            while (n + dict.get(y, m) <= no && m > 1) {
                n += dict.get(y, m);
                --m;
            }
            // Decrement date
            d = dict.get(y, m) - (no - n);
        } else {
            // Get number of days elapsed in current month
            n = d;
            // If 'no' exceeds number of days elapsed in current month
            if (n <= no) {
                // Decrement month
                --m;
                while (n + dict.get(y, m) <= no) {
                    n += dict.get(y, m);
                    --m;
                }
                // Decrement date
                d = dict.get(y, m) - (no - n);
            } else {
                // Get date
                d = d - no;
            }
        }
        return new Date<T>(y, m, d, this.dict);
    }

    public int difference(Ymd sub) {
        int sign = 1;
        int y, m, d;
        int y1, m1, d1;

        if (this.isGreaterThan(sub)) {
            y = this.y;
            m = this.m;
            d = this.d;
            y1 = sub.y;
            m1 = sub.m;
            d1 = sub.d;
            sign = 1;
        } else {
            y1 = this.y;
            m = this.m;
            d = this.d;
            y = sub.y;
            m1 = sub.m;
            d1 = sub.d;
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

    public Date<T> addYears(int no) {
        int y = this.y + no;
        int m = this.m;
        // Fix overflown day
        int d = Math.min(this.d, this.dict.get(y, m));

        return new Date<T>(y, m, d, this.dict);
    }

    public Date<T> addMonths(int no) {
        // NOTE: 'no' can be more than 12
        int y = this.y + (this.m + no - 1) / 12;
        int m = (this.m + no - 1) % 12 + 1;
        // Fix overflown day
        int d = Math.min(this.d, this.dict.get(y, m));

        return new Date<T>(y, m, d, this.dict);
    }

    // Check if the date exceed the minimum and maximum boundary
    protected void checkConversionBounds() {
        if (this.isGreaterThan(this.dict.max()) ||
                this.isLessThan(this.dict.min()))
            throw new OutOfBoundError();
    }

    // Check if the date is valid or not
    protected void checkBounds() {
        if (this.m > 12)
            throw new MonthExceededError();
        if (this.d > dict.get(this.y, this.m))
            throw new DayExceededError();
    }

    public <M extends Dictionary>
    Date<M> convertTo(M z) {
        return new Date<M>(z.min(), z).addDays(this.totalDays());
    }

    public <M extends Dictionary>
    Date<M> firstDayOfMonthIn(M z) {
        return new Date<T>(this.y, this.m, 1, this.dict).convertTo(z);
    }

}
