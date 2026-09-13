package VM7WC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class vmss7wc15c2p3 {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        long[] stackH = new long[N];
        long[] stackC = new long[N];
        int top = -1;
        long result = 0;
        for (int i = 0; i < N; i++) {
            long h = Long.parseLong(in.readLine());
            long cnt = 1;
            while (top >= 0 && stackH[top] <= h) {
                result += stackC[top];
                if (stackH[top] == h) {
                    cnt = stackC[top] + 1;
                }
                top--;
            }
            if (top >= 0) {
                result++;
            }
            stackH[++top] = h;
            stackC[top] = cnt;
        }
        System.out.println(result);
    }
}
