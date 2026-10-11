package AAC;
import java.io.*;
import java.util.*;

public class aac6p2 {
    public static void main(String[] args) throws IOException {
        // use BufferedReader for fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        // M will be the minimum number of distinct bands required.
        int M = 1;
        // we will use a diff array (of size N+1) to mark forced adjacent pairs.
        int[] diff = new int[N+1];   // indices 0..N; we use 0-indexed positions for adjacent pairs:
        // for a friend query [L,R] (1-indexed), the forced adjacent pairs are at indices L-1 .. R-2.

        // Process each friend query; note that if R == L then there is no forced pair.
        for(int i = 0; i < Q; i++){
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            // update M = max(M, R - L + 1)
            M = Math.max(M, R - L + 1);
            // if query length >= 2, add 1 to diff at index L-1 and subtract 1 at index R-1.
            if(R > L) {
                diff[L-1]++;    // 0-indexed: this marks the forced pairs starting at show L.
                diff[R-1]--;    // subtract at index R-1 so that indices R-1 and onwards are not affected.
            }
        }

        // Build an array "forced" for adjacent positions.
        // forced[i] == true means that shows i and i+1 (1-indexed: positions i+1 and i+2) are forced to be distinct.
        boolean[] forced = new boolean[N-1];
        int sum = 0;
        for (int i = 0; i < N-1; i++){
            sum += diff[i];
            forced[i] = (sum > 0);
        }

        // Build the band assignment:
        // a[0] corresponds to show 1, a[1] to show 2, ...; using values from 1 to M.
        int[] ans = new int[N];
        ans[0] = 1;
        for (int i = 1; i < N; i++){
            if(forced[i-1]){
                // forced to change from previous band
                ans[i] = (ans[i-1] % M) + 1;
            } else {
                // allowed to stay the same, so we keep the same band
                ans[i] = ans[i-1];
            }
        }

        // Now output the results.
        // The first line is M (the number of bands hired)
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        pw.println(M);
        // The second line: the schedule for N shows (band id for each)
        for (int i = 0; i < N; i++){
            pw.print(ans[i]);
            if(i < N - 1) {
                pw.print(" ");
            }
        }
        pw.println();
        pw.flush();
    }
}
