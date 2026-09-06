package Woburn_Challenge;

import java.util.Scanner;

public class wc17c1j4 {
    public static boolean isSubsequence(String S1, String S2) {
        int i = 0, j = 0; // pointers for string S1 and S2
        // iterating until reaching end of any one string.
        for (; i < S1.length() && j < S2.length(); j++) {
            if(S1.charAt(i) == S2.charAt(j)) {
                i++; // incrementing i
            }
        }
        return (i == S1.length()); // checking conditon
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);
        String word = s.next();
        System.out.println(isSubsequence("our", word)? "Y" : "N");
        s.close();
    }

}
