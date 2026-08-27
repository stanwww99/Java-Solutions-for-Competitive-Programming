package CIW;
import java.io.*;
import java.util.*;

public class ciw26p2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = n * m; // Total elements

        int[] grid = new int[s];
        int index = 0;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                grid[index++] = Integer.parseInt(st.nextToken());
            }
        }

        int q = Integer.parseInt(br.readLine().trim());
        long multiplier = 1;
        int currentR = n;

        // Process queries in O(Q)
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                if (s > 1) {
                    multiplier = (multiplier * currentR) % (s - 1);
                }
                currentR = s / currentR;
            } else {
                int k = Integer.parseInt(st.nextToken());
                currentR = k;
            }
        }

        int currentC = s / currentR;

        // Output dimensions
        StringBuilder sb = new StringBuilder();
        sb.append(currentR).append(" ").append(currentC).append("\n");

        // Reconstruct final array in O(NM)
        int[] ans = new int[s];
        if (s == 1) {
            ans[0] = grid[0];
        } else {
            for (int i = 0; i < s - 1; i++) {
                int finalIndex = (int) ((i * multiplier) % (s - 1));
                ans[finalIndex] = grid[i];
            }
            // The last element always stays at the end
            ans[s - 1] = grid[s - 1];
        }

        // Append grid output efficiently
        index = 0;
        for (int r = 0; r < currentR; r++) {
            for (int c = 0; c < currentC; c++) {
                sb.append(ans[index++]);
                if (c < currentC - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }
}
