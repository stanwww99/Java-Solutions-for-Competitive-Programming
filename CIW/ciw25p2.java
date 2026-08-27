package CIW;

import java.io.*;
import java.util.*;

public class ciw25p2 {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        int[] psa = new int[N+1];
        int[][] dp = new int[N+1][N+1];

        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 1; i <= N; i++) {
            dp[i][i] = Integer.parseInt(st.nextToken());
            psa[i] = dp[i][i] + psa[i-1];
        }
        for (int len = 2; len <= N; len++) {
            for (int i = 1, j = len; i <= N - len + 1; i++, j++) {
                int sum = psa[j] - psa[i-1];
                dp[i][j] = sum;
                dp[j][i] = Integer.MAX_VALUE;
                // try every possible first split k
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], Math.max(dp[k][i], dp[j][k+1]));
                    dp[j][i] = Math.min(dp[j][i], Math.max(dp[i][k], dp[k+1][j]));
                }
                if(dp[j][i] < sum)  dp[j][i] = sum;
            }
        }
        System.out.println(dp[1][N]);
    }
}
