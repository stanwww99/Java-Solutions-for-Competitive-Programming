package AAC;

import java.util.Scanner;

public class aac1p1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int S = s.nextInt();
        int R = s.nextInt();
        if(S*S > R*R*3.14) {
            System.out.println("SQUARE");
        }else {
            System.out.println("CIRCLE");
        }
        s.close();
    }

}