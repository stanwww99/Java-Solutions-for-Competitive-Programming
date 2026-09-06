package Woburn_Challenge;

import java.io.*;
import java.util.*;

public class wc18c2s1{
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int Xe = fs.nextInt();
        int Ye = fs.nextInt();
        int N = fs.nextInt();
        int M = fs.nextInt();
        int C = fs.nextInt();

        int[] V = new int[N];
        for (int i = 0; i < N; i++) V[i] = fs.nextInt();
        int[] H = new int[M];
        for (int i = 0; i < M; i++) H[i] = fs.nextInt();

        Arrays.sort(V);
        Arrays.sort(H);

        int ethanVcount = lowerBound(V, Xe);
        int ethanHcount = lowerBound(H, Ye);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < C; i++) {
            int x = fs.nextInt();
            int y = fs.nextInt();
            int chipVcount = lowerBound(V, x);
            int chipHcount = lowerBound(H, y);
            if (chipVcount == ethanVcount && chipHcount == ethanHcount) {
                sb.append('Y').append('\n');
            } else {
                sb.append('N').append('\n');
            }
        }
        System.out.print(sb.toString());
    }

    // Returns number of elements in arr strictly less than key
    private static int lowerBound(int[] arr, int key) {
        int idx = Arrays.binarySearch(arr, key);
        if (idx >= 0) {
            // key is present; we need first index of key (but problem guarantees no laser equals Ethan/chip coords)
            // still, handle generally by moving left to first occurrence
            while (idx > 0 && arr[idx - 1] == key) idx--;
            return idx;
        } else {
            return -idx - 1;
        }
    }

    // Fast scanner for integers
    private static class FastScanner {
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