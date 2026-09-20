package MWC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mwc15c1p5 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String A = br.readLine().toLowerCase();
        String B = br.readLine().toLowerCase();
        long Asum = 0;
        for(int i = 0; i < A.length(); i++) {
            Asum += power(A.charAt(i) - 'a' + 1, i + 1, 10);
        }
        Asum %= 10;
        if(Asum == 0) {
            Asum = 10;
        }
        long Bsum = 0;
        for(int i = 0; i < B.length(); i++) {
            Bsum += power(B.charAt(i) - 'a' + 1, i + 1, 10);
        }
        Bsum %= 10;
        if(Bsum == 0) {
            Bsum = 10;
        }
        System.out.println(Asum + Bsum);

    }
    public static long power(long base, long exponent, long MOD) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent < 0) {
            return 0; // Or handle negative exponents as needed, e.g., 1.0 / power(base, -exponent) for doubles
        }
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result *= base;
                result %= MOD;
            }
            base *= base;
            base %= MOD;
            exponent /= 2;
        }
        return result;
    }
}
