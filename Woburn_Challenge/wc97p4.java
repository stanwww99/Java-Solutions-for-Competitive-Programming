package Woburn_Challenge;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class wc97p4 {
    public static boolean permABCDEF(String k) {
        Set<Character> data = new HashSet<>();
        for(char c: k.toCharArray()) {
            if(data.contains(c)) {
                return false;
            }
            data.add(c);
        }
        return true;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        s.nextLine();
        for(int i = 0; i < N; i++) {
            String k = s.nextLine();
            System.out.println(permABCDEF(k)? "OK.":"Nope.");
        }


        s.close();
    }

}
