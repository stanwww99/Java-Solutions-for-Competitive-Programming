package APIO;

import java.io.*;
import java.util.*;

public class apio14p2 {
    static final long INF = Long.MAX_VALUE;

    static class Line {
        long m;    // slope
        long b;    // intercept
        int idx;   // j that produced this line
        Line(long m, long b, int idx) { this.m = m; this.b = b; this.idx = idx; }
        long val(long x) { return m * x + b; }
    }

    // return true if middle line b is unnecessary between a and c
    static boolean isBad(Line a, Line b, Line c) {
        // cross-multiplied comparison to avoid floating point:
        // (c.b - a.b) * (a.m - b.m) <= (b.b - a.b) * (a.m - c.m)
        return (c.b - a.b) * (a.m - b.m) <= (b.b - a.b) * (a.m - c.m);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int k = fs.nextInt();

        long[] S = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            long x = fs.nextLong();
            S[i] = S[i - 1] + x;
        }

        int parts = k + 1;
        long[] prev = new long[n + 1];
        long[] cur  = new long[n + 1];
        Arrays.fill(prev, INF);
        prev[0] = 0;

        int[][] choice = new int[parts + 1][n + 1];

        for (int t = 1; t <= parts; t++) {
            Arrays.fill(cur, INF);
            ArrayDeque<Line> dq = new ArrayDeque<>();

            for (int i = t; i <= n; i++) {
                int jAdd = i - 1;
                if (prev[jAdd] != INF) {
                    long m = -2L * S[jAdd];
                    long b = prev[jAdd] + S[jAdd] * S[jAdd];
                    Line nl = new Line(m, b, jAdd);
                    while (dq.size() >= 2) {
                        Line l2 = dq.removeLast();
                        Line l1 = dq.peekLast();
                        if (isBad(l1, l2, nl)) {
                            // l2 is bad, continue (already removed)
                            continue;
                        } else {
                            dq.addLast(l2);
                            break;
                        }
                    }
                    dq.addLast(nl);
                }

                if (!dq.isEmpty()) {
                    long x = S[i];
                    // pop front while next line is better at x
                    while (dq.size() >= 2) {
                        Iterator<Line> it = dq.iterator();
                        Line f1 = it.next();
                        Line f2 = it.next();
                        if (f1.val(x) >= f2.val(x)) dq.removeFirst();
                        else break;
                    }
                    Line best = dq.peekFirst();
                    cur[i] = x * x + best.val(x);
                    choice[t][i] = best.idx;
                } else {
                    cur[i] = INF;
                    choice[t][i] = -1;
                }
            }

            long[] tmp = prev; prev = cur; cur = tmp;
        }

        long minSumSq = prev[n];
        long total = S[n];
        long maxPoints = (total * total - minSumSq) / 2;

        StringBuilder out = new StringBuilder();
        out.append(maxPoints).append('\n');

        int[] splits = new int[k];
        int idx = k - 1;
        int t = parts;
        int pos = n;
        while (t > 1) {
            int prevPos = choice[t][pos];
            splits[idx--] = prevPos;
            pos = prevPos;
            t--;
        }
        for (int i = 0; i < k; i++) {
            if (i > 0) out.append(' ');
            out.append(splits[i]);
        }
        out.append('\n');

        // fast output
        System.out.print(out.toString());
    }

    // FastScanner using BufferedInputStream and manual parsing
    static class FastScanner {
        private final BufferedInputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { in = new BufferedInputStream(is); }

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
