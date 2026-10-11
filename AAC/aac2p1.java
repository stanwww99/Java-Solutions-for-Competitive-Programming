package AAC;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class aac2p1{
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        String text = s.next();
        Map<Character, Integer> m =  new HashMap<>();
        for(char c: text.toCharArray()) {
            if(!m.containsKey(c)){
                m.put(c, 1);
            }else {
                m.put(c, m.get(c) + 1);
            }
        }
        int oddlettercount = 0;
        for(char c: m.keySet()) {
            if(m.get(c)%2 == 1) {
                oddlettercount++;
            }
        }
        System.out.println(Math.max(1, oddlettercount));
        s.close();
    }
}
