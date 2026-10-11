package EGOI;

import java.io.*;
import java.util.*;

public class egoi21p2 {
    // Fenwick / BIT for point updates and prefix sums
    static class Fenwick {
        private final int n;
        private final int[] bit;
        Fenwick(int n) {
            this.n = n;
            this.bit = new int[n + 1];
        }
        void add(int idx, int val) {
            for (; idx <= n; idx += idx & -idx) bit[idx] += val;
        }
        int sum(int idx) {
            int res = 0;
            for (; idx > 0; idx -= idx & -idx) res += bit[idx];
            return res;
        }
        int rangeSum(int l, int r) {
            if (r < l) return 0;
            return sum(r) - sum(l - 1);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = 2 * n;
        int[] a = new int[m + 1];
        for (int i = 1; i <= m; i++) a[i] = fs.nextInt();

        int[] firstPos = new int[n + 1]; // 1..n
        Arrays.fill(firstPos, 0);
        Fenwick bit = new Fenwick(m);
        long intersections = 0L;

        for (int i = 1; i <= m; i++) {
            int v = a[i];
            if (firstPos[v] == 0) {
                // first occurrence: mark it as open
                firstPos[v] = i;
                bit.add(i, 1);
            } else {
                int l = firstPos[v];
                // count how many first-occurrences started strictly between l and i and are still open
                int cnt = bit.rangeSum(l + 1, i - 1);
                intersections += cnt;
                // close this interval: remove the mark at l
                bit.add(l, -1);
            }
        }

        long result = (long) n + intersections;
        System.out.println(result);
    }

    // Fast scanner
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
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}