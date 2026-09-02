package YAC;
import java.io.*;
import java.util.*;

/**
 * Interactive solution for "Rumour" (YAC3 P4).
 *
 * Strategy: centroid-based narrowing of the candidate component.
 *
 * Usage: run in interactive judge. The program reads N and N-1 edges,
 * then prints queries (single integer per line) and flushes. It reads
 * the interactor's integer responses and terminates when response == 0.
 */
public class yac3p4 {
    static FastScanner fs = new FastScanner(System.in);
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

    static int N;
    static ArrayList<Integer>[] adj;
    static boolean[] alive;      // whether node is currently in candidate component
    static boolean[] removed;    // nodes removed as centroids (not in current component)
    static int[] subSize;
    static int totalAlive;

    public static void main(String[] args) throws Exception {
        N = fs.nextInt();
        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < N - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        alive = new boolean[N + 1];
        removed = new boolean[N + 1];
        subSize = new int[N + 1];
        for (int i = 1; i <= N; i++) alive[i] = true;
        totalAlive = N;

        // pick any alive node as start (1..N)
        int start = 1;

        // main loop: at most 17 queries (problem limit)
        for (int queries = 0; queries < 17; queries++) {
            // find a node that is alive to start DFS from
            start = findAlive(start);
            if (start == -1) break;

            // compute subtree sizes within the current alive component
            computeSubSize(start, 0);

            // find centroid of the current component
            int centroid = findCentroid(start, 0, totalAlive);

            // query centroid
            int response = query(centroid);
            if (response == 0) {
                // found the root; terminate successfully
                System.exit(0);
            }

            // restrict to the component that contains 'response' after removing centroid
            // mark centroid as removed (not alive)
            removed[centroid] = true;
            alive[centroid] = false;
            totalAlive--;

            // find the component containing 'response' (do a DFS from response avoiding removed nodes)
            // collect nodes in that component and set alive[] accordingly
            boolean[] compVisited = new boolean[N + 1];
            ArrayList<Integer> compNodes = new ArrayList<>();
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(response);
            compVisited[response] = true;
            while (!stack.isEmpty()) {
                int u = stack.pop();
                compNodes.add(u);
                for (int v : adj[u]) {
                    if (!compVisited[v] && !removed[v]) {
                        compVisited[v] = true;
                        stack.push(v);
                    }
                }
            }

            // set alive[]: only nodes in compNodes remain alive; others become not alive
            boolean[] newAlive = new boolean[N + 1];
            for (int node : compNodes) newAlive[node] = true;
            alive = newAlive;
            totalAlive = compNodes.size();

            // set start to response for next iteration
            start = response;
        }

        // If we exit loop without finding root, as a fallback query any remaining alive node
        int fallback = findAlive(1);
        if (fallback != -1) {
            query(fallback);
        }
        System.exit(0);
    }

    // Query a node Y: print it, flush, and read response Z
    static int query(int y) {
        out.println(y);
        out.flush();
        int z = fs.nextInt();
        return z;
    }

    // Find any alive node starting from hint; returns -1 if none
    static int findAlive(int hint) {
        if (hint >= 1 && hint <= N && alive[hint]) return hint;
        for (int i = 1; i <= N; i++) if (alive[i]) return i;
        return -1;
    }

    // Compute subtree sizes for the connected component containing 'u'
    static int computeSubSize(int u, int p) {
        subSize[u] = 1;
        for (int v : adj[u]) {
            if (v == p) continue;
            if (!alive[v]) continue;
            subSize[u] += computeSubSize(v, u);
        }
        return subSize[u];
    }

    // Find centroid in the component rooted at u (component size = compSize)
    static int findCentroid(int u, int p, int compSize) {
        for (int v : adj[u]) {
            if (v == p) continue;
            if (!alive[v]) continue;
            if (subSize[v] > compSize / 2) {
                return findCentroid(v, u, compSize);
            }
        }
        return u;
    }

    // Fast scanner for interactive reading
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { in = is; }

        private int read() throws RuntimeException {
            try {
                if (ptr >= len) {
                    len = in.read(buffer);
                    ptr = 0;
                    if (len <= 0) return -1;
                }
                return buffer[ptr++];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int nextInt() {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return -1;
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