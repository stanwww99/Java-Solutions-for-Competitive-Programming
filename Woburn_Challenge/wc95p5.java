package Woburn_Challenge;

import java.math.BigInteger;
import java.util.Scanner;
public class wc95p5 {
    public static BigInteger fact(int n) {
        BigInteger j = new BigInteger("1");
        for(int i = 1; i <= n; i++) {
            j = j.parallelMultiply(BigInteger.valueOf(i));
        }
        return j;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);

        for(int i = 0; i < 5; i++) {
            int n = s.nextInt();
            String k = fact(n).toString();
            System.out.println("The length of " + n + "! is " + k.length());
        }


        s.close();
    }

}
