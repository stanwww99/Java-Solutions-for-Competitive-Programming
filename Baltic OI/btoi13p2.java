package Baltic_OI;

import java.io.*;
import java.util.*;

public class btoi13p2 {

    // dp[pos][tight][started][last1][last2]
    // last1, last2 in [0..9], 10 means "none"
    static long[][][][][] dp;
    static int[] digits;

    static long solve(long x) {
        if (x < 0) return 0;
        digits = toDigits(x);
        int n = digits.length;

        dp = new long[n + 1][2][2][11][11];
        for (int i = 0; i <= n; i++)
            for (int t = 0; t < 2; t++)
                for (int s = 0; s < 2; s++)
                    for (int a = 0; a < 11; a++)
                        Arrays.fill(dp[i][t][s][a], -1);

        return dfs(0, 1, 0, 10, 10);
    }

    static long dfs(int pos, int tight, int started, int last1, int last2) {
        if (pos == digits.length) {
            // number is formed; 0 is allowed (never started)
            return 1;
        }

        long memo = dp[pos][tight][started][last1][last2];
        if (memo != -1) return memo;

        long ans = 0;
        int limit = (tight == 1 ? digits[pos] : 9);

        for (int d = 0; d <= limit; d++) {
            int ntight = (tight == 1 && d == limit) ? 1 : 0;

            if (started == 0) {
                // still in leading zeros
                if (d == 0) {
                    // remain not started; no digits yet, so no palindrome checks
                    ans += dfs(pos + 1, ntight, 0, 10, 10);
                } else {
                    // first non-zero digit starts the number
                    ans += dfs(pos + 1, ntight, 1, d, 10);
                }
            } else {
                // already started: digits are part of the number, check palindromes

                // length-2 palindrome: aa
                if (last1 != 10 && d == last1) continue;

                // length-3 palindrome: aba
                if (last2 != 10 && d == last2) continue;

                ans += dfs(pos + 1, ntight, 1, d, last1);
            }
        }

        dp[pos][tight][started][last1][last2] = ans;
        return ans;
    }

    static int[] toDigits(long x) {
        String s = Long.toString(x);
        int[] a = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
            a[i] = s.charAt(i) - '0';
        return a;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long ans = solve(b) - solve(a - 1);
        System.out.println(ans);
    }
}
