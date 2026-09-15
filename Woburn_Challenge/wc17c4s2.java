package Woburn_Challenge;
import java.io.*;
import java.util.*;

public class wc17c4s2 {
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
            do {
                c = read();
                if (c == -1) return -1;
            } while (c <= ' ');

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

    static int N, M;
    static List<Integer>[] g, gr;

    static int[] bfs(int start, List<Integer>[] adj) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        dist[start] = 0;
        q.add(start);
        while (!q.isEmpty()) {
            int u = q.poll();
            int du = dist[u];
            for (int v : adj[u]) {
                if (dist[v] == -1) {
                    dist[v] = du + 1;
                    q.add(v);
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        N = fs.nextInt();
        M = fs.nextInt();
        g = new ArrayList[N + 1];
        gr = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            g[i] = new ArrayList<>();
            gr[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            g[a].add(b);
            gr[b].add(a); // reverse edge
        }

        int K = fs.nextInt();
        int[] S = new int[K];
        for (int i = 0; i < K; i++) {
            S[i] = fs.nextInt();
        }

        int[] distFrom1 = bfs(1, g);
        int[] distTo1 = bfs(1, gr); // distance in reversed graph: from node to 1 in original

        long total = 0;
        for (int i = 0; i < K; i++) {
            int s = S[i];
            if (distFrom1[s] == -1 || distTo1[s] == -1) {
                System.out.println(-1);
                return;
            }
            total += (long) distFrom1[s] + distTo1[s];
        }

        System.out.println(total);
    }
}
