package ECOO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ecoo19r1p1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 10; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M =Integer.parseInt(st.nextToken());
            int D =Integer.parseInt(st.nextToken());
            int remain = N;
            int times = 0;
            Map<Integer, Integer> days = new HashMap<>();
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                int temp = Integer.parseInt(st.nextToken());
                days.put(temp, days.getOrDefault(temp, 0) + 1);
            }
            for(int j = 1; j <= D; j++) {
                if(remain == 0) {
                    remain = N;
                    times++;
                }
                remain--;
                if(days.containsKey(j)) {
                    remain += days.get(j);
                    N += days.get(j);
                }

            }
            System.out.println(times);
        }

    }

}
