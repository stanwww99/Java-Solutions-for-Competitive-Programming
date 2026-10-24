package ECOO;

import java.io.*;
import java.util.*;

public class ecoo17r2p3 {
    static class Rest {
        int idx;
        long B;
        int A;
        Rest(int idx, long B, int A) {
            this.idx = idx;
            this.B = B;
            this.A = A;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int tc = 0; tc < 10; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            long K = Long.parseLong(st.nextToken());

            int[] R = new int[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                R[i] = Integer.parseInt(st.nextToken());
            }

            Rest[] arr = new Rest[N];
            long maxB = Long.MIN_VALUE, minB = Long.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                long Bi = R[i] / (long) M;
                int Ai = R[i] % M;
                arr[i] = new Rest(i, Bi, Ai);
                maxB = Math.max(maxB, Bi);
                minB = Math.min(minB, Bi);
            }

            // Sort by B descending
            Integer[] idxByB = new Integer[N];
            for (int i = 0; i < N; i++) idxByB[i] = i;
            Arrays.sort(idxByB, (i, j) -> {
                if (arr[i].B != arr[j].B) return Long.compare(arr[j].B, arr[i].B);
                if (arr[i].A != arr[j].A) return Integer.compare(arr[j].A, arr[i].A);
                return Integer.compare(i, j);
            });

            // Sort by A descending, index ascending
            Integer[] orderByA = new Integer[N];
            for (int i = 0; i < N; i++) orderByA[i] = i;
            Arrays.sort(orderByA, (i, j) -> {
                if (arr[i].A != arr[j].A) return Integer.compare(arr[j].A, arr[i].A);
                return Integer.compare(i, j);
            });

            // Build distinct B levels
            ArrayList<Long> v = new ArrayList<>();
            ArrayList<Integer> prefixSize = new ArrayList<>();
            long prevB = Long.MIN_VALUE;
            int count = 0;
            for (int pos = 0; pos < N; pos++) {
                long Bi = arr[idxByB[pos]].B;
                if (Bi != prevB) {
                    v.add(Bi);
                    count++;
                    prefixSize.add(count);
                    prevB = Bi;
                } else {
                    count++;
                    prefixSize.set(prefixSize.size() - 1, count);
                }
            }

            long remainingK = K;
            boolean answered = false;
            for (int j = 0; j < v.size() - 1 && !answered; j++) {
                long top = v.get(j);
                long next = v.get(j + 1);
                long deltaLevels = top - next;
                int c = prefixSize.get(j);

                long total = deltaLevels * (long) c;
                if (remainingK > total) {
                    remainingK -= total;
                } else {
                    int r = (int) ((remainingK - 1) % c);
                    boolean[] inPrefix = new boolean[N];
                    for (int t = 0; t < c; t++) inPrefix[idxByB[t]] = true;

                    int pickedIdx = -1, seen = 0;
                    for (int t = 0; t < N; t++) {
                        int i = orderByA[t];
                        if (inPrefix[i]) {
                            if (seen == r) { pickedIdx = i; break; }
                            seen++;
                        }
                    }
                    System.out.println(pickedIdx + 1);
                    answered = true;
                }
            }

            if (!answered) {
                int r = (int) ((remainingK - 1) % N);
                int pickedIdx = orderByA[r];
                System.out.println(pickedIdx + 1);
            }
        }

    }
}