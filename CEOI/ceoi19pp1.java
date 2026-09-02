package CEOI;

import java.io.*;
import java.util.*;

public class ceoi19pp1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int h = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        long[] ys = new long[h];
        long[] xs = new long[v];

        if (h > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < h; i++) ys[i] = Long.parseLong(st.nextToken());
        } else {
            br.readLine();
        }

        if (v > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < v; i++) xs[i] = Long.parseLong(st.nextToken());
        } else {
            br.readLine();
        }
        HashMap<Long, Long> cntY = new HashMap<>();
        HashMap<Long, Long> cntX = new HashMap<>();

        for (int i = 0; i < h; i++) {
            for (int j = i + 1; j < h; j++) {
                long d = ys[j] - ys[i];
                cntY.put(d, cntY.getOrDefault(d, 0L) + 1L);
            }
        }

        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                long d = xs[j] - xs[i];
                cntX.put(d, cntX.getOrDefault(d, 0L) + 1L);
            }
        }

        long ans = 0L;
        if (cntY.size() < cntX.size()) {
            for (Map.Entry<Long, Long> e : cntY.entrySet()) {
                Long d = e.getKey();
                Long fy = e.getValue();
                Long fx = cntX.get(d);
                if (fx != null) ans += fy * fx;
            }
        } else {
            for (Map.Entry<Long, Long> e : cntX.entrySet()) {
                Long d = e.getKey();
                Long fx = e.getValue();
                Long fy = cntY.get(d);
                if (fy != null) ans += fy * fx;
            }
        }

        System.out.println(ans);
    }
}
