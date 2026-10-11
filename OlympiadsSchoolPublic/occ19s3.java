package OlympiadsSchoolPublic;

import java.io.*;

public class occ19s3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder out = new StringBuilder();

        for (int t = 0; t < T; t++) {
            String s = br.readLine().trim();

            boolean hasN = false;
            boolean noNN = true;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'N') {
                    hasN = true;
                    if (i > 0 && s.charAt(i - 1) == 'N') {
                        noNN = false;
                    }
                }
            }

            // B-word: contains at least one N and has no substring "NN"
            int b = (hasN && noNN) ? 1 : 0;

            // A-word: starts with N and has no substring "NN"
            int a = (noNN && s.charAt(0) == 'N') ? 1 : 0;

            out.append(a).append(' ').append(b).append('\n');
        }

        System.out.print(out);
    }
}