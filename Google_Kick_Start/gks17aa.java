package Google_Kick_Start;
import java.util.Scanner;

public class gks17aa {

    static final long MOD = 1000000007;
    static long r, c, res;
    static long rev2, rev6;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            int n_case = sc.nextInt();
            rev2 = modInverse(2, MOD);
            rev6 = modInverse(6, MOD);

            for (int cs = 1; cs <= n_case; cs++) {
                r = sc.nextLong();
                c = sc.nextLong();
                res = 0;

                if (r > c) {
                    long tmp = r;
                    r = c;
                    c = tmp;
                }

                r--;
                c--;
                long rc1 = (r + c + 1) % MOD;
                long c0 = (r * c % MOD + rc1) % MOD;
                long c1 = rc1 + 1;
                long answer = (c0 * sum(r) % MOD + cube(r) - c1 * square(r)) % MOD;

                System.out.printf("Case #%d: %d\n", cs, (answer + MOD) % MOD);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static long sum(long range) {
        return (range + 1) * range % MOD * rev2 % MOD;
    }

    static long square(long range) {
        return range * (range + 1) % MOD * (2 * range + 1) % MOD * rev6 % MOD;
    }

    static long cube(long range) {
        long res = sum(range);
        return res * res % MOD;
    }

    static long modInverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0)
            x1 += m0;

        return x1;
    }
}
