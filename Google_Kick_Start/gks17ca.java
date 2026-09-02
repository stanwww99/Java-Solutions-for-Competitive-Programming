package Google_Kick_Start;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class gks17ca {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            String w = br.readLine().trim();
            String result = decryptUnique(w);
            System.out.println("Case #" + tc + ": " + result);
        }
    }

    private static String decryptUnique(String encStr) {
        int n = encStr.length();
        int[] enc = new int[n];
        for (int i = 0; i < n; i++) enc[i] = encStr.charAt(i) - 'A';

        // Collect all valid candidate decryptions
        String unique = null;
        int count = 0;

        // Try all 26 possibilities for A[0]
        for (int a0 = 0; a0 < 26; a0++) {
            int[] a = new int[n];
            a[0] = a0;
            if (n >= 2) a[1] = enc[0]; // first encrypted letter equals second plaintext letter

            boolean ok = true;
            // Reconstruct forward: for i from 1 to n-2, compute a[i+1] = enc[i] - a[i-1] (mod 26)
            for (int i = 1; i <= n - 2 && ok; i++) {
                int val = enc[i] - a[i - 1];
                val %= 26;
                if (val < 0) val += 26;
                a[i + 1] = val;
            }

            // Validate final constraint: enc[n-1] must equal a[n-2]
            if (n >= 2) {
                if (enc[n - 1] != a[n - 2]) ok = false;
            }

            if (!ok) continue;

            // Convert to string
            StringBuilder sb = new StringBuilder();
            for (int x : a) sb.append((char) ('A' + x));
            String candidate = sb.toString();

            // Verify that encrypting candidate yields encStr (defensive check)
            if (!encryptMatches(candidate, enc)) continue;

            count++;
            if (count == 1) unique = candidate;
            // If more than one valid, we can stop early if we want, but continue to be sure
            if (count > 1) break;
        }

        if (count == 1) return unique;
        return "AMBIGUOUS";
    }

    // Defensive: re-encrypt plaintext and check equality with given encrypted array
    private static boolean encryptMatches(String plain, int[] enc) {
        int n = plain.length();
        int[] p = new int[n];
        for (int i = 0; i < n; i++) p[i] = plain.charAt(i) - 'A';
        int[] e = new int[n];
        if (n == 1) {
            e[0] = 0; // not used in problem constraints, but keep safe
        } else {
            e[0] = p[1] % 26;
            for (int i = 1; i <= n - 2; i++) {
                e[i] = (p[i - 1] + p[i + 1]) % 26;
            }
            e[n - 1] = p[n - 2] % 26;
        }
        for (int i = 0; i < n; i++) {
            if (e[i] != enc[i]) return false;
        }
        return true;
    }
}
