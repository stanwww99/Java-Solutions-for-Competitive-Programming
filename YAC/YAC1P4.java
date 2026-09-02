package YAC;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class YAC1P4 {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = Integer.parseInt(fs.next());
        String S = fs.next().trim();

        int[] v = new int[N];
        for (int i = 0; i < N; i++) v[i] = S.charAt(i) - 'A' + 1;

        final int MAX_PER = 26 * 26;
        final int MAX_SUM = MAX_PER * N;
        final int OFFSET = MAX_SUM;

        BigInteger[] dpPrev = new BigInteger[N + 1];
        BigInteger[] dpNext = new BigInteger[N + 1];

        BigInteger zero = BigInteger.ZERO;
        BigInteger startBit = BigInteger.ONE.shiftLeft(OFFSET);
        for (int i = 0; i <= N; i++) dpPrev[i] = zero;
        dpPrev[0] = startBit;

        for (int pos = 0; pos < N; pos++) {
            int x = v[pos];
            int shift = x * x;
            for (int i = 0; i <= N; i++) dpNext[i] = zero;

            for (int open = 0; open <= N; open++) {
                BigInteger cur = dpPrev[open];
                if (cur.equals(BigInteger.ZERO)) continue;

                dpNext[open] = dpNext[open].or(cur);

                if (open + 1 <= N) {
                    BigInteger shiftedLeft = cur.shiftLeft(shift);
                    dpNext[open + 1] = dpNext[open + 1].or(shiftedLeft);
                }

                if (open - 1 >= 0) {
                    BigInteger shiftedRight = cur.shiftRight(shift);
                    dpNext[open - 1] = dpNext[open - 1].or(shiftedRight);
                }
            }

            BigInteger[] tmp = dpPrev;
            dpPrev = dpNext;
            dpNext = tmp;
        }

        BigInteger result = dpPrev[0];
        ArrayList<Integer> ans = new ArrayList<>();
        int bitIndex = result.getLowestSetBit();
        while (bitIndex >= 0) {
            int value = bitIndex - OFFSET;
            ans.add(value);
            result = result.clearBit(bitIndex);
            bitIndex = result.getLowestSetBit();
        }
        Collections.sort(ans);

        StringBuilder sb = new StringBuilder();
        sb.append(ans.size()).append('\n');
        for (int vout : ans) sb.append(vout).append('\n');
        System.out.print(sb.toString());
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

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
                sb.append((char) c);
                c = read();
            } while (c > ' ');
            return sb.toString();
        }
    }
}