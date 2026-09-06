package Woburn_Challenge;

import java.io.*;
import java.util.StringTokenizer;

public class wc18c2s2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        int[] O = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            O[i] = Integer.parseInt(st.nextToken());
        }
        if (O[1] > 0 && O[1] != 1) {
            System.out.println(-1);
            return;
        }
        int prevDay = 1;
        int prevVal = 1;
        long minWithdrawals = 0;
        long maxWithdrawals = 0;
        for (int i = 2; i <= N; i++) {
            if (O[i] > 0) {
                int Y = O[i];
                int d = i - prevDay;
                int X = prevVal;
                if (Y > d) {
                    if (Y != X + d) {
                        System.out.println(-1);
                        return;
                    }
                } else {
                    minWithdrawals += 1;
                    maxWithdrawals += (d - Y + 1);
                }
                prevDay = i;
                prevVal = Y;
            }
        }
        int tailDays = N - prevDay;
        maxWithdrawals += tailDays;
        System.out.println(minWithdrawals + " " + maxWithdrawals);
    }
}

