package Baltic_OI;

import java.io.*;
import java.util.*;

/**
 * Baltic OI '02 P1 - Speed Limits
 * Full Java solution using Dijkstra on (node, speed) states.
 */
public class btoi02p1 {
    static class Edge {
        int to;
        int v; // speed limit (0 if missing)
        int len;
        Edge(int to, int v, int len) { this.to = to; this.v = v; this.len = len; }
    }

    static class State implements Comparable<State> {
        int node;
        int speed;
        double time;
        State(int node, int speed, double time) { this.node = node; this.speed = speed; this.time = time; }
        public int compareTo(State o) { return Double.compare(this.time, o.time); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();
        int D = fs.nextInt();

        List<Edge>[] g = new ArrayList[N];
        for (int i = 0; i < N; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int A = fs.nextInt();
            int B = fs.nextInt();
            int V = fs.nextInt();
            int L = fs.nextInt();
            g[A].add(new Edge(B, V, L));
        }

        final int MAXS = 500; // speeds 0..500
        final double INF = 1e100;
        double[][] dist = new double[N][MAXS + 1];
        int[][] prevNode = new int[N][MAXS + 1];
        int[][] prevSpeed = new int[N][MAXS + 1];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(prevNode[i], -1);
            Arrays.fill(prevSpeed[i], -1);
        }

        int startSpeed = 70; // initial speed as specified.
        dist[0][startSpeed] = 0.0;
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(0, startSpeed, 0.0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.time > dist[cur.node][cur.speed]) continue;
            if (cur.node == D) {
                // We cannot break here because a different speed at D might be even better,
                // but we can continue; final selection will pick minimal over speeds.
            }
            for (Edge e : g[cur.node]) {
                int ns;
                double cost;
                if (e.v > 0) {
                    ns = e.v;
                    cost = e.len / (double) e.v;
                } else {
                    // missing sign: keep current speed
                    ns = cur.speed;
                    // guard: current speed should never be zero in valid traversal (initial 70)
                    cost = e.len / (double) cur.speed;
                }
                if (dist[e.to][ns] > cur.time + cost) {
                    dist[e.to][ns] = cur.time + cost;
                    prevNode[e.to][ns] = cur.node;
                    prevSpeed[e.to][ns] = cur.speed;
                    pq.add(new State(e.to, ns, dist[e.to][ns]));
                }
            }
        }

        // find best speed at destination
        double best = INF;
        int bestSpeed = -1;
        for (int s = 0; s <= MAXS; s++) {
            if (dist[D][s] < best) {
                best = dist[D][s];
                bestSpeed = s;
            }
        }

        // Reconstruct path
        if (bestSpeed == -1) {
            // No path found (problem statement implies there will be a path)
            System.out.println("No path");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        int curNode = D;
        int curSpeed = bestSpeed;
        while (!(curNode == 0 && curSpeed == startSpeed)) {
            path.addFirst(curNode);
            int pn = prevNode[curNode][curSpeed];
            int ps = prevSpeed[curNode][curSpeed];
            if (pn == -1) {
                // Should not happen if path exists
                break;
            }
            curNode = pn;
            curSpeed = ps;
        }
        // add start node
        path.addFirst(0);

        // Output nodes space-separated
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int node : path) {
            if (!first) sb.append(' ');
            sb.append(node);
            first = false;
        }
        System.out.println(sb.toString());
    }

    // Fast scanner for competitive programming
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
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
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
