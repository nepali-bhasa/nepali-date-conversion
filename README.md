# BsAdUtil

A library for date arithmetics and date conversion between Bikram Sambat Nepali Calendar and Gregorian Calendar.

## Building

The library uses maven as a build-tool.

    pacman -S maven

Get the sources:

    https://github.com/tnagorra/BsAdUtil.git

Build using maven:

    mvn clean package
    mvn exec:java -Dexec:mainClass="com.bsadutil.App"

## Example

```java
Bs bs = new Bs();
Ad ad = new Ad();

Date<Ad> englishDate;
Date<Bs> nepaliDate;
Date<Bs> anotherNepaliDate;

// Date conversion
englishDate = new Date<Ad>("2017-07-5", ad);
anotherNepaliDate = new Date<Bs>(2070, 10, 11, bs);

nepaliDate = englishDate.convertTo(bs);   // 2074-03-31
nepaliDate.addDays(120);                  // 2074-07-26
nepaliDate.addMonths(4);                  // 2074-07-30
nepaliDate.addYears(2);                   // 2076-03-31
nepaliDate.subtractDays(111);             // 2073-12-13
nepaliDate.difference(anotherNepaliDate); // 1267
nepaliDate.week();                        // 7
nepaliDate.daysInMonth();                 // 31
nepaliDate.daysInYear();                  // 365
```
