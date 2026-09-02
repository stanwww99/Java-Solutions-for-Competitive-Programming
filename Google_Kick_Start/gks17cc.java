package Google_Kick_Start;

import java.io.*;
import java.util.*;

/**
 * Solution for "Magical Thinking" (Google Kickstart '17 Round C Problem C).
 *
 * Approach:
 * - Group columns by the pattern of friends' answers (mask of length N).
 * - For each group (mask) we know how many columns have your answer = T and = F.
 * - For each group choose x = number of columns (in that group) where the true answer is T.
 * - Friends' scores impose linear constraints on the x values.
 * - We brute-force x for each mask (number of masks = 2^N, N small in dataset) with backtracking,
 *   pruning using simple bounds on remaining columns.
 * - For a fixed x in a mask, compute the maximum contribution to *your* score from that mask
 *   using a closed-form expression.
 */
public class gks17cc {
    static int N, Q;
    static int[] S; // friends' scores (length N)
    static String[] answers; // length N+1, last is yours

    static int masksCount;
    static int[] cntTotal;   // cntTotal[mask] = number of columns with this friends-mask
    static int[] cntYouOn;   // cntYouOn[mask] = among those columns, how many have your answer = T
    static int totalColumnsRemaining; // sum of cntTotal for remaining masks (used for pruning)

    static int best;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int T = fs.nextInt();
        StringBuilder out = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {
            N = fs.nextInt();
            Q = fs.nextInt();
            answers = new String[N + 1];
            for (int i = 0; i < N + 1; i++) answers[i] = fs.next();
            S = new int[N];
            for (int i = 0; i < N; i++) S[i] = fs.nextInt();

            masksCount = 1 << N;
            cntTotal = new int[masksCount];
            cntYouOn = new int[masksCount];

            // Build counts per mask
            for (int col = 0; col < Q; col++) {
                int mask = 0;
                for (int i = 0; i < N; i++) {
                    if (answers[i].charAt(col) == 'T') mask |= (1 << i);
                }
                cntTotal[mask]++;
                if (answers[N].charAt(col) == 'T') cntYouOn[mask]++;
            }

            // Prepare order of masks (we'll iterate masks in any fixed order)
            // Precompute prefix sums of remaining columns for pruning
            totalColumnsRemaining = 0;
            for (int m = 0; m < masksCount; m++) totalColumnsRemaining += cntTotal[m];

            best = 0;
            int[] curFriend = new int[N]; // partial sums for friends
            dfs(0, curFriend, 0, totalColumnsRemaining);
            out.append(String.format("Case #%d: %d\n", tc, best));
        }
        System.out.print(out.toString());
    }

    /**
     * Backtracking over masks.
     *
     * @param maskIndex index of current mask (0..masksCount-1)
     * @param curFriend partial scores accumulated for each friend
     * @param curYou    partial score accumulated for you
     * @param remaining total number of columns remaining (including current and later masks)
     */
    static void dfs(int maskIndex, int[] curFriend, int curYou, int remaining) {
        if (maskIndex == masksCount) {
            // All masks assigned: check if friends' scores match exactly
            for (int i = 0; i < N; i++) if (curFriend[i] != S[i]) return;
            best = Math.max(best, curYou);
            return;
        }

        int cnt = cntTotal[maskIndex];
        if (cnt == 0) {
            // nothing to assign for this mask
            dfs(maskIndex + 1, curFriend, curYou, remaining);
            return;
        }

        // Quick pruning: for each friend i, curFriend[i] must not exceed S[i],
        // and curFriend[i] + remaining - cnt (max possible from future masks) must be >= S[i].
        int remainingAfterThis = remaining - cnt;
        for (int x = 0; x <= cnt; x++) {
            // x = number of columns in this mask where true answer = T
            boolean ok = true;
            // compute friends' contributions for this mask and check bounds
            for (int i = 0; i < N; i++) {
                int bit = ((maskIndex >> i) & 1);
                int add = (bit == 1) ? x : (cnt - x);
                int newSum = curFriend[i] + add;
                if (newSum > S[i]) { ok = false; break; }
                // maximum possible additional from future masks is remainingAfterThis
                if (newSum + remainingAfterThis < S[i]) { ok = false; break; }
            }
            if (!ok) continue;

            // compute your contribution from this mask for chosen x
            int cntYou1 = cntYouOn[maskIndex];
            int cntYou0 = cnt - cntYou1;
            // For given x, maximum matches you can get from this mask:
            // choose as many of the x true-answer columns to be those where your answer is T.
            // contribution = cntYou0 - x + 2*min(cntYou1, x)
            int contribYou;
            int minVal = Math.min(cntYou1, x);
            contribYou = cntYou0 - x + 2 * minVal;
            // Update curFriend and recurse
            for (int i = 0; i < N; i++) {
                int bit = ((maskIndex >> i) & 1);
                int add = (bit == 1) ? x : (cnt - x);
                curFriend[i] += add;
            }
            dfs(maskIndex + 1, curFriend, curYou + contribYou, remainingAfterThis);
            // undo
            for (int i = 0; i < N; i++) {
                int bit = ((maskIndex >> i) & 1);
                int add = (bit == 1) ? x : (cnt - x);
                curFriend[i] -= add;
            }
        }
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
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') { if (c == -1) return null; }
            do { sb.append((char)c); c = read(); } while (c > ' ');
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}

