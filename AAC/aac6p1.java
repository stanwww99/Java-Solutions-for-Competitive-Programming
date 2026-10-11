package AAC;

import java.util.Scanner;

public class aac6p1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int K = s.nextInt();
        int[] workout = new int[N];
        long sum = 0;
        for(int i = 0; i < N - 1; i++) {
            workout[i] =  i + 1;
            sum += (i + 1);
        }
        sum += N;
        workout[N - 1] =  N + (int)(K - sum%K);
        for(int i = 0; i < N - 1; i++) {
            System.out.print(workout[i] + " ");
        }
        System.out.println(workout[N - 1]);
        s.close();
    }

}
