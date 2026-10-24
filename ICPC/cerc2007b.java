package ICPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class cerc2007b {
    static int R, C;
    static int[][] init; // 0 = white, 1 = black

    // Flip effect offsets: self, up, down, left, right
    static final int[] dr = {0, -1, 1, 0, 0};
    static final int[] dc = {0, 0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        R = fs.nextInt();
        C = fs.nextInt();
        init = new int[R][C];
        for (int i = 0; i < R; i++) {
            String s = fs.next();
            for (int j = 0; j < C; j++) {
                init[i][j] = (s.charAt(j) == 'X') ? 1 : 0;
            }
        }

        int best = Integer.MAX_VALUE;
        int maxMask = 1 << C;
        for (int mask = 0; mask < maxMask; mask++) {
            int taps = tryMask(mask);
            if (taps >= 0 && taps < best) best = taps;
        }

        if (best == Integer.MAX_VALUE) {
            System.out.println("Damaged billboard.");
        } else {
            System.out.println("You have to tap " + best + " tiles.");
        }
    }

    // Try a given first-row tap pattern encoded in mask.
    // Return number of taps if it leads to all-white, otherwise -1.
    static int tryMask(int mask) {
        int[][] a = new int[R][C];
        // copy initial
        for (int i = 0; i < R; i++) System.arraycopy(init[i], 0, a[i], 0, C);

        int tapsCount = 0;
        int[][] tapped = new int[R][C]; // 1 if tapped

        // apply first row taps according to mask
        for (int j = 0; j < C; j++) {
            if (((mask >> j) & 1) == 1) {
                tap(a, tapped, 0, j);
                tapsCount++;
            }
        }

        // propagate downwards
        for (int r = 0; r < R - 1; r++) {
            for (int c = 0; c < C; c++) {
                if (a[r][c] == 1) { // still black, must tap below
                    tap(a, tapped, r + 1, c);
                    tapsCount++;
                }
            }
        }

        // check last row all white
        for (int c = 0; c < C; c++) {
            if (a[R - 1][c] == 1) return -1;
        }
        return tapsCount;
    }

    // Tap at (r,c): flip (r,c) and its 4-neighbors in array a, and mark tapped.
    static void tap(int[][] a, int[][] tapped, int r, int c) {
        tapped[r][c] = 1;
        for (int k = 0; k < 5; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];
            if (nr >= 0 && nr < R && nc >= 0 && nc < C) {
                a[nr][nc] ^= 1;
            }
        }
    }

    // Fast scanner for input
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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