package CEOI;

import java.io.*;
import java.util.*;

public class ceoi16p2 {
    static final int MOD = 1_000_000_007;

    static long add(long x, long y) {
        long z = (x + y) % MOD;
        if (z < 0) z += MOD;
        return z;
    }

    static long mul(long x, long y) {
        long z = (x % MOD) * (y % MOD) % MOD;
        if (z < 0) z += MOD;
        return z;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int a = fs.nextInt();
        int b = fs.nextInt();

        long[][] dp = new long[n + 3][n + 3];
        dp[1][1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (i == a || i == b) {
                    dp[i][j] = add(dp[i - 1][j - 1], dp[i - 1][j]);
                } else {
                    long part1 = mul(dp[i - 1][j + 1], j);
                    int sub = 0;
                    if (i > a) sub++;
                    if (i > b) sub++;
                    int coeff = j - sub;
                    long coeffMod = ((coeff % MOD) + MOD) % MOD;
                    long part2 = mul(dp[i - 1][j - 1], coeffMod);
                    dp[i][j] = add(part1, part2);
                }
            }
        }

        System.out.println(dp[n][1]);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { in = is; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return neg ? -val : val;
        }
    }
}
