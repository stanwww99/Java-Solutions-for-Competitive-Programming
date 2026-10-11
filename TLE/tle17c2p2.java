package TLE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class tle17c2p2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int[] prefixSum = new int[1000001];
        Set<Integer> unlucky = new HashSet<>();
        int K = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < K; i++) {
            unlucky.add(Integer.parseInt(st.nextToken()));
        }
        for(int i = 0; i < 1000000; i++) {
            if(unlucky.contains(i + 1)) {
                prefixSum[i + 1] = prefixSum[i];
            }else {
                prefixSum[i + 1] = prefixSum[i] + 1;
            }
        }
        int N = Integer.parseInt(br.readLine());
        for(int i = 0; i < N; i++) {
            System.out.println(prefixSum[Integer.parseInt(br.readLine())]);
        }
    }

}
