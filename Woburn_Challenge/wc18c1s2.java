package Woburn_Challenge;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class wc18c1s2 {


    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int W = Integer.parseInt(br.readLine());
        int cnt = 0;
        List<String> words = new ArrayList<>();
        for(int i = 0; i < 26; i++) {
            if(cnt < W) {
                words.add(Character.toString('a' + i));
                cnt++;
            }
        }
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j <26; j++) {
                if(cnt < W) {
                    String s = Character.toString('a' + i) + Character.toString('a' + j);
                    words.add(s);
                    cnt++;
                }

            }
        }
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j <26; j++) {
                for(int k = 0; k < 26; k++) {
                    if(cnt < W) {
                        String s = Character.toString('a' + i) + Character.toString('a' + j) + Character.toString('a' + k);
                        words.add(s);
                        cnt++;
                    }
                }
            }
        }

        for(int i = 0; i < words.size(); i++) {
            if(i == words.size()  - 1){
                System.out.println(words.get(i));
            }else {
                System.out.print(words.get(i) + " ");
            }
        }

    }

}
