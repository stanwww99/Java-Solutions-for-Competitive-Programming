package China_NOI;
import java.io.*;

public class noi01p1 {
    static int[] parent;
    static int[] relation; // relation[x] to parent: 0 same, 1 x eats parent, 2 parent eats x
    static int[] size;

    static int find(int x) {
        if (parent[x] == x) return x;
        int p = parent[x];
        int root = find(p);
        relation[x] = (relation[x] + relation[p]) % 3;
        parent[x] = root;
        return root;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();
        int k = fs.nextInt();

        parent = new int[n + 1];
        relation = new int[n + 1];
        size = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int lies = 0;

        for (int i = 0; i < k; i++) {
            int d = fs.nextInt();
            int x = fs.nextInt();
            int y = fs.nextInt();

            if (x < 1 || x > n || y < 1 || y > n) {
                lies++;
                continue;
            }

            if (d == 2 && x == y) {
                lies++;
                continue;
            }

            int rootX = find(x);
            int rootY = find(y);

            // expected relation from x to y
            // d = 1 means same species -> 0
            // d = 2 means x eats y -> 1
            int expected = (d == 1) ? 0 : 1;

            if (rootX == rootY) {
                int actual = (relation[x] - relation[y] + 3) % 3;
                if (actual != expected) {
                    lies++;
                }
            } else {
                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    relation[rootX] = (expected - relation[x] + relation[y] + 3) % 3;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    relation[rootY] = (relation[x] - relation[y] - expected + 3) % 3;
                    size[rootX] += size[rootY];
                }
            }
        }

        System.out.println(lies);
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

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
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + c - '0';
                c = read();
            }

            return val * sign;
        }
    }
}
