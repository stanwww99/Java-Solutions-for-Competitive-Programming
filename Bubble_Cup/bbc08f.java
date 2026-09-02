package BubbleCup;

import java.io.*;
import java.util.*;

public class bbc08f {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        long x0 = Long.parseLong(st.nextToken());

        long[][] interval = new long[n][2];
        List<Long> coordsList = new ArrayList<>(2*n + 1);
        coordsList.add(x0);

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            interval[i][0] = Long.parseLong(st.nextToken());
            interval[i][1] = Long.parseLong(st.nextToken());
            coordsList.add(interval[i][0]);
            coordsList.add(interval[i][1]);
        }

        Collections.sort(coordsList);
        List<Long> tmp = new ArrayList<>();
        tmp.add(coordsList.get(0));
        for (int i = 1; i < coordsList.size(); i++) {
            if (!coordsList.get(i).equals(coordsList.get(i - 1))) {
                tmp.add(coordsList.get(i));
            }
        }
        int m = tmp.size();
        long[] coords = new long[m];
        for (int i = 0; i < m; i++) {
            coords[i] = tmp.get(i);
        }
        int idx0 = Arrays.binarySearch(coords, x0);

        final long INF = Long.MAX_VALUE / 4;
        long[] dp = new long[m], g = new long[m], light = new long[m];
        Arrays.fill(dp, INF);
        dp[idx0] = 0;

        for (int t = 0; t < n; t++) {
            long L = interval[t][0];
            long R = interval[t][1];
            for (int k = 0; k < m; k++) {
                long x = coords[k];
                if      (x < L)   light[k] = L - x;
                else if (x > R)   light[k] = x - R;
                else              light[k] = 0;
            }
            for (int k = 0; k < m; k++) {
                g[k] = dp[k];
            }
            for (int k = 1; k < m; k++) {
                long d = coords[k] - coords[k - 1];
                g[k] = Math.min(g[k], g[k - 1] + d);
            }
            for (int k = m - 2; k >= 0; k--) {
                long d = coords[k + 1] - coords[k];
                g[k] = Math.min(g[k], g[k + 1] + d);
            }
            for (int k = 0; k < m; k++) {
                dp[k] = g[k] + light[k];
            }
        }


        long ans = INF;
        for (long v : dp) {
            ans = Math.min(ans, v);
        }
        System.out.println(ans);
    }
}
