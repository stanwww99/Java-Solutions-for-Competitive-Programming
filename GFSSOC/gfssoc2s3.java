package GFSSOC;

import java.util.Scanner;

public class gfssoc2s3 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = Integer.parseInt(s.next());
        double A = Double.parseDouble(s.next());
        double B = Double.parseDouble(s.next());
        System.out.println((-B/A)/N);
        s.close();

    }

}