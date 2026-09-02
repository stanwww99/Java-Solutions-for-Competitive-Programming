package YAC;

import java.io.*;
import java.util.StringTokenizer;

public class yac3p1 {
    static class Move {
        boolean known;
        int u, v;
    }
    static int[] getDummy(int stone, int N) {
        if (stone != 1 && stone != 2) {
            return new int[]{1, 2};
        } else if (stone == 1) {
            return new int[]{2, 3};
        } else { // stone == 2
            return new int[]{1, 3};
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input/output.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        Move[] moves = new Move[M];
        int lastUnknown = -1;
        for (int i = 0; i < M; i++) {
            String line = br.readLine().trim();
            st = new StringTokenizer(line);
            moves[i] = new Move();
            if (st.countTokens() == 1) {
                st.nextToken();
                moves[i].known = false;
                lastUnknown = i;
            } else {
                moves[i].known = true;
                moves[i].u = Integer.parseInt(st.nextToken());
                moves[i].v = Integer.parseInt(st.nextToken());
            }
        }
        int[][] result = new int[M][2];
        int stone = start;
        for (int i = 0; i < lastUnknown; i++) {
            if (moves[i].known) {
                result[i][0] = moves[i].u;
                result[i][1] = moves[i].v;
                if (stone == moves[i].u) {
                    stone = moves[i].v;
                } else if (stone == moves[i].v) {
                    stone = moves[i].u;
                }
            } else {
                int[] dummy = getDummy(stone, N);
                result[i][0] = dummy[0];
                result[i][1] = dummy[1];
            }
        }
        int s_pre = stone;
        int t = s_pre;
        for (int i = lastUnknown + 1; i < M; i++) {
            if (t == moves[i].u) {
                t = moves[i].v;
            } else if (t == moves[i].v) {
                t = moves[i].u;
            }
        }
        int t_pre = t;
        if (t_pre == end) {
            int[] dummy = getDummy(s_pre, N);
            result[lastUnknown][0] = dummy[0];
            result[lastUnknown][1] = dummy[1];
        } else {
            int x = end;
            for (int i = M - 1; i >= lastUnknown + 1; i--) {
                if (x == moves[i].u) {
                    x = moves[i].v;
                } else if (x == moves[i].v) {
                    x = moves[i].u;
                }
            }
            result[lastUnknown][0] = s_pre;
            result[lastUnknown][1] = x;
        }
        for (int i = lastUnknown + 1; i < M; i++) {
            result[i][0] = moves[i].u;
            result[i][1] = moves[i].v;
        }
        for (int i = 0; i < M; i++) {
            System.out.println(result[i][0] + " " + result[i][1]);
        }
    }
}