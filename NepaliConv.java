import nc.*;

public class NepaliConv {

    // main function that is invoked
    public static void main(String[] args) {

        try {
            // Conversion
            Ad ad = new Ad();
            Bs bs = new Bs();
            Date<Ad> a = new Date<Ad>(new Ymd(2016, 7, 9), ad);
            Date<Bs> b = new Date<Bs>(bs.min(), bs);
            Ymd t = b.addition(a.totalDays());
            Date<Bs> x = new Date<Bs>(t, bs);

            System.out.println(Helper.anka(x.year(), true, 4));
            System.out.println(Helper.mahina(x.month(), true));
            System.out.println(Helper.anka(x.day(), true, 2));
            System.out.println(Helper.bar(x.week(), true));
        } catch (Exception e){
            System.out.println(e);
        }

    }
}

