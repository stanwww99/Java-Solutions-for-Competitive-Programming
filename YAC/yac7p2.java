package YAC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class yac7p2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int T = Integer.parseInt(br.readLine());

        for(int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            boolean r = true;

            if (N == 1 || M == 1) {
                out.println("-1");
                continue;
            }

            if (N == M) {
                for (int i = 0; i < N; i++) {
                    if (r) {
                        r = false;
                        for (int j = 1; j < M; j++) {
                            out.print((j + (i * M)) + " ");
                        }
                        out.println((i * M) + M);
                    } else {
                        r = true;
                        for (int j = M; j > 1; j--) {
                            out.print((j + (i * M)) + " ");
                        }
                        out.println((i * M) + 1);
                    }
                }
            } else {
                for (int i = 0; i < N; i++) {
                    if (r) {
                        r = false;
                        for (int j = 2; j <= M; j++) {
                            out.print((j + (i * M)) + " ");
                        }
                        if ((i * M) + M + 1 > N * M)
                            out.println(1);
                        else
                            out.println((i * M) + M + 1);
                    } else {
                        r = true;
                        if (i == N - 1) {
                            out.print("1 ");
                            for (int j = M; j > 1; j--) {
                                out.print((j + (i * M)) + " ");
                            }
                            out.println();
                            break;
                        }
                        for (int j = M + 1; j > 2; j--) {
                            out.print((j + (i * M)) + " ");
                        }
                        out.println((i * M) + 2);
                    }
                }
            }
        }

        out.flush();
    }
}
