package Woburn_Challenge;
import java.io.*;
import java.util.*;

public class wc16c2s2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] R = new int[256];
        int[] G = new int[256];
        int[] B = new int[256];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            R[Integer.parseInt(st.nextToken())]++;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            G[Integer.parseInt(st.nextToken())]++;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B[Integer.parseInt(st.nextToken())]++;
        }

        if (Q == 1) {
            // Minimize red shirts
            int[] R_gt = new int[256];
            int[] G_gt = new int[256];
            int[] B_gt = new int[256];
            int suffR = 0, suffG = 0, suffB = 0;

            for (int x = 255; x >= 0; x--) {
                R_gt[x] = suffR;
                G_gt[x] = suffG;
                B_gt[x] = suffB;
                suffR += R[x];
                suffG += G[x];
                suffB += B[x];
            }

            int minRed = 0;
            for (int x = 0; x < 256; x++) {
                int deficit = R_gt[x] - (G_gt[x] + B_gt[x]);
                if (deficit > minRed) minRed = deficit;
            }
            System.out.println(minRed);
        } else {
            // Maximize red shirts
            int[] PG = new int[256];
            int[] PB = new int[256];
            int prefG = 0, prefB = 0;

            for (int x = 0; x < 256; x++) {
                PG[x] = prefG;
                PB[x] = prefB;
                prefG += G[x];
                prefB += B[x];
            }

            int[] selected = new int[256];
            int totalSelected = 0;
            int maxVal = -1;

            for (int x = 0; x < 256; x++) {
                if (R[x] > 0) {
                    selected[x] += R[x];
                    totalSelected += R[x];
                    if (x > maxVal) maxVal = x;
                }

                int cap = Math.min(PG[x], PB[x]);

                while (totalSelected > cap && maxVal >= 0) {
                    int remove = Math.min(selected[maxVal], totalSelected - cap);
                    selected[maxVal] -= remove;
                    totalSelected -= remove;

                    while (maxVal >= 0 && selected[maxVal] == 0) {
                        maxVal--;
                    }
                }
            }

            System.out.println(totalSelected);
        }
    }
}
