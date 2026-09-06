package TSOC;
import java.util.*;

public class tsoc15c1p5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int R = sc.nextInt();

        List<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < R; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }
        int W = sc.nextInt();
        boolean[] dispenser = new boolean[N + 1];
        for (int i = 0; i < W; i++) {
            int d = sc.nextInt();
            dispenser[d] = true;
        }

        int INF = Integer.MAX_VALUE / 2;
        int[] antTime = new int[N + 1];
        Arrays.fill(antTime, INF);
        Queue<Integer> aq = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            if (dispenser[i]) {
                antTime[i] = 0;
                aq.offer(i);
            }
        }

        while (!aq.isEmpty()) {
            int cur = aq.poll();
            for (int next : graph[cur]) {
                int newTime = antTime[cur] + 4;  // 4 seconds per road for ants
                if (newTime < antTime[next]) {
                    antTime[next] = newTime;
                    aq.offer(next);
                }
            }
        }
        int[] bmpTime = new int[N + 1];
        Arrays.fill(bmpTime, INF);
        if (0 < antTime[1]) {
            bmpTime[1] = 0;
            Queue<Integer> bq = new LinkedList<>();
            bq.offer(1);

            while (!bq.isEmpty()) {
                int cur = bq.poll();
                for (int next : graph[cur]) {
                    int newTime = bmpTime[cur] + 1;
                    if (newTime < antTime[next] && newTime < bmpTime[next]) {
                        bmpTime[next] = newTime;
                        bq.offer(next);
                    }
                }
            }
        }
        if (bmpTime[N] == INF) {
            System.out.println("sacrifice bobhob314");
        } else {
            System.out.println(bmpTime[N]);
        }
    }
}
