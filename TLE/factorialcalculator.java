package TLE;

import java.util.Scanner;

public class factorialcalculator {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        double coefficient = 1;
        int power = 0;
        for(int i = 2; i <= N; i++) {
            coefficient*=i;
            while(coefficient >= 10) {
                power++;
                coefficient /= 10;
            }
        }
        System.out.println(coefficient + "e+" + power);
        s.close();

    }

}