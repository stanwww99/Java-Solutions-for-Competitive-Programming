package ICPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class pacnw16h {
    static int findNextJob(int i, long[][] jobs) {
        long end = jobs[i][1];
        int ans = jobs.length;
        int s = i + 1, e = jobs.length - 1;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (jobs[mid][0] >= end) {
                ans = mid;
                e = mid - 1;
            } else {
                s = mid + 1;
            }
        }
        return ans;
    }
    static long maxProfit(long[][] seg) {
        int n = seg.length;
        Arrays.sort(seg, (x, y) -> Long.compare(x[0], y[0]));
        long[] dp = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = seg[i][2];
            int next = findNextJob(i, seg);
            if (next < n) {
                dp[i] += dp[next];
            }
            if (i < n - 1) {
                dp[i] = Math.max(dp[i], dp[i + 1]);
            }
        }
        return dp[0];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        long[][] seg = new long[k][3];
        for(int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            seg[i][0] = Long.parseLong(st.nextToken());
            seg[i][1] =Long.parseLong(st.nextToken());
            seg[i][2] = seg[i][1] - seg[i][0] +1;
        }
        System.out.println(n - maxProfit(seg));
    }

}