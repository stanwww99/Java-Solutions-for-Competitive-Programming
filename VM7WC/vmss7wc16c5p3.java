package VM7WC;

import java.io.*;
import java.util.*;

public class vmss7wc16c5p3 {
    static List<Integer>[] tree;
    static int[] depth;
    static int N, K;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        tree = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            tree[u].add(v);
            tree[v].add(u);
        }

        // Step 1: Find the initial diameter
        depth = new int[N + 1];
        int farthestNode1 = bfs(1);
        int farthestdist = bfsdist(farthestNode1);
        System.out.println(farthestdist);
    }
    private static int bfsdist(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];
        queue.add(start);
        visited[start] = true;
        depth[start] = 0;
        int farthestNode = start;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : tree[node]) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    depth[neighbor] = depth[node] + 1;
                    if (depth[neighbor] > depth[farthestNode]) {
                        farthestNode = neighbor;
                    }
                }
            }
        }

        return depth[farthestNode];
    }
    private static int bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];
        queue.add(start);
        visited[start] = true;
        depth[start] = 0;
        int farthestNode = start;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : tree[node]) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    depth[neighbor] = depth[node] + 1;
                    if (depth[neighbor] > depth[farthestNode]) {
                        farthestNode = neighbor;
                    }
                }
            }
        }

        return farthestNode;
    }

}