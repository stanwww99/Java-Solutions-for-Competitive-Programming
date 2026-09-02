package BubbleCup;

import java.io.*;
import java.util.*;

public class bbc08b {
    static final int MOD = 1_000_000_007;
    static int N, K;
    static ArrayList<int[]>[] adj;
    static int[] A, B, X;
    static int LOG;
    static int[] depth;
    static int[][] up;
    static int[] parentEdge;
    static long[] diffUp, diffDown;
    static long[] pow2;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        N = fs.nextInt();
        adj = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();
        A = new int[N];
        B = new int[N];
        X = new int[N];
        for (int i = 1; i <= N-1; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            int x = fs.nextInt();
            A[i] = a;
            B[i] = b;
            X[i] = x;
            adj[a].add(new int[]{b, i});
            adj[b].add(new int[]{a, i});
        }

        LOG = 1;
        while ((1<<LOG) <= N) LOG++;
        depth = new int[N+1];
        up = new int[LOG][N+1];
        parentEdge = new int[N+1];
        Arrays.fill(parentEdge, -1);
        dfsInit(1, 1, -1);

        for (int k = 1; k < LOG; k++) {
            for (int v = 1; v <= N; v++) {
                up[k][v] = up[k-1][ up[k-1][v] ];
            }
        }

        K = fs.nextInt();
        int[] stops = new int[K+1];
        for (int i = 1; i <= K; i++) stops[i] = fs.nextInt();

        diffUp = new long[N+1];
        diffDown = new long[N+1];

        int cur = 1;
        for (int i = 1; i <= K; i++) {
            int nxt = stops[i];
            if (cur == nxt) { cur = nxt; continue; }
            int l = lca(cur, nxt);
            diffUp[cur] += 1;
            diffUp[l] -= 1;
            diffDown[nxt] += 1;
            diffDown[l] -= 1;
            cur = nxt;
        }

        int maxPow = K;
        pow2 = new long[maxPow+2];
        pow2[0] = 1;
        for (int i = 1; i <= maxPow+1; i++) pow2[i] = (pow2[i-1] * 2) % MOD;

        long[] accUp = new long[N+1];
        long[] accDown = new long[N+1];
        boolean[] visited = new boolean[N+1];
        long ans = dfsAccumulate(1, visited, accUp, accDown);

        System.out.println(ans % MOD);
    }

    static void dfsInit(int u, int p, int pedge) {
        up[0][u] = p;
        parentEdge[u] = pedge;
        for (int[] e : adj[u]) {
            int v = e[0], id = e[1];
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dfsInit(v, u, id);
        }
    }

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) { int t = a; a = b; b = t; }
        int diff = depth[a] - depth[b];
        for (int k = 0; k < LOG; k++) if ((diff & (1<<k)) != 0) a = up[k][a];
        if (a == b) return a;
        for (int k = LOG-1; k >= 0; k--) {
            if (up[k][a] != up[k][b]) {
                a = up[k][a];
                b = up[k][b];
            }
        }
        return up[0][a];
    }

    static long dfsAccumulate(int u, boolean[] visited, long[] accUp, long[] accDown) {
        visited[u] = true;
        accUp[u] = diffUp[u];
        accDown[u] = diffDown[u];
        long subtotal = 0;
        for (int[] e : adj[u]) {
            int v = e[0], id = e[1];
            if (visited[v]) continue;
            subtotal += dfsAccumulate(v, visited, accUp, accDown);
            accUp[u] += accUp[v];
            accDown[u] += accDown[v];

            int parent = u, child = v;
            if (X[id] == 0) {
                continue;
            } else {
                long wrong = 0;
                if (A[id] == parent && B[id] == child) {
                    wrong = accUp[child];
                } else if (A[id] == child && B[id] == parent) {
                    wrong = accDown[child];
                } else {
                    if (A[id] == parent && B[id] == child) wrong = accUp[child];
                    else if (A[id] == child && B[id] == parent) wrong = accDown[child];
                    else wrong = 0;
                }
                if (wrong > 0) {
                    if (wrong >= pow2.length) {
                        int old = pow2.length;
                        int need = (int)wrong;
                        long[] np = new long[need+2];
                        System.arraycopy(pow2, 0, np, 0, old);
                        for (int i = old; i <= need+1; i++) np[i] = (np[i-1]*2)%MOD;
                        pow2 = np;
                    }
                    long add = (pow2[(int)wrong] - 1) % MOD;
                    if (add < 0) add += MOD;
                    subtotal = (subtotal + add) % MOD;
                }
            }
        }
        return subtotal % MOD;
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

