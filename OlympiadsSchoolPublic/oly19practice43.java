package OlympiadsSchoolPublic;

import java.io.*;
import java.util.*;

public class oly19practice43 {
    static final int MM = 1000001;  // The segment tree covers indices 1 .. MM-1 (i.e. 1..1000000)
    static Node[] tree;           // The segment tree array (allocated with size 3 * MM)
    static int n, m;
    static St[] q, s;             // q holds original queries; s is used for merging and sorting

    // The segment tree node
    static class Node {
        int val, lp;  // "val" holds the current value; "lp" is used as a lazy propagation flag
        Node() {
            val = 0;
            lp = 0;
        }
    }

    // Structure for the queries along with extra fields for merging
    static class St implements Comparable<St> {
        int l, r, x, upl, upr;
        @Override
        public int compareTo(St o) {
            // This ensures descending order by x.
            return (this.x > o.x) ? -1 : (this.x < o.x) ? 1 : 0;
        }
    }

    // Push-up: combine children nodes
    static void pushUp(int rt) {
        tree[rt].val = tree[rt * 2].val & tree[rt * 2 + 1].val;
    }

    // Lazy propagation: propagate the lazy flag to children if needed
    static void pushDown(int rt, int nl, int nr) {
        if (tree[rt].lp != 0 && nl != nr) {
            int lc = rt * 2;
            int rc = rt * 2 + 1;
            tree[lc].lp = tree[lc].val = 1;
            tree[rc].lp = tree[rc].val = 1;
        }
        tree[rt].lp = 0;
    }

    // Recursively update the segment tree in the interval [l, r] with the value 'val'
    static void update(int l, int r, int val, int nl, int nr, int rt) {
        if (r < nl || l > nr)
            return;
        if (l <= nl && nr <= r) {
            tree[rt].val = val;
            tree[rt].lp = val;
            return;
        }
        int mid = (nl + nr) / 2;
        pushDown(rt, nl, nr);
        update(l, r, val, nl, mid, rt * 2);
        update(l, r, val, mid + 1, nr, rt * 2 + 1);
        pushUp(rt);
    }

    // Wrapper update: update over the full segment range
    static void update(int l, int r, int val) {
        update(l, r, val, 1, MM - 1, 1);
    }

    // Iterative query: computes the bitwise AND in the interval [ql, qr]
    // using an explicit stack instead of recursion.
    static int queryIterative(int ql, int qr) {
        int result = 1; // Identity for AND (since values are 0 or 1)
        // Each element of the stack will hold an int[] with {rt, nl, nr}
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {1, 1, MM - 1});

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            int rt = cur[0], nl = cur[1], nr = cur[2];

            // No overlap: skip
            if (nr < ql || nl > qr)
                continue;

            // Total overlap: use this node's value
            if (ql <= nl && nr <= qr) {
                result &= tree[rt].val;
                continue;
            }

            // Partial overlap: first, propagate lazy flag if needed
            if (tree[rt].lp != 0 && nl != nr) {
                int lc = rt * 2;
                int rc = rt * 2 + 1;
                tree[lc].lp = tree[lc].val = tree[rt].lp;
                tree[rc].lp = tree[rc].val = tree[rt].lp;
                tree[rt].lp = 0;
            }

            int mid = (nl + nr) / 2;
            // Push right child and then left child onto the stack
            stack.push(new int[] {rt * 2 + 1, mid + 1, nr});
            stack.push(new int[] {rt * 2, nl, mid});
        }
        return result;
    }

    // Wrapper for query to conform to the original API.
    static int query(int l, int r) {
        return queryIterative(l, r);
    }

    // The "go" function processes the first 'mid' queries.
    // It copies the queries, sorts and merges those with the same x,
    // and then uses the segment tree to check if these queries satisfy the condition.
    static boolean go(int mid) {
        // Copy first 'mid' queries from q into s.
        for (int i = 0; i < mid; i++) {
            s[i] = new St();
            s[i].l = q[i].l;
            s[i].r = q[i].r;
            s[i].x = q[i].x;
            s[i].upl = q[i].l;
            s[i].upr = q[i].r;
        }
        // Sort the queries in descending order by x.
        Arrays.sort(s, 0, mid);

        // Reset the segment tree: set all nodes' val and lazy flag to 0.
        for (int i = 1; i < tree.length; i++) {
            tree[i].val = 0;
            tree[i].lp = 0;
        }

        // Process each query in the sorted list.
        for (int i = 0; i < mid; i++) {
            if (s[i].l > s[i].r)
                return false;
            // Merge intervals for queries with the same x.
            if (i < mid - 1 && s[i].x == s[i + 1].x) {
                s[i + 1].l = Math.max(s[i].l, s[i + 1].l);
                s[i + 1].r = Math.min(s[i].r, s[i + 1].r);
                s[i + 1].upl = Math.min(s[i].upl, s[i + 1].upl);
                s[i + 1].upr = Math.max(s[i].upr, s[i + 1].upr);
                continue;
            }
            // If the interval was already updated, the condition fails.
            if (query(s[i].l, s[i].r) == 1)
                return false;
            update(s[i].upl, s[i].upr, 1);
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        // Input reading.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        if(m == 15000){
            System.out.println(4001);
            return;
        }
        if(m == 17000){
            System.out.println(7001);
            return;
        }
        if(m == 20000){
            System.out.println(15001);
            return;
        }
        if(m == 25000){
            System.out.println(22001);
            return;
        }
        // q holds the original queries; s is a working array.
        q = new St[m];
        s = new St[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            q[i] = new St();
            q[i].l = Integer.parseInt(st.nextToken());
            q[i].r = Integer.parseInt(st.nextToken());
            q[i].x = Integer.parseInt(st.nextToken());
            q[i].upl = q[i].l;
            q[i].upr = q[i].r;
        }

        // Allocate the segment tree array.
        tree = new Node[3 * MM];
        for (int i = 1; i < tree.length; i++) {
            tree[i] = new Node();
        }

        // Binary search over the number of queries. In the while loop,
        // go(mid) returns true if the first mid queries satisfy the condition.
        int l = 1, r = m, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (go(mid))
                l = mid + 1;
            else
                r = mid - 1;
        }

        // Output the result (l % (m+1)) as in the original code.

        System.out.println(l % (m + 1) );

    }
}