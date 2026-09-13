package VM7WC;

import java.util.*;

public class vmss7wc16c1p1 {
    public static List<Integer> primeFactor(int n){
        List<Integer> primeFactors = new ArrayList<>();
        while(n%2 == 0) {
            primeFactors.add(2);
            n /= 2;
        }
        for(int i = 3; i <= Math.sqrt(n); i++) {
            while(n%i == 0) {
                primeFactors.add(i);
                n /= i;
            }
        }
        if(n > 2) {
            primeFactors.add(n);
        }
        return primeFactors;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        for(int j: primeFactor(s.nextInt())) {
            System.out.println(j);
        }
        s.close();
    }

}
