package CIW;

import java.io.*;
import java.util.*;

public class ciw26p3 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        long N = fs.nextLong();

        if (N == 0) {
            System.out.println(0);
            return;
        }

        long best = Long.MAX_VALUE;
        long limit = (long) Math.sqrt(N);
        for (long d = 1; d <= limit; d++) {
            if (N % d == 0) {
                long other = N / d;
                long presses = d + other;
                if (presses < best) best = presses;
            }
        }

        System.out.println(best);
    }

    // Simple fast scanner
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
                if (c == -1) return 0;
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
    }
}