package EGOI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class egoi23p1 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long total = 0;
        long globalChange = 0;
        Map<Long, Integer> arr = new HashMap<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            long num = Integer.parseInt(st.nextToken());
            total += num;
            arr.put(num, arr.getOrDefault(num, 0)+1);
        }
        int Q = Integer.parseInt(br.readLine());
        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            String event = st.nextToken();
            if(event.equals("SET")) {
                long x = Integer.parseInt(st.nextToken());
                long y = Integer.parseInt(st.nextToken());
                long globalX = x - globalChange;
                long globalY = y - globalChange;
                int cnt =arr.getOrDefault(globalX, 0);
                total += (y -x)*cnt;
                arr.put(globalX, 0);
                arr.put(globalY, arr.getOrDefault(globalY, 0) + cnt);

                System.out.println(total);
            }else {
                long wholechange = Integer.parseInt(st.nextToken());
                globalChange += wholechange;
                total += wholechange*N;
                System.out.println(total);
            }
        }

    }

}

