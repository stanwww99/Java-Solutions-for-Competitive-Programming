package VM7WC;
import java.io.*;
import java.util.*;

/**
 * Solution for "Jayden Plays Video Games" (VM7WC '16 #5 Silver).
 * Reads N, then N lines of (x, h). Outputs minimum number of cuts.
 */
public class vmss7wc16c5p2 {
    static class Tree implements Comparable<Tree> {
        long x, h;
        Tree(long x, long h) { this.x = x; this.h = h; }
        public int compareTo(Tree o) { return Long.compare(this.x, o.x); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        Tree[] a = new Tree[N];
        for (int i = 0; i < N; i++) {
            long x = fs.nextLong();
            long h = fs.nextLong();
            a[i] = new Tree(x, h);
        }
        Arrays.sort(a);

        // Compute rightReach: index of rightmost tree knocked down when i falls right
        int[] rightReach = new int[N];
        for (int i = 0; i < N; i++) {
            long reachPos = a[i].x + a[i].h;
            int j = i + 1;
            while (j < N && a[j].x <= reachPos) {
                // include tree j and extend reach if needed
                reachPos = Math.max(reachPos, a[j].x + a[j].h);
                j++;
            }
            rightReach[i] = j - 1;
        }

        // Compute leftReach: index of leftmost tree knocked down when i falls left
        int[] leftReach = new int[N];
        for (int i = 0; i < N; i++) {
            long reachPos = a[i].x - a[i].h;
            int j = i - 1;
            while (j >= 0 && a[j].x >= reachPos) {
                reachPos = Math.min(reachPos, a[j].x - a[j].h);
                j--;
            }
            leftReach[i] = j + 1;
        }

        final int INF = 1_000_000_000;
        int[] dp = new int[N];
        Arrays.fill(dp, INF);

        for (int i = 0; i < N; i++) {
            // Option: cut some k to the right so that it covers up to i
            for (int k = 0; k <= i; k++) {
                if (rightReach[k] >= i) {
                    int cost = 1 + (k > 0 ? dp[k - 1] : 0);
                    if (cost < dp[i]) dp[i] = cost;
                }
            }
            // Option: cut i to the left, covering leftReach[i]..i
            int leftStart = leftReach[i];
            int costLeft = 1 + (leftStart > 0 ? dp[leftStart - 1] : 0);
            if (costLeft < dp[i]) dp[i] = costLeft;
        }

        System.out.println(dp[N - 1]);
    }

    // Fast scanner for input
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Long.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
