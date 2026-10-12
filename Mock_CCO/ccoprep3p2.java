package Mock_CCO;

import java.io.*;
import java.util.*;


public class ccoprep3p2 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        long L = fs.nextLong();
        long M = L + 1L;

        long[] C = new long[N + 1];
        for (int i = 1; i <= N; ++i) C[i] = fs.nextLong();

        long[] S = new long[N + 1];
        for (int i = 1; i <= N; ++i) S[i] = S[i - 1] + C[i];

        long[] A = new long[N + 1];
        for (int i = 0; i <= N; ++i) A[i] = S[i] + i;

        long[] dp = new long[N + 1];
        // dp[0] = 0 by definition (no books packed)

        // Convex hull structures
        // store slopes and intercepts as long
        long[] m = new long[N + 1];
        long[] b = new long[N + 1];
        int head = 0, tail = 0; // deque is [head, tail)
        // add line for t = 0
        // m0 = -2*(A[0] + M), b0 = dp[0] + (A[0] + M)^2
        m[tail] = -2L * (A[0] + M);
        b[tail] = dp[0] + (A[0] + M) * (A[0] + M);
        tail++;

        for (int j = 1; j <= N; ++j) {
            long x = A[j];
            // query: find min m[i]*x + b[i]
            while (tail - head >= 2) {
                long v1 = eval(m[head], b[head], x);
                long v2 = eval(m[head + 1], b[head + 1], x);
                if (v2 <= v1) head++;
                else break;
            }
            long best = eval(m[head], b[head], x);
            dp[j] = x * x + best;

            // prepare new line for t = j
            long mt = -2L * (A[j] + M);
            long bt = dp[j] + (A[j] + M) * (A[j] + M);

            // add line (mt, bt) maintaining lower hull
            // remove last while intersection(new, last-1) <= intersection(last, last-1)
            while (tail - head >= 2) {
                int l1 = tail - 2; // last-1
                int l2 = tail - 1; // last
                if (isBad(m[l1], b[l1], m[l2], b[l2], mt, bt)) {
                    tail--; // drop last
                } else break;
            }
            m[tail] = mt;
            b[tail] = bt;
            tail++;
        }

        System.out.println(dp[N]);
    }

    private static long eval(long m, long b, long x) {
        return m * x + b;
    }

    // Check whether line3 makes line2 obsolete between line1 and line3.
    // We check if intersection(line1,line2) >= intersection(line2,line3)
    // Use double for intersection comparison (safe for monotonic inputs).
    private static boolean isBad(long m1, long b1, long m2, long b2, long m3, long b3) {
        // intersection x-coordinate of (m1,b1) and (m2,b2) is (b2-b1)/(m1-m2)
        // intersection of (m2,b2) and (m3,b3) is (b3-b2)/(m2-m3)
        // We want (b2-b1)/(m1-m2) >= (b3-b2)/(m2-m3)
        double left = (double)(b2 - b1) / (double)(m1 - m2);
        double right = (double)(b3 - b2) / (double)(m2 - m3);
        return left >= right;
    }

    // Fast integer scanner for large input
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

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
            while ((c = read()) <= ' ') {
                if (c == -1) return Long.MIN_VALUE;
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}