package YAC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class yac2p2 {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int ask(int l, int r) throws IOException {
        System.out.println("? " + l + " " + r);
        System.out.flush();
        return Integer.parseInt(reader.readLine().trim());
    }

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(reader.readLine().trim());
        int[] res = new int[n];

        int xor_all = ask(1, n);
        int hf = (n - 1) / 2;
        int prev = xor_all;
        for (int i = n - 2; i >= hf; i--) {
            int cur = ask(1, i + 1);
            res[i + 1] = cur ^ prev;
            prev = cur;
        }

        prev = xor_all;
        for (int i = 1; i <= hf; i++) {
            int cur = ask(i + 1, n);
            res[i - 1] = cur ^ prev;
            prev = cur;
        }

        res[hf] = xor_all ^ IntStream.of(res).reduce(0, (a, b) -> a ^ b);
        System.out.println("! " + String.join(" ", IntStream.of(res).mapToObj(String::valueOf).toArray(String[]::new)));
    }
}
