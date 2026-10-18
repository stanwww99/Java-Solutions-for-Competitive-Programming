package GFSSOC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class gfssoc3s1 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int x = Long.toBinaryString(Long.parseLong(br.readLine())).length();
        for(int i = 0; i < x; i++) {
            System.out.print(1);
        }


    }

}