package rank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class test {

    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    public static double pdf(double x, double mu, double sigma) {
        return pdf((x - mu) / sigma) / sigma;
    }

    // return cdf(z) = standard Gaussian cdf using Taylor approximation
    public static double cdf(double z) {
        if (z < -8.0) return 0.0;
        if (z >  8.0) return 1.0;
        double sum = 0.0, term = z;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * z * z / i;
        }
        return 0.5 + sum * pdf(z);
    }

    // return cdf(z, mu, sigma) = Gaussian cdf with mean mu and stddev sigma
    public static double cdf(double z, double mu, double sigma) {
        return cdf((z - mu) / sigma);
    }

    public static double cdf2(double z, double mu, double variance) {
        double variable = (z - mu) / Math.sqrt(variance);
        if (variable < -8.0) return 0.0;
        if (variable >  8.0) return 1.0;
        double sum = 0.0, term = variable;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * Math.pow((z - mu), 2) / (i * variance);
        }
        double pdf = 0.5 + sum * Math.exp( - Math.pow((z- mu),2)/(2 * variance)) / Math.sqrt(2 * Math.PI);
        return pdf;
    }

    public static void main(String[] args) throws ParseException {
       double x1 = cdf(1.5, 2.0, 2.0);
       double x = cdf2(2, 2.0, 4.0);
       double y = cdf2(2.5, 2.0, 4.0);
       double Prediction = cdf2(2.5, 2.0, 4.0) - cdf2( 1.5, 2.0, 4.0);
       Calculator calculator = new Calculator();
       double z = calculator.calculateCDF(2.5, 2.0, 4.0);
       System.out.println(x1);
       System.out.println("------------");
       System.out.println(x);
       System.out.println("------------");
       System.out.println(y);
       System.out.println("------------");
       System.out.println(z);
       // System.out.println(String.format("%.2f", x * 100) + "%" );
       System.out.println("------------");
       System.out.println(Prediction);
//        Random random = new Random();
//        int a = 0;
//        int b = 0;
//        int c = 0;
//        int d = 0;
//        int e = 0;
//        int f = 0;
//        int g = 0;
//        int h = 0;
//        int j = 0;
//        for(int i = 0; i < 100000; i++){
//            double x = Math.sqrt(1) * random.nextGaussian() + 2;
//            int y = (int)Math.round(x);
//            if(y == -2){
//                a++;
//            } else if ( y == -1 ){
//                b++;
//            } else if ( y == 0 ){
//                c++;
//            } else if ( y == 1 ){
//                d++;
//            } else if ( y == 2 ){
//                e++;
//            } else if ( y == 3 ){
//                f++;
//            } else if ( y == 4 ){
//                g++;
//            } else if ( y == 5 ){
//                h++;
//            } else if ( y == 6 ){
//                j++;
//            }
//        }

       // System.out.println("**a " + a + "**b " + b + "**c " + c + "**d " + d + "**e " + e  + "**f " + f + "**g " + g + "**h " + h + "**j" + j);
        Random random = new Random();
       for(int i = 0; i < 100000; i++) {
           int predictScoreDiff = 0;
           do{
               double predictScoreDiffDouble = Math.sqrt(4) * random.nextGaussian() + 2;
               predictScoreDiff = (int)Math.round(predictScoreDiffDouble);
           }while(predictScoreDiff > 4 || predictScoreDiff < 0);
           System.out.println(predictScoreDiff);
       }
    }

}
