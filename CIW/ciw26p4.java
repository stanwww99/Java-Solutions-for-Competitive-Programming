package CIW;
import java.io.*;
import java.util.*;

public class ciw26p4 {
    static class Customer {
        int lSeg; // left segment index (1-based)
        long profit;
        Customer(int l, long p) { lSeg = l; profit = p; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long T = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        int[] custL = new int[N];
        int[] custR = new int[N];
        long[] custV = new long[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            custL[i] = Integer.parseInt(st.nextToken());
            custR[i] = Integer.parseInt(st.nextToken());
            custV[i] = Long.parseLong(st.nextToken());
        }

        long[] w = new long[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            w[i] = Long.parseLong(st.nextToken());
        }
        long[] wpref = new long[M+1];
        for (int i = 0; i < M; i++) wpref[i+1] = wpref[i] + w[i];

        // Collect all critical times: 1, T+1, and each l_i and r_i+1
        TreeSet<Long> ts = new TreeSet<>();
        ts.add(1L);
        ts.add(T+1);
        for (int i = 0; i < N; i++) {
            ts.add((long)custL[i]);
            ts.add((long)custR[i] + 1);
        }
        List<Long> timesList = new ArrayList<>(ts);
        int S = timesList.size() - 1; // number of segments
        long[] times = new long[S+1];
        for (int i = 0; i <= S; i++) times[i] = timesList.get(i);

        // lengths of segments
        long[] len = new long[S+1]; // 1-based
        long[] pre = new long[S+1]; // prefix sum of lengths (pre[0]=0, pre[i] = sum len[1..i])
        for (int i = 1; i <= S; i++) {
            len[i] = times[i] - times[i-1];
            pre[i] = pre[i-1] + len[i];
        }

        // Map each customer to segment indices
        int[] custSegL = new int[N];
        int[] custSegR = new int[N];
        for (int i = 0; i < N; i++) {
            custSegL[i] = findSegment(times, custL[i]);
            custSegR[i] = findSegment(times, custR[i]);
        }

        // Group customers by their right segment
        ArrayList<Customer>[] custByR = new ArrayList[S+2];
        for (int i = 0; i <= S+1; i++) custByR[i] = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            custByR[custSegR[i]].add(new Customer(custSegL[i], custV[i]));
        }

        // Precompute block profits: blockProfit[p][q] for p < q, where p and q are point indices (0..S)
        // block covers segments from p+1 to q
        long[][] blockProfit = new long[S+1][S+1];
        for (int p = 0; p <= S; p++) Arrays.fill(blockProfit[p], -1L);

        for (int p = 0; p < S; p++) {
            boolean[] covered = new boolean[S+2]; // segments 1..S
            int firstUncovered = p+1;
            long sumProfit = 0;
            for (int q = p+1; q <= S; q++) {
                // add customers whose right segment == q and left >= p+1
                for (Customer cust : custByR[q]) {
                    if (cust.lSeg >= p+1) {
                        sumProfit += cust.profit;
                        // mark its range
                        for (int seg = cust.lSeg; seg <= q; seg++) {
                            if (!covered[seg]) {
                                covered[seg] = true;
                                if (seg == firstUncovered) {
                                    while (firstUncovered <= S && covered[firstUncovered]) firstUncovered++;
                                }
                            }
                        }
                    }
                }
                if (firstUncovered > q) {
                    blockProfit[p][q] = sumProfit;
                }
            }
        }

        // DP: dp[p][b] = max profit from point p to end, having already done b tasks
        long NEG = Long.MIN_VALUE / 2;
        long[][] dp = new long[S+1][M+1];
        for (int p = 0; p <= S; p++) Arrays.fill(dp[p], NEG);
        for (int b = 0; b <= M; b++) dp[S][b] = 0;

        for (int p = S-1; p >= 0; p--) {
            for (int b = 0; b <= M; b++) {
                long best = NEG;
                for (int q = p+1; q <= S; q++) {
                    // free run from p to q
                    long L = pre[q] - pre[p];
                    long maxTasks = L / K;
                    int d = (int) Math.min(maxTasks, M - b);
                    long reward = wpref[b+d] - wpref[b];
                    long val = dp[q][b+d] + reward;
                    if (val > best) best = val;

                    // block
                    if (blockProfit[p][q] != -1) {
                        val = dp[q][b] + blockProfit[p][q];
                        if (val > best) best = val;
                    }
                }
                dp[p][b] = best;
            }
        }

        long ans = dp[0][0];
        System.out.println(ans);
    }

    // Given sorted array of times (starts of segments), find segment index (1-based) containing time x
    static int findSegment(long[] times, long x) {
        // times[0] = 1, times[1] = next, ...
        // We need the largest i such that times[i] <= x, then segment = i+1
        int lo = 0, hi = times.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (times[mid] <= x) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        // hi is the largest index with times[hi] <= x
        return hi + 1;
    }
}
