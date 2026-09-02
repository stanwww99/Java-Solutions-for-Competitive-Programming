package classics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class tootsierolls {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] t = new long[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            t[i] = Long.parseLong(st.nextToken());
        }

        List<Integer>[] adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        // 1. BFS to establish parent-child relationships and bottom-up order
        int[] order = new int[N];
        int head = 0, tail = 0;
        order[tail++] = 1;

        boolean[] visited = new boolean[N + 1];
        visited[1] = true;

        List<Integer>[] children = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            children[i] = new ArrayList<>();
        }

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    children[u].add(v);
                    order[tail++] = v;
                }
            }
        }

        // 2. Setup structures for DP
        long[][] dp = new long[N + 1][K + 1];
        long[] S = new long[N + 1];
        int[] size = new int[N + 1];

        // Initialize DP table with a very small number to represent negative infinity/impossible states
        long INF = -1_000_000_000_000_000_000L;
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], INF);
        }

        long[] f = new long[K + 1];
        long[] next_f = new long[K + 1];

        // 3. Bottom-up Tree DP
        for (int i = N - 1; i >= 0; i--) {
            int u = order[i];

            // Calculate the total subtree sum S[u]
            S[u] = t[u];
            for (int v : children[u]) {
                S[u] += S[v];
            }

            Arrays.fill(f, INF);
            f[0] = 0;
            int currentSize = 0;

            // Merge children subtrees (Knapsack)
            for (int v : children[u]) {
                Arrays.fill(next_f, INF);

                for (int x = 0; x <= Math.min(K, currentSize); x++) {
                    if (f[x] == INF) continue;

                    for (int y = 0; y <= Math.min(K - x, size[v]); y++) {
                        if (dp[v][y] == INF) continue;

                        if (f[x] + dp[v][y] > next_f[x + y]) {
                            next_f[x + y] = f[x] + dp[v][y];
                        }
                    }
                }

                int nextLimit = Math.min(K, currentSize + size[v]);
                for (int x = 0; x <= nextLimit; x++) {
                    f[x] = next_f[x];
                }
                currentSize += size[v];
            }

            // Record best scenarios for node u
            for (int x = 0; x <= Math.min(K, currentSize); x++) {
                dp[u][x] = f[x];
            }

            // Option to use exactly 1 choice to grab this entire subtree
            if (K >= 1) {
                dp[u][1] = Math.max(dp[u][1], S[u]);
            }
            size[u] = currentSize + 1;
        }

        // 4. Find the maximum tastiness checking all choices up to K
        long maxTastiness = 0;
        for (int j = 0; j <= K; j++) {
            maxTastiness = Math.max(maxTastiness, dp[1][j]);
        }

        System.out.println(maxTastiness);
    }
}