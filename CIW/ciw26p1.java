package CIW;

import java.io.*;
import java.util.*;

public class ciw26p1 {
    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();
        int K = fs.nextInt();

        int[] label = new int[N + 1];
        ArrayList<Integer>[] groups = new ArrayList[K + 1];
        for (int i = 0; i <= K; i++) groups[i] = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            label[i] = fs.nextInt();
            if (label[i] >= 0 && label[i] <= K) {
                groups[label[i]].add(i);
            }
        }

        ArrayList<Integer>[] adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            adj[a].add(b);
            adj[b].add(a);
        }

        // costPrev[u] = minimum time to be at node u after buying up to previous item
        long[] costPrev = new long[N + 1];
        Arrays.fill(costPrev, INF);
        // start at any entrance (label 0) with cost 0
        for (int v : groups[0]) costPrev[v] = 0;

        // For each item i = 1..K compute costCurr for nodes selling item i
        for (int item = 1; item <= K; item++) {
            long[] dist = dijkstraMultiSource(N, adj, costPrev);
            long[] costCurr = new long[N + 1];
            Arrays.fill(costCurr, INF);
            for (int v : groups[item]) {
                costCurr[v] = dist[v];
            }
            costPrev = costCurr;
        }

        long ans = INF;
        for (int v : groups[K]) ans = Math.min(ans, costPrev[v]);
        System.out.println(ans);
    }

    // Dijkstra with multiple sources; initial distances provided by initDist array
    static long[] dijkstraMultiSource(int N, ArrayList<Integer>[] adj, long[] initDist) {
        long[] dist = new long[N + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 1; i <= N; i++) {
            if (initDist[i] < INF) {
                dist[i] = initDist[i];
                pq.add(new Node(i, dist[i]));
            }
        }
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.d != dist[cur.u]) continue;
            for (int w : adj[cur.u]) {
                long nd = cur.d + 1; // edge weight is 1
                if (nd < dist[w]) {
                    dist[w] = nd;
                    pq.add(new Node(w, nd));
                }
            }
        }
        return dist;
    }

    static class Node implements Comparable<Node> {
        int u;
        long d;
        Node(int u, long d) { this.u = u; this.d = d; }
        public int compareTo(Node o) { return Long.compare(this.d, o.d); }
    }

    // Fast scanner
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
        long nextLong() throws IOException { return Long.parseLong(next()); }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return null;
            }
            do {
                sb.append((char)c);
                c = read();
            } while (c > ' ');
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
