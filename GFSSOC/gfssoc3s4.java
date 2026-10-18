package GFSSOC;

import java.io.*;
import java.util.*;

public class gfssoc3s4 {
    static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int L = R - 1;
        int M = C - 2;
        ArrayList<Integer> validMasks = new ArrayList<>();
        int totalMasks = 1 << M;  // there are 2^M subsets
        for (int mask = 0; mask < totalMasks; mask++) {
            if (isValid(mask, M)) {
                validMasks.add(mask);
            }
        }
        int V = validMasks.size();

        int limit = (1 << M) - 1;
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] compat = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            compat[i] = new ArrayList<>();
        }
        for (int i = 0; i < V; i++) {
            int maskA = validMasks.get(i);
            int forbidden = ((maskA << 1) | maskA | (maskA >> 1)) & limit;
            for (int j = 0; j < V; j++) {
                int maskB = validMasks.get(j);
                if ((maskB & forbidden) == 0) {
                    compat[i].add(j);
                }
            }
        }

        long[][] dp = new long[L][V];
        for (int i = 0; i < V; i++) {
            dp[0][i] = 1;
        }


        for (int r = 1; r < L; r++) {
            for (int i = 0; i < V; i++) {
                long ways = dp[r-1][i];
                if (ways != 0) {
                    for (int j : compat[i]) {
                        dp[r][j] = (dp[r][j] + ways) % MOD;
                    }
                }
            }
        }
        long totalWays = 0;
        for (int i = 0; i < V; i++) {
            totalWays = (totalWays + dp[L - 1][i]) % MOD;
        }
        totalWays = (totalWays + MOD - 1) % MOD;

        System.out.println(totalWays);
    }
    private static boolean isValid(int mask, int M) {
        for (int i = 0; i < M; i++) {
            if ((mask & (1 << i)) != 0) {
                if (i + 1 < M && (mask & (1 << (i + 1))) != 0)
                    return false;
                if (i + 2 < M && (mask & (1 << (i + 2))) != 0)
                    return false;
            }
        }
        return true;
    }
}
