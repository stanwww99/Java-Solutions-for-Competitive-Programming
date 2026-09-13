package VM7WC;

import java.util.Scanner;

public class vmss7wc15c2p1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int[] height = new int[N];
        int ugly = 0;
        for(int i = 0; i < N; i++) {
            height[i] = s.nextInt();
        }
        for(int i = 0; i < N; i++) {
            if(i == 0) {
                if(height[i + 1] <= 41 && height[i] <= 41) {
                    ugly++;
                }
            }else if(i == N - 1) {
                if(height[i - 1] <= 41 && height[i] <= 41) {
                    ugly++;
                }
            }else {
                if(height[i - 1] <= 41 && height[i] <= 41 && height[i + 1] <= 41) {
                    ugly++;
                }
            }
        }
        System.out.println(ugly);
        s.close();

    }

}