package WAC;

import java.io.*;
import java.util.*;

/**
 * Optimized solution for "Wesley's Anger Contest Reject 6 - Geology" (wacreject5).
 *
 * Approach:
 *  - Let g = gcd(N, M). Each A cell expands to blockA = M/g small units per axis.
 *    Each B cell expands to blockB = N/g small units per axis.
 *  - Represent each row/column cell as an interval on the expanded axis.
 *  - Use two-pointer merging to enumerate all overlapping row-pairs (i,p) with
 *    their vertical overlap v. There are at most N+M such pairs.
 *  - For each overlapping row-pair (i,p,v), use two-pointer merging across columns
 *    to compute the total horizontal overlap h where A[i][j] == B[p][q], summing v*h.
 *  - This yields total matching small-cells. Convert to fraction and output
 *    matches * inv(total) mod 998244353.
 *
 * Complexity:
 *  - Building vertical overlap pairs: O(N+M)
 *  - For each vertical pair, scanning columns with two pointers: O(N+M)
 *  - Total worst-case: O((N+M)^2) ~ 25e6 operations for N,M <= 2500 (fast enough).
 */
public class wacreject5 {
    static final long MOD = 998244353L;

    static long modPow(long a, long e) {
        long res = 1 % MOD;
        a %= MOD;
        while (e > 0) {
            if ((e & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            e >>= 1;
        }
        return res;
    }

    static long modInv(long a) {
        return modPow((a % MOD + MOD) % MOD, MOD - 2);
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    // Pair to store overlapping row (or column) indices and overlap length
    static class Overlap {
        int aIdx;
        int bIdx;
        long len;
        Overlap(int aIdx, int bIdx, long len) {
            this.aIdx = aIdx;
            this.bIdx = bIdx;
            this.len = len;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int N = fs.nextInt();
        char[][] A = new char[N][];
        for (int i = 0; i < N; i++) A[i] = fs.next().toCharArray();

        int M = fs.nextInt();
        char[][] B = new char[M][];
        for (int i = 0; i < M; i++) B[i] = fs.next().toCharArray();

        int g = gcd(N, M);
        long blockA = M / g; // size of each A cell on expanded axis
        long blockB = N / g; // size of each B cell on expanded axis
        long l = (long)N * blockA; // lcm(N,M) = N * blockA = M * blockB

        // Build vertical overlaps between rows of A and rows of B
        ArrayList<Overlap> vert = new ArrayList<>();
        int i = 0, p = 0;
        long aStart = 0, bStart = 0;
        while (i < N && p < M) {
            long aEnd = aStart + blockA;
            long bEnd = bStart + blockB;
            long overlap = Math.max(0L, Math.min(aEnd, bEnd) - Math.max(aStart, bStart));
            if (overlap > 0) vert.add(new Overlap(i, p, overlap));
            if (aEnd == bEnd) {
                i++; p++;
                aStart = aEnd;
                bStart = bEnd;
            } else if (aEnd < bEnd) {
                i++;
                aStart = aEnd;
            } else {
                p++;
                bStart = bEnd;
            }
        }

        // Precompute column intervals similarly are implicit; we'll two-pointer per vertical pair.
        long matches = 0L;

        // For each overlapping row-pair, compute horizontal matches by two-pointer across columns
        for (Overlap vo : vert) {
            int rowA = vo.aIdx;
            int rowB = vo.bIdx;
            long v = vo.len;

            int j = 0, q = 0;
            long colAStart = 0, colBStart = 0;
            while (j < N && q < M) {
                long colAEnd = colAStart + blockA;
                long colBEnd = colBStart + blockB;
                long hOverlap = Math.max(0L, Math.min(colAEnd, colBEnd) - Math.max(colAStart, colBStart));
                if (hOverlap > 0) {
                    if (A[rowA][j] == B[rowB][q]) {
                        matches += v * hOverlap;
                    }
                }
                if (colAEnd == colBEnd) {
                    j++; q++;
                    colAStart = colAEnd;
                    colBStart = colBEnd;
                } else if (colAEnd < colBEnd) {
                    j++;
                    colAStart = colAEnd;
                } else {
                    q++;
                    colBStart = colBEnd;
                }
            }
        }

        long total = l * l;
        matches %= MOD;
        long P = matches;
        long Q = (total % MOD + MOD) % MOD;
        long ans = (P * modInv(Q)) % MOD;
        System.out.println(ans);
    }

    // Fast scanner
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
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return null;
            }
            do {
                sb.append((char)c);
                c = read();
            } while (c > ' ');
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}

