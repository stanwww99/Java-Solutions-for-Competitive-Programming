package EGOI;
import java.io.*;
import java.util.*;

public class egoi23p6 {
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int F = fs.nextInt();
        long T = fs.nextLong();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) a[i] = fs.nextLong();

        if (T <= 0) {
            System.out.println(0);
            return;
        }

        int maxCost = N * (N - 1) / 2;
        final long NEG = Long.MIN_VALUE / 4;
        long[][] dp = new long[F + 1][maxCost + 1];
        for (int p = 0; p <= F; p++) Arrays.fill(dp[p], NEG);
        dp[0][0] = 0L;

        for (int i = 0; i < N; i++) {
            int upperP = Math.min(i + 1, F);
            for (int p = upperP; p >= 1; p--) {
                int costForThis = i - (p - 1);
                if (costForThis < 0) continue;
                for (int k = 0; k + costForThis <= maxCost; k++) {
                    if (dp[p - 1][k] != NEG) {
                        long newSum = dp[p - 1][k] + a[i];
                        int nk = k + costForThis;
                        if (newSum > dp[p][nk]) dp[p][nk] = newSum;
                    }
                }
            }
        }

        int answer = -1;
        for (int k = 0; k <= maxCost; k++) {
            if (dp[F][k] >= T) { answer = k; break; }
        }
        if (answer == -1) System.out.println("NO");
        else System.out.println(answer);
    }
}
