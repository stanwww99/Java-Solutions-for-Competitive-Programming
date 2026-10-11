package AAC;
import java.io.*;
import java.util.*;

public class aac3p4 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        HashMap<Long,Integer> row = new HashMap<>();
        HashMap<Long,Integer> col = new HashMap<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            long a = Long.parseLong(st.nextToken());
            if (a != -1) {
                long key = a - i;
                row.put(key, row.getOrDefault(key, 0) + 1);
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int j = 1; j <= M; j++) {
            long b = Long.parseLong(st.nextToken());
            if (b != -1) {
                long key = b - j;
                col.put(key, col.getOrDefault(key, 0) + 1);
            }
        }

        long ans = 0;
        if (row.size() <= col.size()) {
            for (Map.Entry<Long,Integer> e : row.entrySet()) {
                ans += Math.min(e.getValue(), col.getOrDefault(e.getKey(), 0));
            }
        } else {
            for (Map.Entry<Long,Integer> e : col.entrySet()) {
                ans += Math.min(e.getValue(), row.getOrDefault(e.getKey(), 0));
            }
        }

        System.out.println(ans);
    }
}