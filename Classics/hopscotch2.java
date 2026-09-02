package classics;

import java.io.*;
import java.util.*;
public class hopscotch2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        long[] n = new long[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) n[i] = Long.parseLong(st.nextToken());
        if (K > N) {
            System.out.println(0);
            System.out.println();
            return;
        }
        long[] g = new long[N + 2];
        ArrayDeque<Integer> dq = new ArrayDeque<>();

        for (int i = N; i >= 1; --i) {
            if (i + 1 <= N) {
                long val = n[i + 1] + g[i + 1];

                while (!dq.isEmpty()) {
                    int idx = dq.peekLast();
                    long idxVal = n[idx] + g[idx];
                    if (idxVal >= val) dq.pollLast();
                    else break;
                }
                dq.addLast(i + 1);
            }
            while (!dq.isEmpty() && dq.peekFirst() > i + K) dq.pollFirst();

            if (i + K > N) {
                g[i] = 0L;
            } else {
                int j = dq.peekFirst();
                g[i] = n[j] + g[j];
            }
        }

        long ans = Long.MAX_VALUE;
        int start = -1;
        int upto = Math.min(K, N);
        for (int j = 1; j <= upto; j++) {
            long tot = n[j] + g[j];
            if (tot < ans || (tot == ans && j > start)) {
                ans = tot;
                start = j;
            }
        }
        System.out.println(ans);

        ArrayList<Integer> path = new ArrayList<>();
        int cur = start;
        if (cur != -1) path.add(cur);
        while (cur != -1 && cur + K <= N) {
            int chosen = -1;
            int L = cur + 1;
            int R = Math.min(N, cur + K);
            for (int j = R; j >= L; j--) {
                if (n[j] + g[j] == g[cur]) {
                    chosen = j;
                    break;
                }
            }
            if (chosen == -1) break;
            path.add(chosen);
            cur = chosen;
        }
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if(i != path.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
