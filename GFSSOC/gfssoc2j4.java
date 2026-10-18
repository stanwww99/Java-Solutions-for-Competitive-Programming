package GFSSOC;

import java.util.*;
import java.io.*;

public class gfssoc2j4 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws NumberFormatException, IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int[] trees = new int[N];
        int[] suffixSum = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            trees[i] = Integer.parseInt(st.nextToken());
        }
        for(int i = N; i >= 1; i--) {
            suffixSum[i - 1] = trees[i - 1] + suffixSum[i];

        }

        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            System.out.println(suffixSum[0] - (suffixSum[a - 1] - suffixSum[b]));
        }
    }

}
