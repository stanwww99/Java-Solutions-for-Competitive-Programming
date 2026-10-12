package Mock_CCO;
import java.io.*;
import java.util.*;

public class ncco3d2p1 {

    static class SparseTable {
        int n, K;
        int[][] stMin;
        int[][] stMax;
        int[] log2;

        SparseTable(int[] a) {
            n = a.length;
            K = 1;
            while ((1 << K) <= n) K++;

            stMin = new int[K][n];
            stMax = new int[K][n];
            log2 = new int[n + 1];

            log2[1] = 0;
            for (int i = 2; i <= n; i++) {
                log2[i] = log2[i / 2] + 1;
            }

            for (int i = 0; i < n; i++) {
                stMin[0][i] = a[i];
                stMax[0][i] = a[i];
            }

            for (int k = 1; k < K; k++) {
                int len = 1 << k;
                int half = len >> 1;
                for (int i = 0; i + len <= n; i++) {
                    stMin[k][i] = Math.min(stMin[k - 1][i], stMin[k - 1][i + half]);
                    stMax[k][i] = Math.max(stMax[k - 1][i], stMax[k - 1][i + half]);
                }
            }
        }

        int rangeMin(int l, int r) {
            int len = r - l + 1;
            int k = log2[len];
            int half = 1 << k;
            return Math.min(stMin[k][l], stMin[k][r - half + 1]);
        }

        int rangeMax(int l, int r) {
            int len = r - l + 1;
            int k = log2[len];
            int half = 1 << k;
            return Math.max(stMax[k][l], stMax[k][r - half + 1]);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(br.readLine());
        }

        SparseTable stTable = new SparseTable(a);

        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken()) - 1;
            int R = Integer.parseInt(st.nextToken()) - 1;

            int mn = stTable.rangeMin(L, R);
            int mx = stTable.rangeMax(L, R);
            out.println(mx - mn);
        }

        out.flush();
    }
}