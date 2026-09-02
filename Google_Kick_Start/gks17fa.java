package Google_Kick_Start;

import java.io.*;
import java.util.*;

/**
 * Optimized Kicksort checker (Google Kickstart '17 Round F Problem A).
 * For each test case prints "Case #t: YES" or "Case #t: NO".
 *
 * Approach:
 * - Use a Fenwick tree (BIT) to maintain which indices are still present.
 * - Use a TreeSet of remaining values to get current min and max quickly.
 * - At each step compute pivot position k = floor((m-1)/2) (0-based) -> k+1 (1-based),
 *   find the original index of the k-th remaining element via BIT,
 *   check whether its value equals current min or max; if not -> NO.
 * - Remove that element (update BIT and TreeSet) and continue.
 */
public class gks17fa {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int T = fs.nextInt();
        StringBuilder out = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {
            int N = fs.nextInt();
            int[] A = new int[N + 1]; // 1-based index for convenience
            int[] pos = new int[N + 1]; // pos[value] = index in array (1-based)
            for (int i = 1; i <= N; i++) {
                A[i] = fs.nextInt();
                pos[A[i]] = i;
            }
            boolean ok = checkKicksort(N, A, pos);
            out.append("Case #").append(tc).append(": ").append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(out.toString());
    }

    private static boolean checkKicksort(int N, int[] A, int[] pos) {
        Fenwick bit = new Fenwick(N);
        for (int i = 1; i <= N; i++) bit.add(i, 1); // all indices present

        TreeSet<Integer> remainingValues = new TreeSet<>();
        for (int v = 1; v <= N; v++) remainingValues.add(v);

        int m = N;
        while (m > 1) {
            int pivotOrder0 = (m - 1) / 2; // 0-based
            int kth = pivotOrder0 + 1;     // 1-based order for BIT
            int idx = bit.findByOrder(kth); // original index (1-based)
            int val = A[idx];

            int minVal = remainingValues.first();
            int maxVal = remainingValues.last();
            if (val != minVal && val != maxVal) return false;

            // remove this element
            remainingValues.remove(val);
            bit.add(idx, -1);
            m--;
        }
        return true;
    }

    // Fenwick tree (1-based) with findByOrder (k-th one) via binary lifting
    private static class Fenwick {
        final int n;
        final int[] bit;
        Fenwick(int n) {
            this.n = n;
            this.bit = new int[n + 1];
        }
        void add(int idx, int delta) {
            for (; idx <= n; idx += idx & -idx) bit[idx] += delta;
        }
        int sum(int idx) {
            int s = 0;
            for (; idx > 0; idx -= idx & -idx) s += bit[idx];
            return s;
        }
        // find smallest index such that prefix sum >= k (1 <= k <= total)
        int findByOrder(int k) {
            int idx = 0;
            int bitMask = highestPowerOfTwo(n);
            for (int d = bitMask; d != 0; d >>= 1) {
                int next = idx + d;
                if (next <= n && bit[next] < k) {
                    idx = next;
                    k -= bit[next];
                }
            }
            return idx + 1;
        }
        private int highestPowerOfTwo(int x) {
            int p = 1;
            while (p << 1 <= x) p <<= 1;
            return p;
        }
    }

    // Fast scanner
    private static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;
        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
