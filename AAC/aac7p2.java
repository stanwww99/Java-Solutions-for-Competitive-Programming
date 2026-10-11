package AAC;
import java.io.*;
import java.util.*;

public class aac7p2 {
    // Data structure to hold a connected component (each constructed tree)
    static class Component {
        List<Integer> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
    }

    // Simple edge container
    static class Edge {
        int u, v;
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input and output.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // Read number of nodes.
        int n = Integer.parseInt(br.readLine());

        // Build the given tree T as an undirected graph.
        List<Integer>[] tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            tree[u].add(v);
            tree[v].add(u);
        }

        // Compute depths and parents in T (rooted at 1).
        int[] depth = new int[n + 1];
        int[] parent = new int[n + 1];  // parent[i] is the parent of node i in T.
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        depth[1] = 0;
        parent[1] = 0;  // mark 1 as the root (its parent is 0)
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int nxt : tree[cur]) {
                if (nxt == parent[cur]) continue;
                parent[nxt] = cur;
                depth[nxt] = depth[cur] + 1;
                queue.add(nxt);
            }
        }

        // Build a "forest" from the nodes by linking each node (with depth >= 2)
        // to its grandparent (i.e. parent[parent[node]]). Since a tree is bipartite,
        // this connects only nodes of the same parity—ensuring that no T–edge is used.
        List<Integer>[] forest = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            forest[i] = new ArrayList<>();
        }
        for (int v = 2; v <= n; v++) {
            if (depth[v] >= 2) {
                int gp = parent[parent[v]]; // grandparent of v in T (never 0 because depth[v] >= 2)
                // Add an undirected edge (gp, v) to the forest.
                forest[gp].add(v);
                forest[v].add(gp);
            }
        }

        // We now get a forest (a set of trees) on all n nodes.
        // Each connected component of "forest" will be one constructed tree.
        boolean[] visited = new boolean[n + 1];
        List<Component> components = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                Component comp = new Component();
                dfs(i, -1, forest, visited, comp);
                components.add(comp);
            }
        }

        // Output: First print the number of trees (components) constructed.
        out.println(components.size());
        // For each tree, output: number of nodes then list its edges.
        for (Component comp : components) {
            out.println(comp.nodes.size());
            // Each component (if more than one node) has exactly (|nodes| - 1) DFS tree–edges.
            for (Edge e : comp.edges) {
                out.println(e.u + " " + e.v);
            }
        }
        out.flush();
        out.close();
    }

    // Standard DFS over the forest.
    private static void dfs(int u, int p, List<Integer>[] forest, boolean[] visited, Component comp) {
        visited[u] = true;
        comp.nodes.add(u);
        for (int v : forest[u]) {
            if (v == p) continue;
            if (!visited[v]) {
                // Record the edge used in the DFS tree.
                comp.edges.add(new Edge(u, v));
                dfs(v, u, forest, visited, comp);
            }
        }
    }
}