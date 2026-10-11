package TLE;

import java.io.*;
import java.util.*;

public class tle17c8p3 {

    // Upper bound for values
    private static final long MAX_VAL = 10000000000L; // 10^10

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int Q = fs.nextInt();
        long K = fs.nextLong();

        // Precompute all palindromes <= 1e10 that are divisible by K
        ArrayList<Long> pals = generatePalindromesDivisibleByK(K);

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            long M = fs.nextLong();
            long N = fs.nextLong();
            int left = lowerBound(pals, M);
            int right = upperBound(pals, N);
            out.append(right - left).append('\n');
        }

        System.out.print(out.toString());
    }

    // Generate all palindromes up to MAX_VAL that are divisible by K
    private static ArrayList<Long> generatePalindromesDivisibleByK(long K) {
        ArrayList<Long> res = new ArrayList<>();

        // Length from 1 to 10 digits
        for (int len = 1; len <= 10; len++) {
            int halfLen = (len + 1) / 2;
            long start = (long) Math.pow(10, halfLen - 1); // first digit non-zero
            long end = (long) Math.pow(10, halfLen) - 1;

            for (long prefix = start; prefix <= end; prefix++) {
                long pal = buildPalindrome(prefix, len);
                if (pal > MAX_VAL) break;
                if (pal % K == 0) {
                    res.add(pal);
                }
            }
        }

        // They are generated in increasing order already, but sort to be safe
        Collections.sort(res);
        return res;
    }

    // Build palindrome of total length 'len' from prefix
    private static long buildPalindrome(long prefix, int len) {
        String s = Long.toString(prefix);
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        int mirrorStart;
        if (len % 2 == 0) {
            mirrorStart = s.length() - 1;
        } else {
            mirrorStart = s.length() - 2;
        }
        for (int i = mirrorStart; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return Long.parseLong(sb.toString());
    }

    // First index with value >= x
    private static int lowerBound(ArrayList<Long> arr, long x) {
        int lo = 0, hi = arr.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr.get(mid) >= x) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    // First index with value > x
    private static int upperBound(ArrayList<Long> arr, long x) {
        int lo = 0, hi = arr.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr.get(mid) > x) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    // Fast scanner for DMOJ-style input
    private static class FastScanner {
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
            do {
                c = read();
                if (c == -1) return -1;
            } while (c <= ' ');

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