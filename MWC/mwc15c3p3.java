package MWC;

import java.io.*;
import java.util.*;

public class mwc15c3p3 {
    static int N, Q;
    static char[][] grid;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        grid = new char[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = st.nextToken().charAt(0);
            }
        }
        for (int k = 0; k < Q; k++) {
            String word = br.readLine();
            boolean found = false;
            for (int i = 0; i < N && !found; i++) {
                for (int j = 0; j < N && !found; j++) {
                    if (grid[i][j] == word.charAt(0)) {
                        boolean[][] visited = new boolean[N][N];
                        if (dfs(i, j, word, 0, visited)) {
                            found = true;
                        }
                    }
                }
            }
            if(found)
                System.out.println("good puzzle!");
            else
                System.out.println("bad puzzle!");
        }
    }

    static boolean dfs(int i, int j, String word, int index, boolean[][] visited) {
        if (index == word.length())
            return true;
        if (i < 0 || i >= N || j < 0 || j >= N || visited[i][j])
            return false;
        if (grid[i][j] != word.charAt(index))
            return false;
        if (index == word.length() - 1)
            return true;
        visited[i][j] = true;
        for (int dir = 0; dir < 8; dir++) {
            int newRow = i + dx[dir];
            int newCol = j + dy[dir];
            if (dfs(newRow, newCol, word, index + 1, visited))
                return true;
        }
        visited[i][j] = false;
        return false;
    }
}
