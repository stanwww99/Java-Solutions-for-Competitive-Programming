package YAC;

import java.io.*;
import java.util.*;

public class yac7p3 {

    // ---------- FAST INPUT ----------
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

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return -1;
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = c - '0';
            while ((c = read()) > ' ') {
                val = val * 10 + (c - '0');
            }
            return val * sign;
        }
    }

    // ---------- FAST OUTPUT ----------
    static class FastOutput {
        private final OutputStream out;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;

        FastOutput(OutputStream os) {
            out = os;
        }

        void printInt(int x) throws IOException {
            if (ptr > buffer.length - 20) flush();
            if (x == 0) {
                buffer[ptr++] = '0';
                return;
            }
            if (x < 0) {
                buffer[ptr++] = '-';
                x = -x;
            }
            int start = ptr;
            while (x > 0) {
                buffer[ptr++] = (byte) ('0' + (x % 10));
                x /= 10;
            }
            for (int i = start, j = ptr - 1; i < j; i++, j--) {
                byte t = buffer[i];
                buffer[i] = buffer[j];
                buffer[j] = t;
            }
        }

        void printChar(char c) throws IOException {
            if (ptr == buffer.length) flush();
            buffer[ptr++] = (byte) c;
        }

        void println() throws IOException {
            printChar('\n');
        }

        void flush() throws IOException {
            out.write(buffer, 0, ptr);
            ptr = 0;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        FastOutput fo = new FastOutput(System.out);

        int N = fs.nextInt();
        int[] P = new int[N + 1];

        for (int i = 1; i <= N; i++) P[i] = fs.nextInt();

        // ---------- BUILD REVERSE GRAPH ----------
        int[] head = new int[N + 1];
        Arrays.fill(head, -1);
        int[] to = new int[N + 1];
        int[] next = new int[N + 1];
        int eidx = 0;

        int[] indeg = new int[N + 1];

        for (int v = 1; v <= N; v++) {
            int u = P[v];
            to[eidx] = v;
            next[eidx] = head[u];
            head[u] = eidx++;
            indeg[u]++;
        }

        // ---------- FIND CYCLE NODES ----------
        boolean[] inCycle = new boolean[N + 1];
        int[] q = new int[N];
        int qh = 0, qt = 0;

        for (int i = 1; i <= N; i++) {
            if (indeg[i] == 0) q[qt++] = i;
        }

        while (qh < qt) {
            int v = q[qh++];
            int u = P[v];
            indeg[u]--;
            if (indeg[u] == 0) q[qt++] = u;
        }

        for (int i = 1; i <= N; i++) {
            if (indeg[i] > 0) inCycle[i] = true;
        }

        // ---------- FIND PREDECESSOR ON CYCLE ----------
        boolean[] visCyc = new boolean[N + 1];
        int[] prevOnCycle = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            if (inCycle[i] && !visCyc[i]) {
                int cur = i;
                int[] cyc = new int[1024];
                int len = 0;

                do {
                    if (len == cyc.length) cyc = Arrays.copyOf(cyc, len << 1);
                    cyc[len++] = cur;
                    visCyc[cur] = true;
                    cur = P[cur];
                } while (cur != i);

                for (int j = 0; j < len; j++) {
                    int u = cyc[j];
                    int v = cyc[(j + 1) % len];
                    prevOnCycle[v] = u;
                }
            }
        }

        // ---------- BFS TREES FROM CYCLE NODES ----------
        int[] parent = new int[N + 1];
        int[] depth = new int[N + 1];
        int[] bfs = new int[N];

        int globalMaxDepth = 0;
        int bestRoot = 1;
        int bestLeaf = 1;

        for (int root = 1; root <= N; root++) {
            if (!inCycle[root]) continue;

            int localMax = 0;
            int localLeaf = root;

            int bh = 0, bt = 0;

            for (int e = head[root]; e != -1; e = next[e]) {
                int v = to[e];
                if (inCycle[v]) continue;
                if (parent[v] != 0) continue;
                parent[v] = root;
                depth[v] = 1;
                bfs[bt++] = v;
                if (1 > localMax) {
                    localMax = 1;
                    localLeaf = v;
                }
            }

            while (bh < bt) {
                int cur = bfs[bh++];
                for (int e = head[cur]; e != -1; e = next[e]) {
                    int v = to[e];
                    if (inCycle[v]) continue;
                    if (parent[v] != 0) continue;
                    parent[v] = cur;
                    depth[v] = depth[cur] + 1;
                    bfs[bt++] = v;
                    if (depth[v] > localMax) {
                        localMax = depth[v];
                        localLeaf = v;
                    }
                }
            }

            if (localMax > globalMaxDepth) {
                globalMaxDepth = localMax;
                bestRoot = root;
                bestLeaf = localLeaf;
            }
        }

        int A, B;

        if (globalMaxDepth == 0) {
            A = 1;
            B = P[1];
        } else {
            int X = bestRoot;
            int Y = bestLeaf;

            int cur = Y;
            while (parent[cur] != X) cur = parent[cur];
            int Z = cur;

            int W = prevOnCycle[X];

            A = W;
            B = Y;

            P[A] = B;
        }

        // ---------- BINARY LIFTING FOR FINAL GROUPS ----------
        int LOG = 1;
        while ((1 << LOG) <= N) LOG++;

        int[][] up = new int[LOG][N + 1];
        for (int i = 1; i <= N; i++) up[0][i] = P[i];

        for (int k = 1; k < LOG; k++) {
            int[] prev = up[k - 1];
            int[] curUp = up[k];
            for (int i = 1; i <= N; i++) {
                curUp[i] = prev[prev[i]];
            }
        }

        int[] dest = new int[N + 1];
        long steps = N;

        for (int i = 1; i <= N; i++) {
            int cur = i;
            long s = steps;
            for (int k = 0; k < LOG; k++) {
                if ((s & (1L << k)) != 0) cur = up[k][cur];
            }
            dest[i] = cur;
        }

        int[] groupId = new int[N + 1];
        int[] group = new int[N + 1];
        int gcount = 0;

        for (int i = 1; i <= N; i++) {
            int d = dest[i];
            if (groupId[d] == 0) groupId[d] = ++gcount;
            group[i] = groupId[d];
        }

        // ---------- OUTPUT ----------
        fo.printInt(A);
        fo.printChar(' ');
        fo.printInt(B);
        fo.println();

        for (int i = 1; i <= N; i++) {
            fo.printInt(group[i]);
            if (i < N) fo.printChar(' ');
        }
        fo.println();

        fo.flush();
    }
}