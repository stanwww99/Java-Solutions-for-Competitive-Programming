package SIUCF;

import java.io.*;
import java.util.*;


public class si17c3p5 {
    static class Edge {
        int to;
        int w;
        Edge(int t, int ww) { to = t; w = ww; }
    }

    static int n;
    static ArrayList<Edge>[] g;
    static boolean[] removed;
    static int[] subSize;
    static long answer = 0L;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        n = fs.nextInt();
        g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            char c = fs.next().charAt(0);
            int w = (c == 'r') ? 1 : -1;
            g[a].add(new Edge(b, w));
            g[b].add(new Edge(a, w));
        }

        removed = new boolean[n + 1];
        subSize = new int[n + 1];

        // Count pairs with path-sum == +1 and path-sum == -1
        decompose(1);

        // Exclude existing edges (each tree edge is a path of length 1 with |d|=1)
        answer -= (n - 1);
        if (answer < 0) answer = 0;

        System.out.println(answer);
    }

    // Centroid decomposition
    static void decompose(int start) {
        int total = dfsSize(start, -1);
        int centroid = findCentroid(start, -1, total);
        // process centroid
        removed[centroid] = true;

        // Map to accumulate counts of path-sums seen so far (from centroid)
        HashMap<Integer, Integer> counts = new HashMap<>();
        counts.put(0, 1); // centroid itself

        // For both targets (+1 and -1) we will accumulate contributions
        for (Edge e : g[centroid]) {
            int v = e.to;
            if (removed[v]) continue;
            ArrayList<Integer> sums = new ArrayList<>();
            collectSums(v, centroid, e.w, sums);

            // For each sum s in this subtree, pairs with previously seen sums t satisfy s + t = target
            for (int s : sums) {
                // target = +1
                Integer need1 = counts.get(1 - s);
                if (need1 != null) answer += need1;
                // target = -1
                Integer need2 = counts.get(-1 - s);
                if (need2 != null) answer += need2;
            }

            // After counting, add this subtree's sums to counts
            for (int s : sums) {
                counts.put(s, counts.getOrDefault(s, 0) + 1);
            }
        }

        // Recurse on subtrees
        for (Edge e : g[centroid]) {
            if (!removed[e.to]) decompose(e.to);
        }
    }

    // compute subtree sizes
    static int dfsSize(int u, int p) {
        subSize[u] = 1;
        for (Edge e : g[u]) {
            int v = e.to;
            if (v == p || removed[v]) continue;
            subSize[u] += dfsSize(v, u);
        }
        return subSize[u];
    }

    // find centroid of subtree rooted at u
    static int findCentroid(int u, int p, int total) {
        for (Edge e : g[u]) {
            int v = e.to;
            if (v == p || removed[v]) continue;
            if (subSize[v] > total / 2) return findCentroid(v, u, total);
        }
        return u;
    }

    // collect path sums from centroid to nodes in this subtree
    static void collectSums(int u, int p, int curSum, ArrayList<Integer> out) {
        out.add(curSum);
        for (Edge e : g[u]) {
            int v = e.to;
            if (v == p || removed[v]) continue;
            collectSums(v, u, curSum + e.w, out);
        }
    }

    // Fast scanner for large input
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
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            while (c > ' ') {
                sb.append((char)c);
                c = read();
            }
            return sb.toString();
        }
    }
}
