package Baltic_OI;

import java.io.*;
import java.util.*;


public class btoi01p6 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int m = fs.nextInt();
        int n = fs.nextInt();
        int total = m + n;

        int[] out = new int[total];       // out[v] = target node index (0-based over both islands)
        int[] indeg = new int[total];

        // Bornholm nodes: 0..m-1, their targets are Gotland 1..n mapped to indices m..m+n-1
        for (int i = 0; i < m; i++) {
            int t = fs.nextInt(); // 1..n
            out[i] = m + (t - 1);
            indeg[out[i]]++;
        }
        // Gotland nodes: m..m+n-1, their targets are Bornholm 1..m mapped to indices 0..m-1
        for (int i = 0; i < n; i++) {
            int t = fs.nextInt(); // 1..m
            out[m + i] = (t - 1);
            indeg[out[m + i]]++;
        }

        // -1 = unassigned, 1 = sender, 0 = receiver
        int[] state = new int[total];
        Arrays.fill(state, -1);

        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < total; i++) {
            if (indeg[i] == 0) q.add(i);
        }

        // Process nodes with indegree 0: they must be senders
        while (!q.isEmpty()) {
            int u = q.poll();
            if (state[u] != -1) continue;
            // u must be sender
            state[u] = 1;
            int v = out[u];
            if (state[v] == -1) {
                state[v] = 0; // v must be receiver
                // remove v's outgoing edge (v cannot be sender)
                int w = out[v];
                indeg[w]--;
                if (indeg[w] == 0) q.add(w);
            }
        }

        // Remaining unassigned nodes are in cycles. Handle each cycle by alternating.
        boolean[] visited = new boolean[total];
        for (int i = 0; i < total; i++) {
            if (state[i] != -1 || visited[i]) continue;
            // collect cycle starting from i
            int cur = i;
            List<Integer> cycle = new ArrayList<>();
            while (!visited[cur]) {
                visited[cur] = true;
                cycle.add(cur);
                cur = out[cur];
            }
            // If the cycle we collected may include a tail that leads into an already processed cycle,
            // ensure we only process the actual cycle nodes. Find start index of cur in cycle list.
            int start = cycle.indexOf(cur);
            if (start == -1) {
                // This shouldn't happen for remaining unassigned nodes, but guard anyway:
                continue;
            }
            List<Integer> realCycle = cycle.subList(start, cycle.size());
            // Assign alternating: pick realCycle[0] as sender, next as receiver, etc.
            for (int k = 0; k < realCycle.size(); k++) {
                int node = realCycle.get(k);
                if (state[node] != -1) continue;
                if ((k & 1) == 0) {
                    state[node] = 1;
                } else {
                    state[node] = 0;
                }
            }
        }

        // For safety, any still-unassigned nodes (shouldn't happen) set as sender and force target receiver
        for (int i = 0; i < total; i++) {
            if (state[i] == -1) {
                state[i] = 1;
                int v = out[i];
                if (state[v] == -1) state[v] = 0;
            }
        }

        // Output two lines: Bornholm (m chars), Gotland (n chars)
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < m; i++) sb1.append(state[i] == 1 ? '1' : '0');
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < n; i++) sb2.append(state[m + i] == 1 ? '1' : '0');

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
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
