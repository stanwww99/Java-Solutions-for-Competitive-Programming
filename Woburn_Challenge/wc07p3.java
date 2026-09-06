package Woburn_Challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class wc07p3{
    static List<Integer> [] graph;
    static int mod = 13371337;
    static 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // A "state" in the DP recursion is defined by the current node and the visited mask.
    // The visited mask uses a bitmask representation where the i-th bit is 1 if vertex i has been visited.
    static class State {
        int node;
        int mask;

        public State(int node, int mask) {
            this.node = node;
            this.mask = mask;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State state = (State) o;
            return node == state.node && mask == state.mask;
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, mask);
        }
    }

    /**
     * Recursive method using DP to count the number of simple paths from the current node to dest.
     * @param current The current vertex.
     * @param dest    The destination vertex.
     * @param mask    An int bitmask representing the set of visited vertices.
     * @param memo    A memoization map to cache computed states.
     * @return        The number of simple paths from the current vertex to dest.
     */
    private static long dp(int current, int dest, int mask, Map<State, Long> memo) {
        // If we reached destination, count as one valid path.
        if (current == dest) {
            return 1;
        }

        State state = new State(current, mask);
        if (memo.containsKey(state)) {
            return memo.get(state);
        }

        long count = 0;
        // Explore each neighbor. Note that parallel edges are considered separately
        // because the neighbor may appear more than once in the adjacency list.
        for (int neighbor : graph[current]) {
            // Skip if neighbor has already been visited
            if ((mask & (1 << neighbor)) == 0) {
                // Include the neighbor in the visited mask for further recursion.
                count += dp(neighbor, dest, mask | (1 << neighbor), memo);
            }
        }
        // Memorize and return the computed count for this state.
        memo.put(state, count);
        return count;
    }

    /**
     * Public API to count the number of simple paths from source to destination.
     * @param src   The source vertex.
     * @param dest  The destination vertex.
     * @return      The total number of simple paths from src to dest.
     */
    public static long countSimplePaths(int src, int dest) {
        // A map to memoize state results: (current node, visited mask) -> number of paths.
        Map<State, Long> memo = new HashMap<>();
        // The starting mask has only the source vertex visited.
        return dp(src, dest, (1 << src), memo);
    }
    public static void addEdge(int src, int dest) {
        graph[src].add(dest);
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NumberFormatException, IOException {
        int T = Integer.parseInt(br.readLine());
        for(int i = 0; i < T; i++) {
            int N = Integer.parseInt(br.readLine());
            graph = new List[N];
            for(int j =0; j < N; j++) {
                graph[j] = new ArrayList<>();
            }
            for(int j =0; j < N; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int k = 0; k < N; k++) {
                    int connect = Integer.parseInt(st.nextToken());
                    if(connect == 1) {
                        graph[j].add(k);
                    }
                }
            }
            System.out.println(countSimplePaths(0, N -1) % mod);


        }
    }

}
