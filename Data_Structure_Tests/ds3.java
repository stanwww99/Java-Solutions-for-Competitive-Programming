package Data_Structure_Tests;
import java.io.*;

public class ds3 {
    static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    static int[] mergeNode(int lMin, int lGcd, int lCnt, int rMin, int rGcd, int rCnt) {
        if (lGcd == 0) return new int[]{rMin, rGcd, rCnt};
        if (rGcd == 0) return new int[]{lMin, lGcd, lCnt};
        int pMin = Math.min(lMin, rMin);
        int pGcd = gcd(lGcd, rGcd);
        int pCnt = 0;
        if (lGcd == pGcd) pCnt += lCnt;
        if (rGcd == pGcd) pCnt += rCnt;
        return new int[]{pMin, pGcd, pCnt};
    }

    // Fast input reader
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
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            int c = read();
            while (c <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
                c = read();
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int N = fs.nextInt();
        int M = fs.nextInt();

        int size = 1;
        while (size < N) size <<= 1;

        int segLen = 2 * size;
        int[] segMin = new int[segLen];
        int[] segGcd = new int[segLen];
        int[] segCnt = new int[segLen];
        for (int i = 0; i < segLen; i++) {
            segMin[i] = Integer.MAX_VALUE;
            segGcd[i] = 0;
            segCnt[i] = 0;
        }
        for (int i = 0; i < N; i++) {
            int v = fs.nextInt();
            segMin[size + i] = v;
            segGcd[size + i] = v;
            segCnt[size + i] = 1;
        }

        for (int i = size - 1; i >= 1; i--) {
            int left = 2 * i, right = 2 * i + 1;
            int[] merged = mergeNode(segMin[left], segGcd[left], segCnt[left],
                    segMin[right], segGcd[right], segCnt[right]);
            segMin[i] = merged[0];
            segGcd[i] = merged[1];
            segCnt[i] = merged[2];
        }

        for (int qi = 0; qi < M; qi++) {
            String op = fs.next();
            if (op.equals("C")) {
                int x = fs.nextInt() - 1;
                int v = fs.nextInt();
                int pos = size + x;
                segMin[pos] = v;
                segGcd[pos] = v;
                segCnt[pos] = 1;
                pos >>= 1;
                while (pos >= 1) {
                    int left = 2 * pos, right = 2 * pos + 1;
                    int[] merged = mergeNode(segMin[left], segGcd[left], segCnt[left],
                            segMin[right], segGcd[right], segCnt[right]);
                    segMin[pos] = merged[0];
                    segGcd[pos] = merged[1];
                    segCnt[pos] = merged[2];
                    pos >>= 1;
                }
            } else {
                int l = fs.nextInt() - 1;
                int r = fs.nextInt() - 1;
                int L = l + size;
                int R = r + size;
                int lMin = Integer.MAX_VALUE, lGcd = 0, lCnt = 0;
                int rMin = Integer.MAX_VALUE, rGcd = 0, rCnt = 0;
                while (L <= R) {
                    if ((L & 1) == 1) {
                        int[] merged = mergeNode(lMin, lGcd, lCnt, segMin[L], segGcd[L], segCnt[L]);
                        lMin = merged[0]; lGcd = merged[1]; lCnt = merged[2];
                        L++;
                    }
                    if ((R & 1) == 0) {
                        int[] merged = mergeNode(segMin[R], segGcd[R], segCnt[R], rMin, rGcd, rCnt);
                        rMin = merged[0]; rGcd = merged[1]; rCnt = merged[2];
                        R--;
                    }
                    L >>= 1; R >>= 1;
                }
                int[] ans = mergeNode(lMin, lGcd, lCnt, rMin, rGcd, rCnt);
                if (op.equals("M")) {
                    out.println(ans[0]);
                } else if (op.equals("G")) {
                    out.println(ans[1]);
                } else if (op.equals("Q")) {
                    out.println(ans[2]);
                }
            }
        }

        out.flush();
        out.close();
    }
}