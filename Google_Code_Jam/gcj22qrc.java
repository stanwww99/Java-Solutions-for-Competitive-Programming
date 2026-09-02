package Google_Code_Jam;

import java.util.Arrays;
import java.util.Scanner;

public class gcj22qrc {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        for(int i = 0; i < T; i++) {
            int N = s.nextInt();
            int[] dice = new int[N];
            for(int j = 0; j < N; j++) {
                dice[j] = s.nextInt();
            }
            Arrays.sort(dice);
            int length = 0;
            for(int j = 0; j < N; j++) {
                if(dice[j] > length) {
                    length++;
                }
            }
            System.out.println("Case #" + (i+1) + ": " + length);
        }
        s.close();

    }

}