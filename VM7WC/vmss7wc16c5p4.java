package VM7WC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class vmss7wc16c5p4 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] len = new int[3];
        len[0] = Integer.parseInt(st.nextToken());
        len[1] = Integer.parseInt(st.nextToken());
        len[2] = Integer.parseInt(st.nextToken());
        int[] dp = new int[N + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for(int i = 0; i <= N; i++) {
            for(int j: len) {
                if(i - j >= 0 && dp[i - j] != -1) {
                    dp[i] = Math.max(dp[i], dp[i - j] + 1);
                }
            }
        }
        System.out.println(dp[N]);
    }

}