package Woburn_Challenge;
import java.io.*;
import java.util.*;

public class wc96p6 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 5; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num1 = Integer.parseInt(st.nextToken(), Integer.parseInt(st.nextToken()));
            st = new StringTokenizer(br.readLine());
            int num2 = Integer.parseInt(st.nextToken(), Integer.parseInt(st.nextToken()));
            System.out.println(Integer.toString(num1*num2, Integer.parseInt(br.readLine())));
        }
    }
}
