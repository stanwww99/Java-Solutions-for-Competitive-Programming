package VM7WC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
final class Edgers{
    private final int dest;
    private final int dist;
    public Edgers(int dest, int dist){
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
        Edgers p = (Edgers) obj;
        return (this.dest == p.getDest() && this.dist == p.dist);
    }
    public int getDest() {
        return dest;
    }
    public int getDist() {
        return dist;
    }
}

class Graphers{
    public final Map<Integer, Integer> dist =  new HashMap<>(); //Stores the distance
    public final Set<Integer> visited = new HashSet<>();
    public final PriorityQueue<Edgers> queue = new PriorityQueue<>((a, b) -> Double.compare(a.getDist(), b.getDist()));
    private final Map<Integer, List<Edgers>> graph = new HashMap<>(); //Stores the graph
    public Map<Integer, List<Edgers>> returnGraph() {
        return graph;
    }
    public void addVertex(int v){
        graph.putIfAbsent(v, new LinkedList<>());
    }
    public void addEdge(int source, int destination, int weight){
        addVertex(source);
        addVertex(destination);
        graph.get(source).add(new Edgers(destination, weight));
        graph.get(destination).add(new Edgers(source, weight));
    }
    public void dijkstra(int startVertex) {

        for(int v: graph.keySet()) {
            dist.put(v, 99999999);
        }
        dist.put(startVertex, 0);
        relax(startVertex);
        while(!queue.isEmpty()) {
            int vertex = queue.poll().getDest();
            visited.add(vertex);
            relax(vertex);
        }
    }
    public void relax(int vertex) {
        for(Edgers v: graph.get(vertex)) {
            if (dist.get(v.getDest()) > dist.get(vertex) + v.getDist()) {
                dist.put(v.getDest(), dist.get(vertex) + v.getDist()) ;
                queue.add(new Edgers(v.getDest(), v.getDist()));
            }
        }
    }
}
public class vmss7wc16c3p3 {
    static 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        Graphers g = new Graphers();
        for(int i = 1; i <= N; i++) {
            g.addVertex(i);
        }
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            g.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        g.dijkstra(B);
        for(int i = 1; i <= Q; i++) {
            int dest = Integer.parseInt(br.readLine());
            int dists = g.dist.get(dest);
            System.out.println((dists == 99999999)? -1: dists);
        }
    }

}
