package Woburn_Challenge;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class wc01p3 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for(int i = 0; i < N; i++) {
            int temp = Integer.parseInt(br.readLine());
            String s1 = Integer.toString(temp, 2);
            String s2 = new StringBuilder(s1).reverse().toString();
            System.out.println(Integer.parseInt(s2, 2));
        }
    }

}
