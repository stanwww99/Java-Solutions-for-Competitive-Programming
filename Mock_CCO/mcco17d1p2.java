package Mock_CCO;
import java.io.*;
import java.util.*;

public class mcco17d1p2 {
    static final int MAXN = 200000;
    static int N, K, Q;
    static long[] seg = new long[4 * MAXN];
    static long[] lazy = new long[4 * MAXN];

    static void pushDown(int i, int l, int r) {
        if (lazy[i] != 0) {
            seg[i] += lazy[i];
            if (l != r) {
                lazy[i * 2] += lazy[i];
                lazy[i * 2 + 1] += lazy[i];
            }
            lazy[i] = 0;
        }
    }

    static void update(int i, int l, int r, int ql, int qr, int diff) {
        pushDown(i, l, r);
        if (qr < l || r < ql) return;
        if (ql <= l && r <= qr) {
            lazy[i] += diff;
            pushDown(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        update(i * 2, l, mid, ql, qr, diff);
        update(i * 2 + 1, mid + 1, r, ql, qr, diff);
        seg[i] = Math.max(seg[i * 2], seg[i * 2 + 1]);
    }

    static long query(int i, int l, int r, int ql, int qr) {
        pushDown(i, l, r);
        if (qr < l || r < ql) return 0;
        if (ql <= l && r <= qr) return seg[i];
        int mid = (l + r) / 2;
        return Math.max(query(i * 2, l, mid, ql, qr),
                query(i * 2 + 1, mid + 1, r, ql, qr));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if (type == 0) {
                int idx = Integer.parseInt(st.nextToken());
                int diff = Integer.parseInt(st.nextToken());
                update(1, 0, N - 1, Math.max(0, idx - K + 1), idx, diff);
            } else {
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                System.out.println(query(1, 0, N - 1, l, r));
            }
        }
    }
}