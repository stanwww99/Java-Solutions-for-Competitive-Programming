package TLE;

import java.io.*;
import java.util.*;
class Pair{
    int x;
    int d;
    Pair(int x, int d){
        this.x = x;
        this.d = d;
    }
}
public class tle16c6s3 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Pair[] arr = new Pair[N];
        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(arr, (x, y) -> Integer.compare(x.x, y.x));
        int idx = -1;
        for(int i = 0; i < N; i++) {
            if(arr[i].x == 0) {
                idx = i;
                break;
            }
        }
        long[][] dpLeft = new long[N][N];
        long[][] dpRight = new long[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dpLeft[i], -1);
            Arrays.fill(dpRight[i], -1);
        }
        dpLeft[idx][idx] = arr[idx].d;
        dpRight[idx][idx] = arr[idx].d;

        for(int len = 1; len < N; len++) {
            for (int i = 0; i < N - len; i++) {
                int j = i + len;
                long dist = Math.abs(arr[j- 1].x - arr[j].x);
                if(dpRight[i][j- 1 ] >= dist) {
                    dpRight[i][j] = dpRight[i][j - 1] - dist + arr[j].d;
                }
                dist = Math.abs(arr[i] .x - arr[j].x);
                if(dpLeft[i][j- 1 ] >= dist) {
                    dpRight[i][j] = Math.max(dpRight[i][j], dpLeft[i][j - 1] - dist + arr[j].d);
                }
                dist = Math.abs(arr[i].x - arr[i + 1].x);
                if(dpLeft[i + 1][j] >= dist) {
                    dpLeft[i][j] = dpLeft[i + 1][j]  - dist + arr[i].d;
                }
                dist = Math.abs(arr[i] .x - arr[j].x);
                if(dpRight[i + 1][j] >= dist) {
                    dpLeft[i][j] = Math.max(dpLeft[i][j], dpRight[i + 1][j] - dist + arr[i].d);
                }
            }
        }
        long[] prefix = new long[N + 1];
        for (int i = 0; i < N; i++) {
            prefix[i + 1] = prefix[i] + arr[i].d;
        }
        long ans = 0;
        for(int i = 0; i < idx + 1; i++) {
            for(int j = idx; j < N; j++) {
                if(dpLeft[i][j] != - 1 || dpRight[i][j] != -1) {
                    ans = Math.max(ans, prefix[j + 1] - prefix[i]);
                }
            }
        }

        System.out.println(ans);


    }

}
