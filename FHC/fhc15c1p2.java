package FHC;
import java.io.*;
public class fhc15c1p2 {
    static final int ALPH = 26;

    static class Node {
        Node[] next = new Node[ALPH];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            Node root = new Node();
            long typed = 0L;
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                Node cur = root;
                boolean decided = false;
                for (int j = 0; j < s.length(); j++) {
                    int c = s.charAt(j) - 'a';
                    if (!decided) {
                        if (cur.next[c] == null) {
                            typed += (j + 1);
                            decided = true;
                        }
                    }

                    if (cur.next[c] == null) cur.next[c] = new Node();
                    cur = cur.next[c];
                }
                if (!decided) {
                    typed += s.length();
                }
            }
            System.out.println("Case #" +tc + ": " + typed);
        }
    }
}