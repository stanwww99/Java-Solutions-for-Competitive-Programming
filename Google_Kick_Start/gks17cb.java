package Google_Kick_Start;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class gks17cb {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        int T = fs.nextInt();
        StringBuilder out = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {
            int N = fs.nextInt();
            char[][] g = new char[N][N];
            for (int i = 0; i < N; i++) {
                String s = fs.next();
                g[i] = s.toCharArray();
            }

            String ans = solveCase(g, N) ? "POSSIBLE" : "IMPOSSIBLE";
            out.append("Case #").append(tc).append(": ").append(ans).append('\n');
        }
        System.out.print(out.toString());
    }

    private static boolean solveCase(char[][] g, int N) {
        // N must be odd for the target shape
        if (N % 2 == 0) return false;

        int totalX = 0;
        int singletonRows = 0;
        int singletonCol = -1;

        // Map pair "a,b" -> count of rows that have Xs exactly at columns a and b
        Map<Long, Integer> pairCount = new HashMap<>();
        // Track columns used by pairs
        HashSet<Integer> usedCols = new HashSet<>();

        for (int i = 0; i < N; i++) {
            int cnt = 0;
            int first = -1, second = -1;
            for (int j = 0; j < N; j++) {
                if (g[i][j] == 'X') {
                    totalX++;
                    if (cnt == 0) first = j;
                    else if (cnt == 1) second = j;
                    cnt++;
                }
            }

            if (cnt == 0 || cnt > 2) return false; // cannot be part of target
            if (cnt == 1) {
                singletonRows++;
                if (singletonRows > 1) return false; // more than one singleton row
                singletonCol = first;
            } else { // cnt == 2
                int a = Math.min(first, second);
                int b = Math.max(first, second);
                long key = (((long)a) << 32) | (b & 0xffffffffL);
                pairCount.put(key, pairCount.getOrDefault(key, 0) + 1);
                usedCols.add(a);
                usedCols.add(b);
            }
        }

        // Total Xs must match target: 2*(N-1) + 1 = 2N - 1
        if (totalX != 2 * N - 1) return false;

        // Exactly one singleton row
        if (singletonRows != 1) return false;

        // Number of two-X rows must be N-1 and they must form (N-1)/2 unique pairs
        int twoXRows = N - 1;
        if (pairCount.values().stream().mapToInt(Integer::intValue).sum() != twoXRows) return false;

        int uniquePairs = pairCount.size();
        if (uniquePairs != (N - 1) / 2) return false;

        // Each unique pair must appear exactly twice
        for (int cnt : pairCount.values()) {
            if (cnt != 2) return false;
        }

        // Columns used by pairs must be exactly N-1 distinct columns (singleton column is the only unused one)
        if (usedCols.size() != N - 1) return false;
        if (singletonCol < 0 || usedCols.contains(singletonCol)) return false;

        // Ensure pairs are disjoint in columns: since usedCols size == 2*uniquePairs,
        // and uniquePairs == (N-1)/2, usedCols.size() == 2*uniquePairs must hold.
        if (usedCols.size() != 2 * uniquePairs) return false;

        // All checks passed
        return true;
    }

    // Fast scanner for input
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
