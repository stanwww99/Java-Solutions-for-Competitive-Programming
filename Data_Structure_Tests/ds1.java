package Data_Structure_Tests;

import java.io.*;
import java.util.StringTokenizer;

public class ds1 {

    // BIT for prefix sums (1-indexed)
    static class BIT {
        final int n;
        long[] bit;
        BIT(int n) { this.n = n; bit = new long[n + 1]; }
        void add(int i, long delta) {
            for (; i <= n; i += i & -i) bit[i] += delta;
        }
        long sum(int i) {
            long s = 0;
            for (; i > 0; i -= i & -i) s += bit[i];
            return s;
        }
        long rangeSum(int l, int r) { return sum(r) - sum(l - 1); }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        final int MAXV = 100000; // adjust if value range differs

        int[] arr = new int[N + 1];
        BIT bit = new BIT(N);           // for element sums
        BIT freq = new BIT(MAXV);       // frequency BIT over values
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int v = Integer.parseInt(st.nextToken());
            arr[i] = v;
            bit.add(i, v);
            freq.add(v, 1);
        }
        for (int op = 0; op < M; op++) {
            st = new StringTokenizer(br.readLine());
            String token = st.nextToken();
            if (token == null) break;
            char type = token.charAt(0);
            if (type == 'C') {
                int x =Integer.parseInt(st.nextToken());
                int v =Integer.parseInt(st.nextToken());
                int old = arr[x];
                if (old == v) continue;
                arr[x] = v;
                bit.add(x, (long)v - old);
                freq.add(old, -1);
                freq.add(v, 1);
            } else if (type == 'S') {
                int l =Integer.parseInt(st.nextToken());
                int r =Integer.parseInt(st.nextToken());
                System.out.println(bit.rangeSum(l, r));
            } else if (type == 'Q') {
                int v =Integer.parseInt(st.nextToken());
                if (v <= 0) {
                    System.out.println(0);
                } else if (v >= MAXV) {
                    System.out.println(freq.sum(MAXV));
                } else {
                    System.out.println((freq.sum(v)));
                }
            }
        }
    }
}
