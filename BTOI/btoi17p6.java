package Baltic_OI;

import java.io.InputStream;
import java.io.IOException;

public class btoi17p6 {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner(System.in);

        int N = scanner.nextInt();
        int D = scanner.nextInt();

        int[] parent = new int[N];
        for (int i = 1; i < N; i++) {
            parent[i] = scanner.nextInt();
        }

        int[] M = new int[N];
        int[] U = new int[N];

        final int INF = 1_000_000_000;
        final int NEG_INF = -1_000_000_000;

        for (int i = 0; i < N; i++) {
            M[i] = INF;
            U[i] = 0;
        }

        int ans = 0;

        for (int i = N - 1; i >= 1; i--) {
            int p = parent[i];

            int mv = M[i] == INF ? INF : M[i] + 1;
            int uv = U[i] == NEG_INF ? NEG_INF : U[i] + 1;

            int M_new = Math.min(M[p], mv);
            int U_cur = U[p];

            // Invalidate if too close to already marked nodes
            if (U_cur != NEG_INF && mv != INF && U_cur + mv < D) {
                U_cur = NEG_INF;
            }
            if (uv != NEG_INF && M[p] != INF && uv + M[p] < D) {
                uv = NEG_INF;
            }

            int U_new;
            if (U_cur != NEG_INF && uv != NEG_INF && U_cur + uv >= D) {
                // Both active nodes are far enough apart, mark the deeper one
                ans++;
                M_new = Math.min(M_new, Math.max(U_cur, uv));
                U_new = Math.min(U_cur, uv);
            } else {
                // Keep the one that is deeper (better for future placements)
                U_new = Math.max(U_cur, uv);
            }

            M[p] = M_new;
            U[p] = U_new;
        }

        // If the root still has a valid unmarked active node, mark it!
        if (U[0] >= 0) {
            ans++;
        }

        System.out.println(ans);
    }

    // Fast I/O for competitive programming performance
    static class FastScanner {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int head, tail;

        public FastScanner(InputStream stream) {
            this.stream = stream;
        }

        int read() {
            if (head >= tail) {
                try {
                    tail = stream.read(buf, 0, buf.length);
                    if (tail <= 0) return -1;
                    head = 0;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return buf[head++];
        }

        public int nextInt() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new RuntimeException("Invalid digit");
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }
    }
}