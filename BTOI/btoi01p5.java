package Baltic_OI;

import java.io.*;
import java.util.*;

public class btoi01p5 {
    static class Event implements Comparable<Event> {
        int x;
        int y1, y2;
        int type; // +1 for entering, -1 for leaving
        Event(int x, int y1, int y2, int type) {
            this.x = x; this.y1 = y1; this.y2 = y2; this.type = type;
        }
        public int compareTo(Event o) {
            return Integer.compare(this.x, o.x);
        }
    }

    static class SegmentTree {
        int n; // number of elementary intervals (ys.length - 1)
        int[] cover;
        long[] len;
        int[] ys; // reference to coordinate array

        SegmentTree(int n, int[] ys) {
            this.n = n;
            this.ys = ys;
            cover = new int[n * 4];
            len = new long[n * 4];
        }

        void update(int node, int l, int r, int ql, int qr, int val) {
            if (ql > r || qr < l) return;
            if (ql <= l && r <= qr) {
                cover[node] += val;
                pushUp(node, l, r);
                return;
            }
            int mid = (l + r) >> 1;
            update(node<<1, l, mid, ql, qr, val);
            update(node<<1|1, mid+1, r, ql, qr, val);
            pushUp(node, l, r);
        }

        void pushUp(int node, int l, int r) {
            if (cover[node] > 0) {
                // fully covered
                len[node] = ys[r+1] - ys[l];
            } else {
                if (l == r) {
                    len[node] = 0;
                } else {
                    len[node] = len[node<<1] + len[node<<1|1];
                }
            }
        }

        long totalCoveredLength() {
            return len[1];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        Event[] events = new Event[N * 2];
        int[] ysAll = new int[N * 2];
        int idx = 0, yidx = 0;
        for (int i = 0; i < N; i++) {
            int x1 = fs.nextInt();
            int y1 = fs.nextInt();
            int x2 = fs.nextInt();
            int y2 = fs.nextInt();
            events[idx++] = new Event(x1, y1, y2, +1);
            events[idx++] = new Event(x2, y1, y2, -1);
            ysAll[yidx++] = y1;
            ysAll[yidx++] = y2;
        }
        if (N == 0) {
            System.out.println(0);
            return;
        }

        // coordinate compress y
        Arrays.sort(ysAll, 0, yidx);
        int m = 0;
        for (int i = 0; i < yidx; i++) {
            if (i == 0 || ysAll[i] != ysAll[i-1]) {
                ysAll[m++] = ysAll[i];
            }
        }
        int[] ys = Arrays.copyOf(ysAll, m);

        // sort events by x
        Arrays.sort(events, 0, idx);

        // build mapping from y value to index
        HashMap<Integer,Integer> yToIndex = new HashMap<>();
        for (int i = 0; i < ys.length; i++) yToIndex.put(ys[i], i);

        // segment tree works on elementary intervals between ys[i] and ys[i+1]
        if (ys.length <= 1) {
            // all rectangles have zero height
            System.out.println(0);
            return;
        }
        SegmentTree st = new SegmentTree(ys.length - 1, ys);

        long area = 0L;
        int i = 0;
        while (i < idx) {
            int curX = events[i].x;
            // process all events at curX: first apply updates at this x
            int j = i;
            while (j < idx && events[j].x == curX) {
                int y1 = events[j].y1;
                int y2 = events[j].y2;
                int l = yToIndex.get(y1);
                int r = yToIndex.get(y2) - 1; // cover intervals [l, r] in elementary intervals
                if (l <= r) st.update(1, 0, st.n - 1, l, r, events[j].type);
                j++;
            }
            // next x (if any)
            if (j < idx) {
                int nextX = events[j].x;
                long coveredY = st.totalCoveredLength();
                long dx = (long)nextX - curX;
                area += coveredY * dx;
            }
            i = j;
        }

        System.out.println(area);
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
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}