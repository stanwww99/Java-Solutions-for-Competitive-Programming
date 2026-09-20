package MWC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class mwc15c2p2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] H = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            H[i] = Integer.parseInt(st.nextToken());
        }
        int[] stack = new int[N];
        int top = -1;
        for (int i = 1; i <= N; i++) {
            while (top >= 0 && H[stack[top]] <= H[i]) {
                top--;
            }
            int prevGreater = (top >= 0) ? stack[top] : 0;
            int visible = (prevGreater == 0) ? i - 1 : i - prevGreater;
            System.out.print(visible);;

            if (i < N) {
                System.out.print(' ');
            }
            stack[++top] = i;
        }
        System.out.println();
    }
}
