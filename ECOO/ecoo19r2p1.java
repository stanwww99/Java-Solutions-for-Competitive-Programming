package ECOO;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ecoo19r2p1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        for(int i = 0; i < 10; i++) {
            int N = s.nextInt();
            Set<String> email = new HashSet<>();
            for(int j = 0; j < N; j++) {
                String str = s.next().toLowerCase();
                StringBuilder temp = new StringBuilder();
                for(int k = 0; k < str.indexOf('@'); k++ ) {
                    if(str.charAt(k) != '.') {
                        temp.append(str.charAt(k));
                    }
                }
                temp.append(str.substring(str.indexOf('@')));
                str = temp.toString();
                if(str.contains("+")) {
                    str = str.substring(0, str.indexOf('+')) + str.substring(str.indexOf('@'));
                }
                email.add(str);
            }
            System.out.println(email.size());
        }
        s.close();
    }

}
