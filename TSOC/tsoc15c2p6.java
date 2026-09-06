package TSOC;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

class Node {
    int mi;
    long lazy;
}

public class tsoc15c2p6 {
    static Node[] st = new Node[5000000];
    static int[] arr = new int[1000005];
    static FastReader fr = new FastReader();
    static {
        for (int i = 0; i < st.length; i++) {
            st[i] = new Node();
        }
        Arrays.fill(arr, Integer.MAX_VALUE);
    }

    static int build(int l, int r, int v) {
        if (l == r) {
            return st[v].mi = arr[l];
        }
        int m = (l + r) >> 1;
        return st[v].mi = Math.min(build(l, m, v << 1), build(m + 1, r, v << 1 | 1));
    }

    static void pushdown(int l, int r, int v) {
        if (st[v].lazy != 0) {
            st[v].mi -= Math.min((long) st[v].mi, st[v].lazy);
            if (l != r) {
                st[v << 1].lazy += st[v].lazy;
                st[v << 1 | 1].lazy += st[v].lazy;
            }
            st[v].lazy = 0;
        }
    }

    static int run(int l, int r, int v, int li, int ri, int c) {
        pushdown(l, r, v);
        if (l > ri || r < li) return Integer.MAX_VALUE;
        if (l >= li && r <= ri) {
            st[v].lazy = c;
            pushdown(l, r, v);
            return st[v].mi;
        }
        int m = (l + r) >> 1;
        int ans = Math.min(run(l, m, v << 1, li, ri, c),
                run(m + 1, r, v << 1 | 1, li, ri, c));
        st[v].mi = Math.min(st[v << 1].mi, st[v << 1 | 1].mi);
        return ans;
    }

    public static void main(String[] args) throws IOException {
        int n = fr.nextInt(), q = fr.nextInt();
        for (int x = 1; x <= n; x++) {
            arr[x] = fr.nextInt();
        }
        build(1, n, 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int l = fr.nextInt(), r = fr.nextInt(), c = fr.nextInt();
            int ans = run(1, n, 1, l, r, c);
            sb.append(ans).append(" ").append(st[1].mi).append("\n");
        }
        System.out.print(sb);
    }
    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                bytesRead = din.read(buffer, 0, BUFFER_SIZE);
                bufferPointer = 0;
                if (bytesRead == -1) return -1;
            }
            return buffer[bufferPointer++];
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            return neg ? -ret : ret;
        }
    }
}