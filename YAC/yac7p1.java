package YAC;

import java.util.*;

public class yac7p1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int[] value = new int[N];
        int[] printOrder = new int[N];

        for(int i = 0; i < N; i++) {
            value[i] = s.nextInt();
        }
        long total = 0;
        int count = 0;
        boolean odd = false;
        for(int i = 0; i < N; i++) {
            if(value[i]%2 != 0) {
                printOrder[count++] = i + 1;
                total += value[i]/2;
                odd = true;
                break;
            }
        }
        for(int i = 0; i < N; i++) {
            if(value[i]%2 ==0) {
                printOrder[count++] = i + 1;
                if(odd) {
                    total += value[i]/2 - 1;
                }else {
                    total += value[i]/2;
                }
            }
        }
        if(odd) {
            for(int i = printOrder[0]; i < N; i++) {
                if(value[i]%2 != 0) {
                    total += value[i]/2;
                    printOrder[count++] = i + 1;
                }
            }
        }
        System.out.println(total);
        for(int i: printOrder) {
            System.out.print(i + " ");
        }
        s.close();
    }
}