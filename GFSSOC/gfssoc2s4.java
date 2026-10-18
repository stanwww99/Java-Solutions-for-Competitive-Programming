package GFSSOC;

import java.io.*;
import java.util.*;

public class gfssoc2s4 {
    static class Node{
        int vertex;
        int weight;
        public Node(int v, int w){
            this.vertex = v;
            this.weight = w;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int INF = (int)1e9;
    public static int[] dijkstra(int V, int src, List<List<Node>> g) {
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));
        dist[src] = 0;
        pq.add(new Node(src, 0));
        while(!pq.isEmpty()) {
            Node u = pq.poll();
            if(u.weight > dist[u.vertex]) continue;
            for(Node v: g.get(u.vertex)) {
                if (dist[v.vertex] > dist[u.vertex] + v.weight) {
                    dist[v.vertex] = dist[u.vertex] + v.weight;
                    pq.add(new Node(v.vertex, dist[v.vertex]));
                }
            }


        }
        return dist;
    }
    public static void main(String[] args) throws IOException {
        List<List<Node>> graph = new ArrayList<>();
        List<List<Node>> reverseGraph = new ArrayList<>();;
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] dist1;
        int[] dist2;
        for(int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w =  Integer.parseInt(st.nextToken());
            graph.get(u).add(new Node(v, w));
            reverseGraph.get(v).add(new Node(u, w));
        }
        dist1 = dijkstra(N, 0, graph);
        dist2 = dijkstra(N, N-1, reverseGraph);
        int minDist = dist1[N -1];
        int D = Integer.parseInt(br.readLine());
        for(int i = 0; i < D; i ++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w =  Integer.parseInt(st.nextToken());
            minDist = Math.min(dist1[u] + dist2[v] + w, minDist);
        }
        if(minDist == INF) {
            System.out.println(-1);
        }else {
            System.out.println(minDist);
        }

    }

}
