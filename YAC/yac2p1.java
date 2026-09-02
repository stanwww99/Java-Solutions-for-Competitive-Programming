package YAC;

import java.io.*;
import java.util.*;

public class yac2p1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long A = Integer.parseInt(st.nextToken());
            long B = Integer.parseInt(st.nextToken());
            long C = Integer.parseInt(st.nextToken());
            long D = Integer.parseInt(st.nextToken());
            System.out.println(((B - A)*(D - C) > A*C)? "YES": "NO");
        }

    }
}
