package Data_Structure_Tests;

import java.io.*;
import java.util.*;

/**
 * DMOJ ds4 Binary Search Tree Test solution using a Treap (order-statistic tree).
 * Supports:
 *  - I v : insert v
 *  - R v : remove one occurrence of v (if exists)
 *  - S v : output v-th smallest element (1-based)
 *  - L v : output index (1-based) of first occurrence of v in sorted array, or -1 if absent
 *
 * Handles encrypted v via v = x ^ lastAns where lastAns is last S or L answer (0 initially).
 */
public class ds4 {
    static final Random RNG = new Random();

    static class Node {
        int key;
        int priority;
        int count;      // multiplicity of key
        int size;       // size of subtree (sum of counts)
        Node left, right;

        Node(int key) {
            this.key = key;
            this.priority = RNG.nextInt();
            this.count = 1;
            this.size = 1;
        }

        void recalc() {
            size = count + sizeOf(left) + sizeOf(right);
        }
    }

    static int sizeOf(Node n) {
        return n == null ? 0 : n.size;
    }

    // rotate right
    static Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        y.recalc();
        x.recalc();
        return x;
    }

    // rotate left
    static Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.recalc();
        y.recalc();
        return y;
    }

    // insert key into treap rooted at node, return new root
    static Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key == node.key) {
            node.count++;
        } else if (key < node.key) {
            node.left = insert(node.left, key);
            if (node.left.priority > node.priority) node = rotateRight(node);
        } else {
            node.right = insert(node.right, key);
            if (node.right.priority > node.priority) node = rotateLeft(node);
        }
        node.recalc();
        return node;
    }

    // remove one occurrence of key from treap rooted at node, return new root
    static Node remove(Node node, int key) {
        if (node == null) return null;
        if (key == node.key) {
            if (node.count > 1) {
                node.count--;
            } else {
                // remove node by merging children
                if (node.left == null && node.right == null) {
                    return null;
                } else if (node.left == null) {
                    node = rotateLeft(node);
                    node.left = remove(node.left, key);
                } else if (node.right == null) {
                    node = rotateRight(node);
                    node.right = remove(node.right, key);
                } else {
                    if (node.left.priority > node.right.priority) {
                        node = rotateRight(node);
                        node.right = remove(node.right, key);
                    } else {
                        node = rotateLeft(node);
                        node.left = remove(node.left, key);
                    }
                }
            }
        } else if (key < node.key) {
            node.left = remove(node.left, key);
        } else {
            node.right = remove(node.right, key);
        }
        if (node != null) node.recalc();
        return node;
    }

    // find k-th smallest (1-based). Assumes 1 <= k <= sizeOf(root)
    static int kth(Node node, int k) {
        if (node == null) throw new IllegalArgumentException("k out of bounds");
        int leftSize = sizeOf(node.left);
        if (k <= leftSize) return kth(node.left, k);
        if (k <= leftSize + node.count) return node.key;
        return kth(node.right, k - leftSize - node.count);
    }

    // count number of elements strictly less than key
    static int countLessThan(Node node, int key) {
        if (node == null) return 0;
        if (key <= node.key) {
            return countLessThan(node.left, key);
        } else {
            return sizeOf(node.left) + node.count + countLessThan(node.right, key);
        }
    }

    // check if key exists
    static boolean contains(Node node, int key) {
        while (node != null) {
            if (key == node.key) return true;
            node = key < node.key ? node.left : node.right;
        }
        return false;
    }

    // inorder traversal to append final sorted elements
    static void inorderPrint(Node node, StringBuilder sb) {
        if (node == null) return;
        inorderPrint(node.left, sb);
        for (int i = 0; i < node.count; i++) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(node.key);
        }
        inorderPrint(node.right, sb);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();

        Node root = null;
        for (int i = 0; i < N; i++) {
            int v = fs.nextInt();
            root = insert(root, v);
        }

        long lastAns = 0;
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < M; i++) {
            char op = fs.next().charAt(0);
            long x = fs.nextLong();
            int v = (int)(x ^ lastAns);
            if (op == 'I') {
                root = insert(root, v);
            } else if (op == 'R') {
                if (contains(root, v)) root = remove(root, v);
            } else if (op == 'S') {
                // v is guaranteed to be <= size
                int ans = kth(root, v);
                out.append(ans).append('\n');
                lastAns = ans;
            } else if (op == 'L') {
                if (!contains(root, v)) {
                    out.append(-1).append('\n');
                    lastAns = 0;
                } else {
                    int idx = countLessThan(root, v) + 1;
                    out.append(idx).append('\n');
                    lastAns = idx;
                }
            }
        }

        // final array in non-decreasing order on single line
        StringBuilder finalLine = new StringBuilder();
        inorderPrint(root, finalLine);
        if (finalLine.length() > 0) {
            out.append(finalLine.toString()).append('\n');
        } else {
            out.append('\n');
        }

        System.out.print(out.toString());
    }

    // Fast scanner using buffered input
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
            while ((c = read()) <= ' ') {
                if (c == -1) return null;
            }
            do {
                sb.append((char)c);
                c = read();
            } while (c > ' ');
            return sb.toString();
        }

        int nextInt() throws IOException {
            int sign = 1, val = 0;
            int c = read();
            while (c <= ' ') c = read();
            if (c == '-') { sign = -1; c = read(); }
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        long nextLong() throws IOException {
            int sign = 1;
            long val = 0;
            int c = read();
            while (c <= ' ') c = read();
            if (c == '-') { sign = -1; c = read(); }
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}
