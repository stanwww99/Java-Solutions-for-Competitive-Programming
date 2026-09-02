package Google_Code_Jam;

import java.util.*;

public class gcj14r1ab {
    static final int MAXN = 1000;
    static final int INF = (int) 1e8;

    static int n;
    static List<Integer>[] adjs = new ArrayList[MAXN];
    static boolean[] visited = new boolean[MAXN];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            n = scanner.nextInt();

            for (int i = 0; i < n; i++) {
                if (adjs[i] == null) {
                    adjs[i] = new ArrayList<>();
                } else {
                    adjs[i].clear();
                }
            }

            for (int i = 0; i < n - 1; i++) {
                int a = scanner.nextInt() - 1;
                int b = scanner.nextInt() - 1;
                adjs[a].add(b);
                adjs[b].add(a);
            }

            int best = n - 1;
            for (int i = 0; i < n; i++) {
                Arrays.fill(visited, false);
                best = Math.min(best, n - dfs(i));
            }
            System.out.println("Case #" + tc + ": " + best);
        }
        scanner.close();
    }

    static int dfs(int k) {
        visited[k] = true;

        List<Integer> counts = new ArrayList<>();
        for (int i : adjs[k]) {
            if (!visited[i]) {
                counts.add(dfs(i));
            }
        }

        int children = counts.size();
        if (children <= 1) return 1;

        Collections.sort(counts);
        return 1 + counts.get(children - 1) + counts.get(children - 2);
    }
}