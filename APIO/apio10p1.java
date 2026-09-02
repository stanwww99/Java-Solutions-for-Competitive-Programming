package APIO;

import java.io.*;
import java.util.*;

public class apio10p1 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int n = fs.nextInt();
        long a = fs.nextLong();
        long b = fs.nextLong();
        long c = fs.nextLong();
        long[] x = new long[n+1];
        for (int i = 1; i <= n; i++) x[i] = fs.nextLong();

        long[] pre = new long[n+1];
        for (int i = 1; i <= n; i++) pre[i] = pre[i-1] + x[i];

        long[] dp = new long[n+1];
        Arrays.fill(dp, Long.MIN_VALUE / 4);
        dp[0] = 0L;
        int last = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = last; j < i; j++) {
                long sum = pre[i] - pre[j];
                long val = a * sum * sum + b * sum + c;
                if(dp[j] + val > dp[i]){
                    dp[i] = Math.max(dp[i], dp[j] + val);
                    last = j;
                }

            }
        }

        System.out.println(dp[n]);
    }

    // Fast scanner for large input
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Long.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
