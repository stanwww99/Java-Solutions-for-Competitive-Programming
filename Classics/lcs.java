package classics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class lcs {
    // Computes LCS length using O(min(n,m)) space for integer sequences
    public static int lcsLength(int[] a, int[] b) {
        // ensure b is the shorter array to minimize space
        if (a.length < b.length) {
            int[] tmp = a; a = b; b = tmp;
        }
        int n = a.length;
        int m = b.length;
        int[] prev = new int[m + 1];
        int[] cur  = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            int ai = a[i - 1];
            for (int j = 1; j <= m; j++) {
                if (ai == b[j - 1]) {
                    cur[j] = prev[j - 1] + 1;
                } else {
                    cur[j] = Math.max(prev[j], cur[j - 1]);
                }
            }
            // swap prev and cur
            int[] t = prev;
            prev = cur;
            cur = t;
        }
        return prev[m];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        int[] b = new int[m];

        // read array a
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
        }

        // read array b
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            b[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(lcsLength(a, b));
    }
}
