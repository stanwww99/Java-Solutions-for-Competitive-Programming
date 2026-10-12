package Mock_CCO;

import java.io.*;
import java.util.*;

public class ccoprep6p2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = (int) Long.parseLong(st.nextToken());
        }
        int max= 1;
        int[] dp = new int[31];
        for(int i: a) {
            int num = i;
            int prev = 0;
            while(num > 0) {
                int b = Integer.numberOfTrailingZeros(num);
                prev = Math.max(prev, dp[b]);
                num = num & (num -1);
            }
            int curr = prev + 1;
            max = Math.max(curr, max);
            num = i;
            while(num > 0) {
                int b = Integer.numberOfTrailingZeros(num);
                dp[b] = Math.max(curr, dp[b]);
                num = num & (num -1);
            }
        }
        System.out.println(max);
    }
}