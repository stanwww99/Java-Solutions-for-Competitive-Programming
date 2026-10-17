package TLE;
import java.io.*;
import java.util.Arrays;

public class tle16c6s5 {
    private static final long INF = Long.MAX_VALUE / 4;

    private static class FastInput {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

        private int read() throws IOException {
            if (pointer >= length) {
                length = in.read(buffer);
                pointer = 0;
                if (length <= 0) return -1;
            }
            return buffer[pointer++];
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            boolean negative = c == '-';
            if (negative) c = read();

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return negative ? -value : value;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    private static class FastOutput {
        private final OutputStream out = System.out;
        private final byte[] buffer = new byte[1 << 16];
        private final byte[] digits = new byte[20];
        private int pointer = 0;

        private void write(int c) throws IOException {
            if (pointer == buffer.length) flush();
            buffer[pointer++] = (byte) c;
        }

        void println(long value) throws IOException {
            if (value < 0) {
                write('-');
                value = -value;
            }

            int count = 0;
            do {
                digits[count++] = (byte) ('0' + value % 10);
                value /= 10;
            } while (value != 0);

            while (count > 0) write(digits[--count]);
            write('\n');
        }

        void cannotDeliver() throws IOException {
            String message = "Cannot Deliver\n";
            for (int i = 0; i < message.length(); i++) {
                write(message.charAt(i));
            }
        }

        void flush() throws IOException {
            out.write(buffer, 0, pointer);
            pointer = 0;
        }
    }

    // ceil(a / b), where b is positive, including when a is negative.
    private static long ceilDiv(long a, long b) {
        long result = a / b;
        if (a % b > 0) result++;
        return result;
    }

    public static void main(String[] args) throws IOException {
        FastInput in = new FastInput();
        FastOutput out = new FastOutput();

        int n = in.nextInt();
        int m = in.nextInt();
        int days = in.nextInt();

        int[] from = new int[m];
        int[] to = new int[m];
        long[] weight = new long[m];

        for (int i = 0; i < m; i++) {
            from[i] = in.nextInt() - 1;
            to[i] = in.nextInt() - 1;
            weight[i] = in.nextLong();
        }

        // dp[v][k]: minimum initial cost from city 1 to v
        // using exactly k edges.
        long[][] dp = new long[n][n];
        for (long[] row : dp) Arrays.fill(row, INF);
        dp[0][0] = 0;

        // The graph is a DAG, so a path has at most n - 1 edges.
        for (int k = 0; k < n - 1; k++) {
            boolean anyPath = false;

            for (int e = 0; e < m; e++) {
                long previous = dp[from[e]][k];
                if (previous == INF) continue;

                long candidate = previous + weight[e];
                if (candidate < dp[to[e]][k + 1]) {
                    dp[to[e]][k + 1] = candidate;
                }
                anyPath = true;
            }

            if (!anyPath) break;
        }

        // Each possible edge count k gives a line:
        // cost(x) = k * x + dp[v][k].
        // Store the lower envelope for each destination.
        int[][] slopes = new int[n][];
        long[][] starts = new long[n][];
        int[] hullSize = new int[n];

        for (int v = 0; v < n; v++) {
            long[] costs = dp[v];
            int count = 0;
            for (int k = 0; k < n; k++) {
                if (costs[k] != INF) count++;
            }

            if (count == 0) continue;

            int[] hull = new int[count];
            long[] start = new long[count];
            int size = 0;

            // Decreasing slopes: smaller slopes become optimal
            // as the cumulative toll change increases.
            for (int k = n - 1; k >= 0; k--) {
                if (costs[k] == INF) continue;

                long first = Long.MIN_VALUE;

                while (size > 0) {
                    int previousK = hull[size - 1];

                    // First integer x where the new line is
                    // no more expensive than the previous line.
                    first = ceilDiv(
                            costs[k] - costs[previousK],
                            previousK - k
                    );

                    if (first <= start[size - 1]) {
                        size--;
                    } else {
                        break;
                    }
                }

                if (size == 0) first = Long.MIN_VALUE;

                hull[size] = k;
                start[size] = first;
                size++;
            }

            slopes[v] = hull;
            starts[v] = start;
            hullSize[v] = size;
        }

        long totalChange = 0;

        for (int day = 0; day < days; day++) {
            totalChange += in.nextLong();
            int destination = in.nextInt() - 1;

            int size = hullSize[destination];
            if (size == 0) {
                out.cannotDeliver();
                continue;
            }

            long[] start = starts[destination];
            int low = 0;
            int high = size - 1;

            // Find the last line whose optimal interval has begun.
            while (low < high) {
                int mid = (low + high + 1) >>> 1;
                if (start[mid] <= totalChange) {
                    low = mid;
                } else {
                    high = mid - 1;
                }
            }

            int k = slopes[destination][low];
            long answer = dp[destination][k] + totalChange * k;
            out.println(answer);
        }

        out.flush();
    }
}