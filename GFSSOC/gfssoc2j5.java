package GFSSOC;
import java.io.*;
import java.util.*;


public class gfssoc2j5 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int[] prefixMax = new int[N + 1];
        int[][] prefixfreq = new int[N + 1][10];
        int[][] suffixFreq = new int[N + 1][10];
        int[] suffixMax = new int[N + 1];
        int[] value = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            value[i] = Integer.parseInt(st.nextToken());
            prefixMax[i + 1] = Math.max(value[i], prefixMax[i]);
        }
        for(int i = N - 1; i >= 0; i--) {
            suffixMax[i] = Math.max(value[i], suffixMax[i + 1]);
        }
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < 10; j++) {
                if(value[i] - 1 == j) {
                    prefixfreq[i + 1][j] = prefixfreq[i][j] + 1;
                }else {
                    prefixfreq[i + 1][j] = prefixfreq[i][j];
                }
            }
        }
        for(int i = N - 1; i >= 0; i--) {
            for(int j = 0; j < 10; j++) {
                if(value[i] - 1 == j) {
                    suffixFreq[i][j] = suffixFreq[i + 1][j] + 1;
                }else {
                    suffixFreq[i][j] = suffixFreq[i + 1][j];
                }
            }
        }
        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int max = Math.max(prefixMax[a - 1], suffixMax[b]);
            System.out.println(max + " " + (prefixfreq[a - 1][max - 1] + suffixFreq[b][max - 1]));
        }

    }

}
