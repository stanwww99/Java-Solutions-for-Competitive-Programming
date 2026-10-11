package EGOI;

import java.io.*;
import java.util.*;

public class egoi23p5 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        // rankings: for i from 1..N-1, read i integers
        // We'll store each ranking line as an array for building pos when needed.
        List<int[]> rankings = new ArrayList<>(N);
        rankings.add(new int[0]); // placeholder for general 0

        for (int i = 1; i < N; i++) {
            int[] arr = new int[i];
            for (int k = 0; k < i; k++) arr[k] = fs.nextInt();
            rankings.add(arr);
        }

        ArrayList<Integer> seq = new ArrayList<>();
        if (N > 0) seq.add(0);

        for (int j = 1; j < N; j++) {
            int[] rank = rankings.get(j); // length j, rank[0] best ... rank[j-1] worst
            int[] pos = new int[j]; // pos[x] = index of x in j's ranking
            for (int idx = 0; idx < j; idx++) {
                pos[rank[idx]] = idx;
            }
            boolean[] allowed = new boolean[j];
            int threshold = (j - 1) / 2; // allowed if pos <= threshold
            for (int x = 0; x < j; x++) {
                allowed[x] = (pos[x] <= threshold);
            }

            // find insertion index k in [0..seq.size()] such that
            // left neighbor (if any) is allowed and right neighbor (if any) is allowed
            boolean inserted = false;
            int len = seq.size();
            for (int k = 0; k <= len; k++) {
                boolean leftOk = (k == 0) || allowed[seq.get(k - 1)];
                boolean rightOk = (k == len) || allowed[seq.get(k)];
                if (leftOk && rightOk) {
                    seq.add(k, j);
                    inserted = true;
                    break;
                }
            }
            // By problem guarantee, insertion must succeed.
            if (!inserted) {
                // Fallback (should not happen): append at end
                seq.add(j);
            }
        }

        // print sequence
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seq.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(seq.get(i));
        }
        System.out.println(sb.toString());
    }

    // Fast scanner for competitive programming
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
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}
