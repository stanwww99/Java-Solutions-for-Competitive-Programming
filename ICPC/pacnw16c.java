package ICPC;
import java.util.Scanner;
import java.util.Stack;

public class pacnw16c {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n  = s.nextInt();
        int k = s.nextInt();
        int r = s.nextInt();
        boolean[] hasCameras = new boolean[n];
        for(int i = 0; i < k; i++) {
            hasCameras[s.nextInt() - 1] = true;
        }
        int total = 0;
        Stack<Integer> gaps = new Stack<>();
        for(int i = 0; i < r; i++) {
            if(hasCameras[i]) {
                total++;
            }else {
                gaps.push(i);
            }
        }
        int needed = 0;
        for(int i = r - 1; i < n; i++) {
            while(total < 2) {
                int temp = gaps.pop();
                hasCameras[temp] = true;
                total++;
                needed++;
            }
            if(i < n - 1) {
                if(hasCameras[i - r + 1]) {
                    total--;
                }
                if(hasCameras[i + 1]){
                    total++;
                }else {
                    gaps.push(i + 1);
                }
            }


        }
        System.out.println(needed);
        s.close();
    }

}
