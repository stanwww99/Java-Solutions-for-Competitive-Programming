package EGOI;
import java.io.*;
import java.util.*;

public class egoi22p1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        for (int _case = 0; _case < t; _case++) {
            int n = Integer.parseInt(br.readLine());
            long[] a = new long[n];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                long f = Long.parseLong(st.nextToken());
                a[i] = f - 1;   // final operation consumes one copy of each 0..n-1
            }

            long ans = 1;  // final operation that inserts n

            for (int i = n - 1; i >= 0; i--) {
                if (a[i] < 0) {
                    long need = -a[i];
                    ans += need;
                    for (int j = 0; j < i; j++) {
                        a[j] += a[i];   // a[i] is negative → subtract |a[i]| from all lower indices
                    }
                }
            }

            System.out.println(ans);
        }
    }
}