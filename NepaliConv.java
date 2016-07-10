import nc.*;

// TODO:
// Try to make Dictionary implemented classes use static methods only

public class NepaliConv {

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
            Date<Ad> y = x.convertTo(ad);

            System.out.println(Helper.anka(x.year(), true, 4));
            System.out.println(Helper.mahina(x.month(), true));
            System.out.println(Helper.anka(x.day(), true, 2));
            System.out.println(Helper.bar(x.week(), true));

        } catch (Exception e){
            System.out.println(e);
        }

    }
}

