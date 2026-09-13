package VM7WC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class vmss7wc16c4p3 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int D = Integer.parseInt(st.nextToken());
        int I = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        String A = st.nextToken();
        String B =  st.nextToken();
        A += '.';
        B += '.';
        long[][] dp = new long[A.length() + 1][B.length() + 1];
        for(int i = 0; i <= A.length(); i++) {
            Arrays.fill(dp[i], 100000000000l);
        }
        dp[A.length()][B.length()] = 0;
        for(int i = A.length() - 1; i >= 0; i--) {
            for(int j = B.length() - 1; j  >= 0; j--) {
                if(A.charAt(i)== B.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                }else {
                    dp[i][j] = Math.min(D + dp[i + 1][j], Math.min(I + dp[i][j + 1], R + dp[i + 1][j + 1]));
                }
            }
        }
        System.out.println(dp[0][0]);
    }

}