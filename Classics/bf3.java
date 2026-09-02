package classics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bf3 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static boolean isPrime(long n) {
        if(n<2) {
            return false;
        }else if(n == 2 || n == 3){
            return true;
        }else if (n%2==0) {
            return false;
        }
        for(long i = 3; i <= Math.sqrt(n); i+=2) {
            if(n%i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        long N = Long.parseLong(br.readLine());
        while(!isPrime(N)) {
            N++;
        }
        System.out.println(N);
    }
}