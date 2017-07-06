package com.bsadutil.core;
import com.bsadutil.error.BadFormatError;

import java.lang.String;

public class Ymd {

    protected int y;
    protected int m;
    protected int d;

    public String toString() {
        return String.format("%1$d-%2$02d-%3$02d", this.y, this.m, this.d);
    }

    // yyyy-mm-dd format
    public Ymd(String date) {
        String nepaliDate[] = date.split("-");
        if (nepaliDate.length != 3)
            throw new BadFormatError();
        this.y = Integer.parseInt(nepaliDate[0]);
        this.m = Integer.parseInt(nepaliDate[1]);
        this.d = Integer.parseInt(nepaliDate[2]);
    }

    public Ymd(int y, int m, int d) {
        this.y = y;
        this.m = m;
        this.d = d;
    }

    public Ymd(Ymd ymd) {
        this.y = ymd.y;
        this.m = ymd.m;
        this.d = ymd.d;
    }

    public int year() {
        return this.y;
    }

    public int month() {
        return this.m;
    }

    public int day() {
        return this.d;
    }

    public boolean isEqual(Ymd ymd) {
        return (this.y == ymd.y) && (this.m == ymd.m) && (this.d == ymd.d);
    }

    public boolean isNotEqual(Ymd ymd) {
        return !isEqual(ymd);
    }

    public boolean isLessThan(Ymd ymd) {
        return (this.y < ymd.y) || (this.y == ymd.y && this.m < ymd.m) ||
               (this.y == ymd.y && this.m == ymd.m && this.d < ymd.d);
    }

    public boolean isGreaterThan(Ymd ymd) {
        return !isLessThan(ymd);
    }

    public boolean isLessThanOrEqual(Ymd ymd) {
        return isEqual(ymd) || isLessThan(ymd);
    }

    public boolean isGreaterThanOrEqual(Ymd ymd) {
        return isEqual(ymd) || isGreaterThan(ymd);
    }

}
