package YAC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class yac1p2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine().trim());
        String colors = br.readLine().trim();

        ArrayList<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        // BFS to get topological order for bottom-up Tree DP (avoids recursion StackOverflow)
        int[] order = new int[n];
        int[] parent = new int[n + 1];
        boolean[] visited = new boolean[n + 1];

        int head = 0, tail = 0;
        order[tail++] = 1;
        visited[1] = true;

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    order[tail++] = v;
                }
            }
        }

        long badPaths = 0;
        long[][][] dp = new long[n + 1][3][3];

        // Process nodes in reverse BFS order (bottom-up)
        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];

            long[][] tempDp = new long[3][3];
            int u_b = colors.charAt(u - 1) == 'B' ? 1 : 0;
            int u_w = 1 - u_b;
            tempDp[u_b][u_w] = 1;

            for (int v : adj[u]) {
                if (v == parent[u]) continue;

                // Count pairs spanning across different subtrees (or starting at u and going down)
                for (int b1 = 0; b1 <= 2; b1++) {
                    for (int w1 = 0; w1 <= 2; w1++) {
                        if (tempDp[b1][w1] == 0) continue;

                        for (int b2 = 0; b2 <= 2; b2++) {
                            for (int w2 = 0; w2 <= 2; w2++) {
                                if (dp[v][b2][w2] == 0) continue;

                                if (b1 + b2 <= 2 && w1 + w2 <= 2) {
                                    badPaths += tempDp[b1][w1] * dp[v][b2][w2];
                                }
                            }
                        }
                    }
                }

                // Incorporate child's paths into current node's DP
                for (int b2 = 0; b2 <= 2; b2++) {
                    for (int w2 = 0; w2 <= 2; w2++) {
                        if (dp[v][b2][w2] == 0) continue;

                        int b_new = b2 + u_b;
                        int w_new = w2 + u_w;
                        if (b_new <= 2 && w_new <= 2) {
                            tempDp[b_new][w_new] += dp[v][b2][w2];
                        }
                    }
                }
            }

            dp[u] = tempDp;
        }

        long totalPaths = (long) n * (n - 1) / 2;
        long validPaths = totalPaths - badPaths;

        System.out.println(validPaths);
    }
}
