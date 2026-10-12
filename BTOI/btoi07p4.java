package Baltic_OI;

import java.io.*;
import java.util.*;

public class btoi07p4 {
    static class Point { int x, y; Point(int x, int y){ this.x = x; this.y = y; } }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int m = Integer.parseInt(br.readLine().trim());
        int[] x1 = new int[m], y1 = new int[m], x2 = new int[m], y2 = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            x1[i] = Integer.parseInt(st.nextToken());
            y1[i] = Integer.parseInt(st.nextToken());
            x2[i] = Integer.parseInt(st.nextToken());
            y2[i] = Integer.parseInt(st.nextToken());
        }

        Point[] c = new Point[4 * m];
        for (int i = 0; i < m; i++) {
            c[4*i]   = new Point(x1[i], y1[i]);
            c[4*i+1] = new Point(x1[i], y2[i]);
            c[4*i+2] = new Point(x2[i], y1[i]);
            c[4*i+3] = new Point(x2[i], y2[i]);
        }

        int N = 8 * m;
        ArrayList<int[]>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < 4*m; i++) {
            for (int j = 0; j < 4*m; j++) {
                if (i == j) continue;
                int u1 = Math.min(c[i].x, c[j].x), u2 = Math.max(c[i].x, c[j].x);
                int v1 = Math.min(c[i].y, c[j].y), v2 = Math.max(c[i].y, c[j].y);
                int k;
                for (k = 0; k < m; k++) {
                    if (!(u1 >= x2[k]) && !(u2 <= x1[k]) && !(v1 >= y2[k]) && !(v2 <= y1[k])) break;
                }
                if (k == m) {
                    int p = ((u1 >= x2[0]) && (v2 >= y2[0]) && (v1 < y2[0])) ? 1 : 0;
                    int w = (u2 - u1) + (v2 - v1);
                    adj[i].add(new int[]{ j + p * 4 * m, w });
                    adj[i + 4*m].add(new int[]{ j + (1 - p) * 4 * m, w });
                }
            }
        }

        final int INF = Integer.MAX_VALUE / 4;
        int ans = INF;
        for (int s = 0; s < 4*m; s++) {
            int[] dist = new int[N];
            Arrays.fill(dist, INF);
            dist[s] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            pq.add(new int[]{0, s});
            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int d = cur[0], u = cur[1];
                if (d > dist[u]) continue;
                if (u == s + 4*m) break;
                for (int[] e : adj[u]) {
                    int v = e[0], w = e[1];
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        pq.add(new int[]{dist[v], v});
                    }
                }
            }
            if (dist[s + 4*m] < INF) ans = Math.min(ans, dist[s + 4*m]);
        }

        if (ans == INF) System.out.println("-1");
        else System.out.println(ans);
    }
}
