package VM7WC;
import java.io.*;
import java.util.*;

public class vmss7wc16c2p3 {
    public static void main(String[] args) throws IOException {
        // setting up the buffered reader for fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] s = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }
        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] idx = new int[1000001];
        for (int i = 1;  i <= M; i++) {
            int t = Integer.parseInt(st.nextToken());
            idx[t] = i;
        }
        ArrayList<Integer> lis = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if(idx[s[i]]==0) continue;
            int pos = Collections.binarySearch(lis, idx[s[i]]);
            if (pos < 0) {
                pos = -pos - 1;
            }
            if (pos == lis.size()) {
                lis.add(idx[s[i]]);
            } else {
                lis.set(pos, idx[s[i]]);
            }
        }
        System.out.println(lis.size());
    }
}