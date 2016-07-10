import patro.*;

// TODO:
// Try to make Dictionary implemented classes use static methods only

public class Nepalic {

    // main function that is invoked
    public static void main(String[] args) {

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


            System.out.println(Nepalify.anka(x.year(), true, 4)+" "+Nepalify.mahina(x.month(), true)
                               +" "+Nepalify.anka(x.day(), true, 2)+" "+Nepalify.bar(x.week(), true));

            Date<Ad> firstDayOfNepaliMonth = x.firstDayOfMonthIn(ad);

            // Fill gap
            for (int i=1;i<=firstDayOfNepaliMonth.week(); i++)
                System.out.print("-\t");

            // Fill text
            int k = firstDayOfNepaliMonth.day();
            for (int i=1; i<=x.daysInMonth(); i++, k++) {
                if (k>firstDayOfNepaliMonth.daysInMonth())
                    k = 1;
                System.out.print(k+" "+Nepalify.anka(i, true, 2)+"\t");
            }
            System.out.println();


        } catch (Exception e){
            System.out.println(e);
        }

    }
}

