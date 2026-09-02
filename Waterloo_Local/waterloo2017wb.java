package Waterloo_Local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class waterloo2017wb {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] freq = new  int[26];
        String s = br.readLine();
        char least = 'a';
        for(char c: s.toCharArray()) {
            freq[c - 'a']++;
        }
        for(char i = 'a'; i <= 'z'; i++) {
            if(freq[i - 'a'] < freq[least - 'a']) {
                least = i;
            }
        }
        if(K > N || freq[least - 'a'] > K) {
            System.out.println("WRONGANSWER");
            return;
        }
        K -= freq[least - 'a'];
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < N; i++) {
            if(least == s.charAt(i)) {
                temp.append(s.charAt(i));
            }else if(K > 0) {
                temp.append(s.charAt(i));
                K--;
            }else {
                temp.append(least);
            }
        }
        System.out.println(temp.toString());
    }

}