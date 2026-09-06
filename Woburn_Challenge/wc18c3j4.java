package Woburn_Challenge;
import java.io.*;
import java.util.*;

public class wc18c3j4 {
    static class Pokemon {
        int pos;
        int lvl;
        int gain;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        long L = Long.parseLong(st.nextToken());

        int[][] pok = new int[N][3];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            pok[i][0] = Integer.parseInt(st.nextToken());
            pok[i][1] = Integer.parseInt(st.nextToken());
            pok[i][2] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(pok, Comparator.comparingInt(p -> p[0]));
        int r = 0;
        while (r < N && pok[r][0] <= S) {
            r++;
        }
        int l = r - 1;
        while (true) {
            boolean canLeft  = (l >= 0   && pok[l][1] <= L);
            boolean canRight = (r < N    && pok[r][1] <= L);

            if (!canLeft && !canRight) {
                break;
            }

            if (canLeft && canRight) {
                if (pok[l][2] > pok[r][2]) {
                    L	 += pok[l][2];
                    l--;
                } else {
                    L+= pok[r][2];
                    r++;
                }
            } else if (canLeft) {
                L += pok[l][2];
                l--;
            } else {
                L += pok[r][2];
                r++;
            }
        }
        System.out.println(L);
    }
}