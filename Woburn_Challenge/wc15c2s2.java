package Woburn_Challenge;
import java.io.*;
import java.util.*;

/**
 * Very low-memory offline solution that prints totals directly with System.out.println
 */
public class wc15c2s2 {
    static final int INT_MAX = Integer.MAX_VALUE;

    static long orderKey(int s, int a) {
        int ta = (s % 2 == 0) ? (INT_MAX - a) : a;
        return (((long)s) << 32) | (ta & 0xffffffffL);
    }
    static int unpackS(long key) { return (int)(key >>> 32); }
    static int unpackA(long key) {
        int s = unpackS(key);
        int ta = (int)key;
        return (s % 2 == 0) ? (INT_MAX - ta) : ta;
    }
    static long manhattan(long k1, long k2) {
        int s1 = unpackS(k1), a1 = unpackA(k1);
        int s2 = unpackS(k2), a2 = unpackA(k2);
        return Math.abs(s1 - s2) + Math.abs(a1 - a2);
    }

    static class Fenwick {
        final int n;
        final int[] bit;
        Fenwick(int n) { this.n = n; bit = new int[n + 1]; }
        void add(int i, int delta) { for (; i <= n; i += i & -i) bit[i] += delta; }
        int sum(int i) { int r = 0; for (; i > 0; i -= i & -i) r += bit[i]; return r; }
        int findByPrefix(int k) {
            if (k <= 0) return 1;
            int idx = 0;
            int bitMask = Integer.highestOneBit(n);
            for (; bitMask != 0; bitMask >>= 1) {
                int next = idx + bitMask;
                if (next <= n && bit[next] < k) {
                    idx = next;
                    k -= bit[next];
                }
            }
            return idx + 1;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int[] inS = new int[N];
        int[] inA = new int[N];
        for (int i = 0; i < N; i++) {
            inS[i] = fs.nextInt();
            inA[i] = fs.nextInt();
        }

        int M = N + 1;
        long[] allKeys = new long[M];
        allKeys[0] = orderKey(1, 1);
        for (int i = 0; i < N; i++) allKeys[i + 1] = orderKey(inS[i], inA[i]);

        Arrays.sort(allKeys);
        int uniqueCount = 0;
        for (int i = 0; i < M; i++) {
            if (i == 0 || allKeys[i] != allKeys[i - 1]) {
                allKeys[uniqueCount++] = allKeys[i];
            }
        }
        long[] orderKeys = Arrays.copyOf(allKeys, uniqueCount);

        Fenwick bit = new Fenwick(uniqueCount);
        boolean[] present = new boolean[uniqueCount + 1];

        long startKey = orderKey(1, 1);
        int startPos = Arrays.binarySearch(orderKeys, startKey) + 1;
        bit.add(startPos, 1);
        present[startPos] = true;
        int totalPresent = 1;
        long total = 0L;

        for (int i = 0; i < N; i++) {
            long k = orderKey(inS[i], inA[i]);
            int pos = Arrays.binarySearch(orderKeys, k);
            if (pos < 0) {
                System.out.println(total);
                continue;
            }
            pos += 1;

            if (present[pos]) {
                System.out.println(total);
                continue;
            }

            int leftCount = bit.sum(pos);
            int predIdx = -1, succIdx = -1;
            if (leftCount != 0) predIdx = bit.findByPrefix(leftCount);
            if (leftCount != totalPresent) succIdx = bit.findByPrefix(leftCount + 1);

            if (predIdx != -1 && succIdx != -1) {
                long predKey = orderKeys[predIdx - 1];
                long succKey = orderKeys[succIdx - 1];
                total -= manhattan(predKey, succKey);
                total += manhattan(predKey, k);
                total += manhattan(k, succKey);
            } else if (predIdx != -1) {
                long predKey = orderKeys[predIdx - 1];
                total += manhattan(predKey, k);
            } else if (succIdx != -1) {
                long succKey = orderKeys[succIdx - 1];
                total += manhattan(k, succKey);
            }

            bit.add(pos, 1);
            present[pos] = true;
            totalPresent++;

            System.out.println(total);
        }
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
            while ((c = read()) <= ' ') if (c == -1) return -1;
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