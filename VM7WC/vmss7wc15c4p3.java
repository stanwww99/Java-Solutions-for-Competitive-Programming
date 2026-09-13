package VM7WC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
class Edge{
    int dest;
    long dist;
    public Edge(int dest, long dist){
        this.dest = dest;
        this.dist = dist;
    }
}
public class vmss7wc15c4p3 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static List<Edge> graph[];
    static long INF = 10000000000l;
    public static long[] dijkstra(int src) {
        long[] dist = new long[graph.length];
        Arrays.fill(dist, INF);
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Long.compare(a.dist, b.dist));
        dist[src] = 0;
        pq.add(new Edge(src, 0l));
        while(!pq.isEmpty()) {
            int vertex = pq.poll().dest;
            for(Edge v: graph[vertex]) {
                if (dist[v.dest] > dist[vertex] + v.dist) {
                    dist[v.dest] = dist[vertex] + v.dist;
                    pq.add(new Edge(v.dest, v.dist));
                }
            }

        }
        return dist;
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        graph = new List[N];
        for(int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dest = Integer.parseInt(st.nextToken());
            int dist =  Integer.parseInt(st.nextToken());
            graph[src].add(new Edge(dest, dist));
            graph[dest].add(new Edge(src, dist));
        }
        long[] dist1 = dijkstra(0);
        long[] dist2 = dijkstra(N - 1);
        long maxdist = -INF;
        for(int i = 0; i < N; i ++) {
            maxdist = Math.max(dist1[i] + dist2[i], maxdist);
        }
        System.out.println(maxdist);
    }

}


