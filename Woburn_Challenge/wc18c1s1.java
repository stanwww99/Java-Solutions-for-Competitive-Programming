package Woburn_Challenge;

import java.io.*;
import java.util.StringTokenizer;

public class wc18c1s1 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int R = fs.nextInt();
        int C = fs.nextInt();
        int K = fs.nextInt();

        int[][] grid = new int[R + 1][C]; // 1-based rows for convenience
        for (int r = 1; r <= R; r++) {
            for (int c = 0; c < C; c++) {
                grid[r][c] = fs.nextInt();
            }
        }

        int[][] prefix = new int[R + 1][C];
        for (int c = 0; c < C; c++) prefix[0][c] = 0;
        for (int r = 1; r <= R; r++) {
            for (int c = 0; c < C; c++) {
                prefix[r][c] = prefix[r - 1][c] + (grid[r][c] == 1 ? 1 : 0);
            }
        }

        long inspired = 0;
        for (int r = 1; r <= R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == 2) {
                    int low = Math.max(1, r - K);
                    int high = r - 1;
                    if (high >= low) {
                        int ones = prefix[high][c] - prefix[low - 1][c];
                        if (ones > 0) inspired++;
                    }
                }
            }
        }

        System.out.println(inspired);
    }

    // Fast scanner using BufferedInputStream
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
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}

