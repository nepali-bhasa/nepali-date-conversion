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

    // main function that is invoked
    public static void main(String[] args) {

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

        try {
            Ad ad = new Ad();
            Bs bs = new Bs();
            for (Pair<String, String> datePair : datePairs) {

                String nepali = datePair.a;
                String english = datePair.b;
                // Convert nepali to english
                {
                    Date<Bs> a = new Date<Bs>(nepali, bs);
                    Date<Ad> b = a.convertTo(ad);
                    if (!b.toString().equals(english)) {
                        System.out.println(b.toString() + " is not " + english);
                    }
                }
                // Convert english to nepali
                {
                    Date<Ad> a = new Date<Ad>(english, ad);
                    Date<Bs> b = a.convertTo(bs);
                    if (!b.toString().equals(nepali)) {
                        System.out.println(b.toString() + " is not " + english);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

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
