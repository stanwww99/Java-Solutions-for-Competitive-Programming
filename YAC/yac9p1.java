package YAC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class yac9p1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] s = new int[N];
        int[] t = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            t[i] = Integer.parseInt(st.nextToken());
        }

        // Mapping the position of each element in the target array t
        Map<Integer, Integer> posInT = new HashMap<>();
        for (int i = 0; i < N; i++) {
            posInT.put(t[i], i);
        }

        // Count the minimum number of contiguous subsequences
        int minCuts = 1;
        for (int i = 1; i < N; i++) {
            if (posInT.get(s[i]) != posInT.get(s[i - 1]) + 1) {
                minCuts++;
            }
        }

        System.out.println(minCuts);
    }
}

