package YAC;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class yac8p2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Set<Integer> set = new HashSet<>();

        for(int i = 0; i < N; i++) {
            if(set.contains(arr[i] ^ X)) {
                System.out.println("YES");
                return;
            }
            set.add(arr[i]);

        }
        System.out.println("NO");


    }
}
