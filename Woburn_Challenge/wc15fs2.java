package Woburn_Challenge;
import java.util.Arrays;
import java.util.Scanner;

public class wc15fs2 {

    public static void main(String[] args) {;
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int M = s.nextInt();
        int K = s.nextInt();
        int[] C = new int[N + 1];
        int[] T = new int[M + 1];
        for(int i = 0; i < N; i++){
            C[i + 1] = s.nextInt();
        }
        for(int i = 0; i < M; i++){
            T[i + 1] = s.nextInt();
        }
        Arrays.sort(C);
        Arrays.sort(T);
        long left = 1, right = N;
        long ans = N + 1;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            int cnt = 0;
            int j = 1;
            boolean works = false;
            for(int i =1; i <= M; i++) {
                while(j <= N && T[i] <= C[j] && T[i] >= C[j] - K && cnt <mid) {
                    j++;
                    cnt++;
                }
                cnt= 0;
                if(j > N) {
                    works = true;
                    break;
                }
            }

            if (works) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        System.out.println((ans > N)? -1: ans);
    }

}
