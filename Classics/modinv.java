package classics;

import java.math.BigInteger;
import java.util.Scanner;
public class modinv {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);
        BigInteger N = new BigInteger(s.next());
        BigInteger M = new BigInteger(s.next());
        N = N.modInverse(M);
        System.out.println(N);
        s.close();
    }

}