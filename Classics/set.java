package classics;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class set {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Set<Integer> set = new HashSet<>();
        int N = s.nextInt();
        for(int i = 0; i < N; i++) {
            set.add(s.nextInt());
        }
        System.out.println(set.size());
        s.close();

    }

}
