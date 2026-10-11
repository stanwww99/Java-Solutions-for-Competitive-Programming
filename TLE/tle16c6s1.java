package TLE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class Triple{
    String question;
    int preference;
    int index;
    Triple(String question, int preference, int index){
        this.question = question;
        this.preference = preference;
        this.index = index;
    }
    public int getPreference() {
        return preference;
    }
    public int getIndex() {
        return index;
    }
}
public class tle16c6s1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        Map<String, Integer> preference = new HashMap<>();
        for(int i = 0; i < T; i++) {
            preference.put(s.next(), i + 1);
        }
        int N = s.nextInt();
        List<Triple> qorder = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            String question = s.next();
            qorder.add(new Triple(question, preference.get(question), i + 1));
        }
        qorder.sort(Comparator.comparing(Triple:: getPreference).thenComparing(Triple:: getIndex));
        for(Triple t: qorder) {
            System.out.println(t.index);
        }
        s.close();
    }

}