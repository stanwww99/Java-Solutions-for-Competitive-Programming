package classics;
import java.util.*;

public class a4b1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int[] a = new int[N];
        for(int i = 0; i < N; i++) {
            a[i] = s.nextInt();
        }
        Arrays.sort(a);
        for(int i = 0; i < N; i++) {
            System.out.println(a[i]);
        }
        s.close();
    }
}