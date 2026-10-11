package CEOI;

import java.io.*;
import java.util.*;

public class ceoi17p4 {

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    static class Line {
        long m, b;

        Line(long m, long b) {
            this.m = m;
            this.b = b;
        }

        long eval(long x) {
            return m * x + b;
        }
    }

    static class LiChao {
        Line[] tree;
        int maxX;

        LiChao(int maxX) {
            this.maxX = maxX;
            tree = new Line[4 * (maxX + 2)];
        }

        void addLine(Line nw) {
            addLine(nw, 1, 0, maxX);
        }

        private void addLine(Line nw, int node, int l, int r) {
            if (tree[node] == null) {
                tree[node] = nw;
                return;
            }

            int mid = (l + r) >>> 1;
            Line cur = tree[node];

            boolean left = nw.eval(l) < cur.eval(l);
            boolean middle = nw.eval(mid) < cur.eval(mid);

            if (middle) {
                tree[node] = nw;
                nw = cur;
            }

            if (l == r) return;

            if (left != middle) {
                addLine(nw, node << 1, l, mid);
            } else {
                addLine(nw, (node << 1) | 1, mid + 1, r);
            }
        }

        long query(long x) {
            return query(1, 0, maxX, x);
        }

        private long query(int node, int l, int r, long x) {
            long res = tree[node] == null ? Long.MAX_VALUE / 4 : tree[node].eval(x);

            if (l == r) return res;

            int mid = (l + r) >>> 1;
            if (x <= mid) {
                long q = query(node << 1, l, mid, x);
                return res < q ? res : q;
            } else {
                long q = query((node << 1) | 1, mid + 1, r, x);
                return res < q ? res : q;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();

        long[] h = new long[n + 1];
        int maxH = 0;

        for (int i = 1; i <= n; i++) {
            h[i] = fs.nextLong();
            if (h[i] > maxH) maxH = (int) h[i];
        }

        long[] w = new long[n + 1];
        long totalW = 0;

        for (int i = 1; i <= n; i++) {
            w[i] = fs.nextLong();
            totalW += w[i];
        }

        long[] dp = new long[n + 1];

        LiChao cht = new LiChao(maxH);

        dp[1] = -w[1];
        cht.addLine(new Line(-2 * h[1], dp[1] + h[1] * h[1]));

        for (int j = 2; j <= n; j++) {
            long best = cht.query(h[j]) + h[j] * h[j] - w[j];
            dp[j] = best;

            if (j < n) {
                cht.addLine(new Line(-2 * h[j], dp[j] + h[j] * h[j]));
            }
        }

        System.out.println(totalW + dp[n]);
    }
}