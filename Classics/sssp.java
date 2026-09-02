package classics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
final class Edge{
    private final int dest;
    private final int dist;
    public Edge(int dest, int dist){
        this.dest = dest;
        this.dist = dist;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.dest, this.dist);
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Edge p = (Edge) obj;
        return (this.dest == p.getDest() && this.dist == p.dist);
    }
    public int getDest() {
        return dest;
    }
    public int getDist() {
        return dist;
    }
}

class Graph{
    public final Map<Integer, Integer> dist =  new HashMap<>(); //Stores the distance
    public final PriorityQueue<Edge> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.getDist(), b.getDist()));
    private final Map<Integer, List<Edge>> graph = new HashMap<>(); //Stores the graph
    public Map<Integer, List<Edge>> returnGraph() {
        return graph;
    }
    public void addVertex(int v){
        graph.putIfAbsent(v, new LinkedList<>());
    }
    public void addEdge(int source, int destination, int weight){
        addVertex(source);
        addVertex(destination);
        graph.get(source).add(new Edge(destination, weight));
        graph.get(destination).add(new Edge(source, weight));
    }
    public void dijkstra(int startVertex) {
        for(int v: graph.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        dist.put(startVertex, 0);
        queue.add(new Edge(startVertex, 0));
        while(!queue.isEmpty()) {
            Edge vertex = queue.poll();
            if(vertex.getDist() > dist.get(vertex.getDest())) continue;
            for(Edge v: graph.get(vertex.getDest())) {
                if (dist.get(v.getDest()) > dist.get(vertex.getDest()) + v.getDist()) {
                    dist.put(v.getDest(), dist.get(vertex.getDest()) + v.getDist()) ;
                    queue.add(new Edge(v.getDest(), v.getDist() + + dist.get(vertex.getDest())));
                }
            }
        }
    }
}
public class sssp {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Graph g = new Graph();
        for(int i = 1; i <= N; i++) {
            g.addVertex(i);
        }
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        g.dijkstra(1);
        for(int i: g.dist.keySet()) {
            if(g.dist.get(i) == Integer.MAX_VALUE) {
                System.out.println(-1);
            }else {
                System.out.println(g.dist.get(i));
            }
        }
    }

}