package VM7WC;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
class RoadMap{
    Map<Integer, List<Integer>> graph = new HashMap<>();
    void addVertex(int v){
        graph.putIfAbsent(v, new LinkedList<Integer>());
    }
    void addEdge(int source, int destination){
        addVertex(source);
        addVertex(destination);
        graph.get(source).add(destination);
        graph.get(destination).add(source);
    }
    boolean isConnected(int startVertex, int endVertex){
        Set<Integer> visited = new HashSet<>();//Create visited vertices
        DFSRecursion(startVertex, visited);
        return visited.contains(endVertex);
    }
    void DFSRecursion(int startVertex, Set<Integer> visited){
        visited.add(startVertex); //Mark as visited
        for(int i: getAdjVertices(startVertex)) {
            if(!visited.contains(i)) { //Check if the vertex is visited
                DFSRecursion(i, visited); //Call the method recursively to mark the next Vertex
                DFSRecursion(i, visited);
                DFSRecursion(i, visited);
                DFSRecursion(i, visited);
                DFSRecursion(i, visited);
                DFSRecursion(i, visited);
            }
        }

    }
    List<Integer> getAdjVertices(int v){
        return graph.getOrDefault(v, new LinkedList<>());
    }
}
public class vmss7wc16c3p2 {
    public static void main(String[] args) {
        RoadMap r = new RoadMap();
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int M = s.nextInt();
        int A = s.nextInt();
        int B = s.nextInt();
        for(int i = 1; i <= N; i++) {
            r.addVertex(i);
        }
        for(int i = 0; i < M; i++) {
            int x = s.nextInt();
            int y = s.nextInt();
            r.addEdge(x, y);
        }
        System.out.println(r.isConnected(A,B)? "GO SHAHIR!":"NO SHAHIR!");
        s.close();
    }
}