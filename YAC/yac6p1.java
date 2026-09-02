package YAC;

import java.io.*;
import java.util.StringTokenizer;

public class yac6p1 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();

        StringBuilder sb = new StringBuilder();
        int used = 0;

        // 1) Create a star centered at 1: edges (1, i) for i = 2..N
        for (int i = 2; i <= N; i++) {
            if (used == M) break;
            sb.append(1).append(' ').append(i).append('\n');
            used++;
        }

        // 2) Add remaining edges between leaves (2..N) in lexicographic order
        outer:
        for (int i = 2; i <= N; i++) {
            for (int j = i + 1; j <= N; j++) {
                if (used == M) break outer;
                // Skip if this edge would duplicate the star edge (none do, since star edges are (1,x))
                sb.append(i).append(' ').append(j).append('\n');
                used++;
            }
        }

        // Print result (should have exactly M lines)
        System.out.print(sb.toString());
    }

    // Fast scanner for input
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
