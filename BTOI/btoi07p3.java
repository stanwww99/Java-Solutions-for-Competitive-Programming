package Baltic_OI;

import java.io.*;

public class btoi07p3 {
    static final class FastScanner {
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
    static class ArrayDequeIdx {
        final int[] data;
        int head, tail, size;
        ArrayDequeIdx(int capacity) {
            data = new int[capacity];
            head = tail = size = 0;
        }
        boolean isEmpty() { return size == 0; }
        void addLast(int x) {
            data[tail++] = x;
            if (tail == data.length) tail = 0;
            size++;
        }
        int peekFirst() { return data[head]; }
        int peekLast() {
            int idx = tail - 1;
            if (idx < 0) idx += data.length;
            return data[idx];
        }
        int pollFirst() {
            int val = data[head++];
            if (head == data.length) head = 0;
            size--;
            return val;
        }
        int pollLast() {
            tail--;
            if (tail < 0) tail += data.length;
            int val = data[tail];
            size--;
            return val;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();
        int c = fs.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextInt();

        ArrayDequeIdx minQ = new ArrayDequeIdx(n + 5);
        ArrayDequeIdx maxQ = new ArrayDequeIdx(n + 5);
        boolean found = false;

        for (int i = 0; i < n; i++) {
            while (!minQ.isEmpty() && a[minQ.peekLast()] >= a[i]) minQ.pollLast();
            minQ.addLast(i);
            while (!maxQ.isEmpty() && a[maxQ.peekLast()] <= a[i]) maxQ.pollLast();
            maxQ.addLast(i);

            int start = i - m + 1;
            if (start >= 0) {
                while (!minQ.isEmpty() && minQ.peekFirst() < start) minQ.pollFirst();
                while (!maxQ.isEmpty() && maxQ.peekFirst() < start) maxQ.pollFirst();

                int curMin = a[minQ.peekFirst()];
                int curMax = a[maxQ.peekFirst()];
                if (curMax - curMin <= c) {
                    System.out.println(start + 1);
                    found = true;
                }
            }
        }

        if (!found) System.out.println("NONE");
    }
}
