package GFSSOC;

import java.util.Scanner;

public class gfssoc2s1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int S = s.nextInt();
        int M = s.nextInt();
        int L = s.nextInt();
        double A = s.nextDouble();
        double B = s.nextDouble();
        double C = s.nextDouble();
        int choose = (S + M + L)/4;
        if(choose >= L) {
            choose -= L;
            L = 0;
        }else {
            L -= choose;
            choose = 0;
        }
        if(choose >= M) {
            choose -= M;
            M = 0;
        }else {
            M -= choose;
            choose = 0;
        }
        if(choose >= S) {
            choose -= S;
            S = 0;
        }else {
            S -= choose;
            choose = 0;
        }
        System.out.printf("%.2f", S*A + M*B + L*C);
        s.close();

    }

}
