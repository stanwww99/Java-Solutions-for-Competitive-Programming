package Woburn_Challenge;
import java.util.*;
import java.io.*;

public class wc18c4s1 {
    static List<Integer>[] graph;
    static char[] control;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] connect;
    static void addVertex(int v) {
        graph[v] = new ArrayList<>();
    }

    static void addEdge(int src, int dest) {
        graph[src].add(dest);
        graph[dest].add(src);
    }

    public static void dfsAll() {
        boolean[] visited = new boolean[graph.length];
        int component = 1;
        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) {
                dfs(i, visited, component);
                component++;
            }
        }
    }
    public static List<Integer> adj(int node) {
        List<Integer> adj = new ArrayList<>();
        for(int i: graph[node]) {
            if(control[node] == control[i]) {
                adj.add(i);
            }
        }
        return adj;
    }
    public static void dfs(int node, boolean[] visited, int component) {
        visited[node] = true;
        connect[node] = component;
        for(int i: adj(node)) {
            if(!visited[i]) {
                dfs(i, visited, component);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N];
        connect = new int[N];
        for (int i = 0; i < N; i++) {
            addVertex(i);
        }
        control = br.readLine().toCharArray();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken())  - 1;
            int dest = Integer.parseInt(st.nextToken()) - 1;
            addEdge(src, dest);
        }
        dfsAll();
        int safe = 0;
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken())  - 1;
            int dest = Integer.parseInt(st.nextToken()) - 1;
            if(connect[src] == connect[dest]) {
                safe++;
            }
        }
        System.out.println(safe);
    }
}
