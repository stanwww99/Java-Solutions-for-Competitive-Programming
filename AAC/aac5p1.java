package AAC;

import java.util.Scanner;

public class aac5p1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int odd = 0;
        int even = 0;
        int a = s.nextInt();
        for(int i = 0; i < a; i++) {
            int temp = s.nextInt();
            if(temp%2 == 1) {
                odd++;
            }else {
                even++;
            }
        }
        System.out.println(odd/2 + even/2);
        s.close();
    }

}
