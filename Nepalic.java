import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import patro.Date;
import patro.Ymd;
import patro.dictionary.Bs;
import patro.dictionary.Ad;
import patro.Translator;

class Pair<A, B> {
    public A a;
    public B b;
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
}

public class Nepalic {

    public static void assertUnreachable(String expression) {
        System.out.println("FAIL: [" + expression + "]");
    }

    public static void assertEqual(Object stringObject, String expectation, String expression) {
        String reality = stringObject.toString();
        if (reality.equals(expectation)) {
            // System.out.print("PASS: [" + expression + "]    ");
            // System.out.println(reality);
        } else {
            System.out.print("FAIL: [" + expression + "]    ");
            System.out.println(reality + " != " + expectation);
        }
    }

    // main function that is invoked
    public static void main(String[] args) {
        Ad ad = new Ad();
        Bs bs = new Bs();

        // Test overflow error
        try {
            Date<Bs> np1 = new Date<Bs>("2012-13-12", bs);
            assertUnreachable("Month value is out of range");
        } catch (Exception ex) {
        }
        try {
            Date<Bs> np1 = new Date<Bs>(2012, 0, 12, bs);
            assertUnreachable("Month value is out of range");
        } catch (Exception ex) {
        }
        try {
            Date<Bs> np1 = new Date<Bs>(2012, 0, -12, bs);
            assertUnreachable("Day value is out of range");
        } catch (Exception ex) {
        }
        try {
            Date<Bs> np1 = new Date<Bs>("2012-12", bs);
            assertUnreachable("Supplied date is not properly formatted");
        } catch (Exception ex) {
        }

        // Test getDate<Bs>
        {
            Date<Bs> np = new Date<Bs>("2012-02-12", bs);
            assertEqual(np, "2012-02-12", "getDate<Bs>");
        }
        {
            Date<Bs> np = new Date<Bs>(2012, 2, 12, bs);
            assertEqual(np, "2012-02-12", "getDate<Bs>");
        }

        // Test addYears, addMonths
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addYears(5), "2078-11-10", "addYears: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-12-31", bs);
            assertEqual(np.addYears(5), "2078-12-30", "addYears: overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-12-31", bs);
            assertEqual(np.addYears(5), "2078-12-30", "addYears: overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-30", bs);
            assertEqual(np.addYears(1), "2074-11-30", "addYears: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2062-11-29", bs);
            assertEqual(np.addYears(1), "2063-11-29", "addYears: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addMonths(1), "2073-12-10", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addMonths(2), "2074-01-10", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addMonths(3), "2074-02-10", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addMonths(13), "2074-12-10", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2058-05-31", bs);
            assertEqual(np.addMonths(12), "2059-05-31", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2058-05-31", bs);
            assertEqual(np.addMonths(16), "2059-09-30", "addMonths: overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2056-09-30", bs);
            assertEqual(np.addMonths(24), "2058-09-29", "addMonths: overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-10", bs);
            assertEqual(np.addMonths(14), "2075-01-10", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2073-11-30", bs);
            assertEqual(np.addMonths(2), "2074-01-30", "addMonths: no overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2074-01-31", bs);
            assertEqual(np.addMonths(7), "2074-08-29", "addMonths: overflow");
        }
        {
            Date<Bs> np = new Date<Bs>("2074-04-32", bs);
            assertEqual(np.addMonths(4), "2074-08-29", "addMonths: overflow");
        }

        // Read date pairs
        List<Pair<String, String>> datePairs = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream("dates.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                String[] splitted = strLine.split(" ");
                datePairs.add(new Pair<String, String>(splitted[0], splitted[1]));
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        // Check date conversion for every date in 20 years
        try {
            for (Pair<String, String> datePair : datePairs) {

                String nepali = datePair.a;
                String english = datePair.b;
                // Convert nepali to english
                {
                    Date<Bs> a = new Date<Bs>(nepali, bs);
                    Date<Ad> b = a.convertTo(ad);
                    assertEqual(b, english, "convertToAD");
                }
                // Convert english to nepali
                {
                    Date<Ad> a = new Date<Ad>(english, ad);
                    Date<Bs> b = a.convertTo(bs);
                    if (!b.toString().equals(nepali)) {
                        System.out.println(b.toString() + " is not " + english);
                    }
                    assertEqual(b, nepali, "convertToBS");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ALL TEST COMPLETE");

        /*
        try {
        // Get Ad and Bs dictionaries for calendar data
        Ad ad = new Ad();
        Bs bs = new Bs();

        // Create a date using Ad data
        Date<Ad> a = new Date<Ad>(new Ymd(2016, 7, 10), new Ad());
        // Convert ad to bs
        Date<Bs> x = a.convertTo(bs);
        // Convert bs to ad
        // Date<Ad> y = x.convertTo(ad);


        System.out.println(Translator.anka(x.year(), true, 4)+" "+Translator.mahina(x.month(), true)
                           +" "+Translator.anka(x.day(), true, 2)+" "+Translator.bar(x.week(), true));

        Date<Ad> firstDayOfNepaliMonth = x.firstDayOfMonthIn(ad);

        // Fill gap
        for (int i=1;i<=firstDayOfNepaliMonth.week(); i++)
            System.out.print("-\t");

        // Fill text
        int k = firstDayOfNepaliMonth.day();
        for (int i=1; i<=x.daysInMonth(); i++, k++) {
            if (k>firstDayOfNepaliMonth.daysInMonth())
                k = 1;
            System.out.print(k+" "+Translator.anka(i, true, 2)+"\t");
        }
        System.out.println();


        } catch (Exception e){
        System.out.println(e);
        }
        */

    }
}
