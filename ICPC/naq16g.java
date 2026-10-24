package ICPC;

import java.io.*;
import java.math.BigInteger;

public class naq16g {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int l = s.length();
        if (l <= 10000) {
            BigInteger target = new BigInteger(s);
            BigInteger fact = BigInteger.ONE;
            int i = 0;
            for (i = 1; ; i++) {
                fact = fact.multiply(BigInteger.valueOf(i));
                if (fact.compareTo(target) >= 0) break;
            }
            System.out.println(i);
        } else {
            double digs = 0.0;
            int i = 0;
            while (true) {
                i++;
                digs += Math.log10(i);
                if (digs >= l) break;
            }
            System.out.println(i - 1);
        }
    }
}