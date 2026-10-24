package ECOO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ecoo16r1p3 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        for(int i = 0; i < 10; i++) {
            int N = s.nextInt();
            List<Integer> perm = new ArrayList<>();
            List<Integer> side = new ArrayList<>();
            int[] original = new int[N];
            for(int j = 0; j < N; j++) {
                int temp = s.nextInt();
                perm.add(temp);
                original[temp - 1] = j;
            }
            int cost = 0;
            int lastcar = original[N - 1];
            for(int j = N - 2; j >= 0; j--) {
                int idx = original[j];
                for(int k = 0; k < side.size(); k++) {
                    if(side.get(k) >idx) {
                        idx++;
                    }
                }
                if(lastcar < idx) {
                    perm.add(perm.get(idx), 0);
                    perm.remove(idx + 1);
                    side.add(idx);
                    cost += idx;
                    lastcar = 0;
                }else {
                    lastcar = idx;
                }
            }

            System.out.println(cost);

        }
        s.close();
    }
}
