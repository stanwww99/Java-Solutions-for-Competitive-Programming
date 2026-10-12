package Mock_CCO;
import java.io.*;
import java.util.*;

public class mcco19c2d2p3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] hate = new int[N];
        int[] indegree = new int[N];
        long[] dp0 = new long[N];   // max sum in subtree if this node is NOT chosen
        long[] dp1 = new long[N];   // max sum in subtree if this node IS chosen
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken()) - 1;
            dp1[i] = w;
            hate[i] = h;
            indegree[h]++;
        }

        // Kahn's algorithm to strip away cycles, computing dp for tree nodes
        Deque<Integer> queue = new ArrayDeque<>(N);
        boolean[] removed = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                removed[i] = true;
            }
        }
        while (!queue.isEmpty()) {
            int u = queue.poll();
            int p = hate[u];
            // accumulate child's contributions into parent p
            dp0[p] += Math.max(dp0[u], dp1[u]);
            dp1[p] += dp0[u];
            indegree[p]--;
            if (indegree[p] == 0) {
                removed[p] = true;
                queue.add(p);
            }
        }

        // Now remaining nodes (removed[i]==false) form disjoint cycles
        boolean[] seen = new boolean[N];
        long answer = 0L;
        final long NEG_INF = Long.MIN_VALUE / 4;

        for (int i = 0; i < N; i++) {
            if (removed[i] || seen[i]) continue;
            // extract the cycle
            ArrayList<Integer> cycle = new ArrayList<>();
            int cur = i;
            do {
                cycle.add(cur);
                seen[cur] = true;
                cur = hate[cur];
            } while (cur != i);

            int k = cycle.size();
            // extract dp0/ dp1 values for cycle nodes
            long[] c0 = new long[k];
            long[] c1 = new long[k];
            for (int j = 0; j < k; j++) {
                int node = cycle.get(j);
                c0[j] = dp0[node];
                c1[j] = dp1[node];
            }

            // Case 1: first node NOT chosen
            long prev0 = c0[0];
            long prev1 = NEG_INF;
            for (int j = 1; j < k; j++) {
                long cur0 = Math.max(prev0, prev1) + c0[j];
                long cur1 = prev0 + c1[j];
                prev0 = cur0;
                prev1 = cur1;
            }
            long best1 = Math.max(prev0, prev1);

            // Case 2: first node IS chosen
            prev0 = NEG_INF;
            prev1 = c1[0];
            for (int j = 1; j < k; j++) {
                long cur0 = Math.max(prev0, prev1) + c0[j];
                long cur1 = prev0 + c1[j];
                prev0 = cur0;
                prev1 = cur1;
            }
            long best2 = prev0;

            answer += Math.max(best1, best2);
        }

        System.out.println(answer);
    }
}