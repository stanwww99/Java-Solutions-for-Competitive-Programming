package Baltic_OI;
import java.io.*;
import java.util.*;

public class btoi09p2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] x = new int[n + 1];
        int[] y = new int[n + 1];
        long[] U = new long[n + 1];
        long[] V = new long[n + 1];
        Integer[] order = new Integer[n];
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            x[i] = a;
            y[i] = b;
            U[i] = (long) a - (long) b;
            V[i] = (long) a + (long) b;
            order[i - 1] = i;
        }
        Arrays.sort(order, (a, b) -> {
            if (U[a] != U[b]) return Long.compare(U[a], U[b]);
            return Long.compare(V[b], V[a]);
        });
        TreeSet<Long> set = new TreeSet<>(new Comparator<Long>() {
            public int compare(Long A, Long B) {
                long va = A >> 32;
                long vb = B >> 32;
                if (va != vb) return Long.compare(va, vb);
                int ia = (int) (A.longValue());
                int ib = (int) (B.longValue());
                return Integer.compare(ia, ib);
            }
        });
        int[] ans = new int[n + 1];
        int tot = 0;
        for (int id : order) {
            long vv = V[id];
            long searchKey = (vv << 32);
            Long found = set.ceiling(searchKey);
            if (found == null) {
                ans[id] = ++tot;
            } else {
                int existingId = (int) (found.longValue());
                ans[id] = ans[existingId];
                set.remove(found);
            }
            long insertKey = (vv << 32) | (id & 0xffffffffL);
            set.add(insertKey);
        }
        System.out.println(tot);
        for (int i = 1; i <= n; i++) {
            System.out.println(x[i] + " " + y[i] + " " + ans[i]);
        }
    }
}
