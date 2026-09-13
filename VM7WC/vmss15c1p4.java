package VM7WC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
final class Edgerss{
    private final int dest;
    private final long dist;
    public Edgerss(int dest, long dist){
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
        Edgerss p = (Edgerss) obj;
        return (this.dest == p.getDest() && this.dist == p.dist);
    }
    public int getDest() {
        return dest;
    }
    public long getDist() {
        return dist;
    }
}

class Grapherss{
    public final Map<Integer, Long> dist =  new HashMap<>(); //Stores the distance
    public final PriorityQueue<Edgerss> queue = new PriorityQueue<>((a, b) -> Double.compare(a.getDist(), b.getDist()));
    private final Map<Integer, List<Edgerss>> graph = new HashMap<>(); //Stores the graph
    public final List<Integer> foodBasics = new ArrayList<>();
    public Map<Integer, List<Edgerss>> returnGraph() {
        return graph;
    }
    public void addVertex(int v){
        graph.putIfAbsent(v, new LinkedList<>());
    }
    public void addEdge(int source, int destination, long weight){
        addVertex(source);
        addVertex(destination);
        graph.get(source).add(new Edgerss(destination, weight));
    }
    public void dijkstra(int startVertex) {

        for(int v: graph.keySet()) {
            dist.put(v, 999999999999l);
        }
        dist.put(startVertex, 0l);
        relax(startVertex);
        while(!queue.isEmpty()) {
            int vertex = queue.poll().getDest();
            relax(vertex);
        }
    }
    public void relax(int vertex) {
        for(Edgerss v: graph.get(vertex)) {
            if (dist.get(v.getDest()) > dist.get(vertex) + v.getDist()) {
                dist.put(v.getDest(), dist.get(vertex) + v.getDist()) ;
                queue.add(new Edgerss(v.getDest(), v.getDist()));
            }
        }
    }
}
public class vmss15c1p4 {
    static 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());
        Grapherss g = new Grapherss();
        for(int i = 0; i <= N; i++) {
            g.addVertex(i);
        }
        for(int i = 0; i < G; i++) {
            g.foodBasics.add(Integer.parseInt(br.readLine()));
        }
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        g.dijkstra(0);
        int count = 0;
        for(int i = 1; i <= N; i++) {
            if(g.foodBasics.contains(i)) {
                long dists = g.dist.get(i);
                if(dists < T) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

}