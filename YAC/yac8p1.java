package YAC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class yac8p1 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] perm = new int[N];
        for(int i = 0; i <N; i++) {
            perm[i] = Integer.parseInt(st.nextToken());
        }
        int max = 0;
        int cost = 0;
        int consecutive = 0;
        for(int i = 0; i <N; i++) {
            max = Math.max(max, perm[i]);
            if(i + 1 == perm[i] && max == perm[i]) {
                cost += consecutive;
                consecutive = 0;
                max = 0;
            }else {
                consecutive++;
            }
        }
        System.out.println(cost + consecutive);
    }

}