package Data_Structure_Tests;

import java.io.*;
import java.util.StringTokenizer;

public class lazy {
    static int N, Q;
    static long[] arr;

    // Segment Tree Node class
    static class SegmentTreeNode {
        int left, right;
        SegmentTreeNode leftChild, rightChild;
        long min;       // Minimum value in the segment
        long add;       // Pending addition to be applied
        long set;       // Pending assignment value
        boolean hasSet; // Indicates if there is a pending assignment

        // Constructor to build the segment tree
        public SegmentTreeNode(int l, int r) {
            this.left = l;
            this.right = r;
            this.add = 0;
            this.set = 0;
            this.hasSet = false;

            if (l == r) {
                // Leaf node: Initialize with the array value
                min = arr[l];
            } else {
                // Internal node: Recursively build left and right children
                int m = (l + r) / 2;
                leftChild = new SegmentTreeNode(l, m);
                rightChild = new SegmentTreeNode(m + 1, r);
                min = Math.min(leftChild.min, rightChild.min);
            }
        }

        // Method to push down pending updates to child nodes
        void pushDown() {
            if (leftChild != null) {
                if (hasSet) {
                    // Assign operation overrides everything
                    leftChild.hasSet = true;
                    leftChild.set = set;
                    leftChild.add = 0;
                    leftChild.min = set;

                    rightChild.hasSet = true;
                    rightChild.set = set;
                    rightChild.add = 0;
                    rightChild.min = set;
                }
                if (add != 0) {
                    // Apply addition to children
                    if (leftChild.hasSet) {
                        leftChild.set += add;
                        leftChild.min = leftChild.set;
                    } else {
                        leftChild.add += add;
                        leftChild.min += add;
                    }

                    if (rightChild.hasSet) {
                        rightChild.set += add;
                        rightChild.min = rightChild.set;
                    } else {
                        rightChild.add += add;
                        rightChild.min += add;
                    }
                }
            }
            // Clear pending updates
            hasSet = false;
            set = 0;
            add = 0;
        }

        // Method to perform range addition
        void rangeAdd(int l, int r, long val) {
            if (r < left || right < l) {
                // No overlap
                return;
            }
            if (l <= left && right <= r) {
                // Total overlap
                if (hasSet) {
                    set += val;
                    min += val;
                } else {
                    add += val;
                    min += val;
                }
                return;
            }
            // Partial overlap
            pushDown();
            leftChild.rangeAdd(l, r, val);
            rightChild.rangeAdd(l, r, val);
            min = Math.min(leftChild.min, rightChild.min);
        }

        // Method to perform range assignment
        void rangeSet(int l, int r, long val) {
            if (r < left || right < l) {
                // No overlap
                return;
            }
            if (l <= left && right <= r) {
                // Total overlap
                hasSet = true;
                set = val;
                add = 0;
                min = val;
                return;
            }
            // Partial overlap
            pushDown();
            leftChild.rangeSet(l, r, val);
            rightChild.rangeSet(l, r, val);
            min = Math.min(leftChild.min, rightChild.min);
        }

        // Method to query the minimum value in a range
        long rangeMin(int l, int r) {
            if (r < left || right < l) {
                // No overlap
                return Long.MAX_VALUE;
            }
            if (l <= left && right <= r) {
                // Total overlap
                return min;
            }
            // Partial overlap
            pushDown();
            return Math.min(leftChild.rangeMin(l, r), rightChild.rangeMin(l, r));
        }
    }

    public static void main(String[] args) throws IOException {
        // Use BufferedReader and BufferedWriter for fast I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // Read N and Q
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        arr = new long[N + 1]; // 1-based indexing

        // Read the initial array
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        // Build the segment tree
        SegmentTreeNode root = new SegmentTreeNode(1, N);

        // Process Q queries
        for (int q = 0; q < Q; q++) {
            String line = br.readLine();
            st = new StringTokenizer(line);
            int op = Integer.parseInt(st.nextToken());
            if (op == 1) {
                // Range addition
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                long v = Long.parseLong(st.nextToken());
                root.rangeAdd(l, r, v);
            } else if (op == 2) {
                // Range assignment
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                long v = Long.parseLong(st.nextToken());
                root.rangeSet(l, r, v);
            } else if (op == 3) {
                // Range minimum query
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                long res = root.rangeMin(l, r);
                bw.write(String.valueOf(res));
                bw.newLine();
            }
        }
        // Flush the output buffer
        bw.flush();
    }
}
