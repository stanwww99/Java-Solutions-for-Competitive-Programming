package OlympiadsSchoolPublic;
import java.util.Scanner;

public class occ19b1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next().toLowerCase();
        int underscoreCount = 0;
        for(char c: s.toCharArray()) {
            if(c == '_') {
                underscoreCount++;
            }

        }
        if(s.equalsIgnoreCase("Darcy_Liu")) {
            System.out.println("real");
        }else {
            if(underscoreCount >= 2 && s.startsWith("darcy") && s.endsWith("liu")){
                int othercount = 0;
                for(int i = 5; i < s.length() - 3; i++) {
                    if(s.charAt(i) != '_') {
                        othercount++;
                        break;
                    }
                }
                if(othercount == 0) {
                    System.out.println("fake");
                }else {
                    System.out.println("other user");
                }
            }else {
                System.out.println("other user");
            }

        }
        sc.close();

    }

}
