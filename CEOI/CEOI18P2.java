package CEOI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CEOI18P2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());

        long[] T = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            T[i] = Long.parseLong(st.nextToken());
        }

        // R[i] will store the length of the LIS starting at index i
        int[] R = new int[n];
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = Long.MIN_VALUE;

        // Compute R[i] by evaluating right-to-left
        for (int i = n - 1; i >= 0; i--) {
            long val = -T[i];
            int low = 1, high = n, pos = 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (dp[mid] >= val) {
                    high = mid - 1;
                } else {
                    pos = mid + 1;
                    low = mid + 1;
                }
            }
            dp[pos] = val;
            R[i] = pos;
        }

        long[] dp_pref = new long[n + 1];
        Arrays.fill(dp_pref, Long.MAX_VALUE);
        dp_pref[0] = Long.MIN_VALUE;
        int ans = 0;

        // Compute LIS prefix left-to-right and combine the result
        for (int i = 0; i < n; i++) {
            long target = T[i] + x;
            int low = 1, high = n, bestLen = 0;

            // Find longest prefix ending with a value < T[i] + x
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (dp_pref[mid] < target) {
                    bestLen = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            // The maximum sequence combined by prefix ending prior to i, and suffix starting at i
            ans = Math.max(ans, bestLen + R[i]);

            // Update dp_pref array with current element T[i]
            low = 1;
            high = n;
            int pos = 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (dp_pref[mid] >= T[i]) {
                    high = mid - 1;
                } else {
                    pos = mid + 1;
                    low = mid + 1;
                }
            }
            dp_pref[pos] = Math.min(dp_pref[pos], T[i]);
        }

        System.out.println(ans);
    }
}


