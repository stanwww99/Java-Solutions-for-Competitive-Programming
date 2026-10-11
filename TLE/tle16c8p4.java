package TLE;
import java.io.*;
import java.util.*;

public class tle16c8p4 {
    static final int MOD = 1000000007;

    static long md(long x) {
        return ((x % MOD) + MOD) % MOD;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken());

        if (X == 1) {

            st = new StringTokenizer(br.readLine());
            int D = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());

            int[] fuel = new int[D + 2];
            fuel[0] = N;


            for (int i = 0; i < P; i++) {
                st = new StringTokenizer(br.readLine());
                int q = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                fuel[q] = r;
            }

            long[] dp = new long[D + 2];

            dp[D] = 1;

            for (int i = D - 1; i >= 0; i--) {
                int bound = Math.min(i + 1 + fuel[i], D + 1);
                dp[i] = md(dp[i + 1] * 2 - dp[bound]);
            }


            System.out.println(md(dp[0] - dp[1]));
        }
        else {
            System.out.println("69 1 32");
            st = new StringTokenizer(br.readLine());
            long w = Long.parseLong(st.nextToken());
            w <<= 1;
            for (int i = 1; i <= 32; i++) {
                if ((w & (1L << (i - 1))) != 0) {
                    System.out.println(i + " " + 68);
                } else {
                    System.out.println(i + " " + 32);
                }
            }
        }
    }
}
