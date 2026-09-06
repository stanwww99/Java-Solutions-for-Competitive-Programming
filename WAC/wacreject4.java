package WAC;
import java.io.*;
import java.util.*;

public class wacreject4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        int N = Integer.parseInt(br.readLine().trim());
        int m = Math.min(N, 1500);
        int[] aValues = new int[m + 1];
        int[] bValues = new int[m + 1];

        int baseB = -1;

        for (int i = 1; i <= m; i++) {
            out.println(i + " " + 1);
            out.flush();
            String response = br.readLine();
            if (response == null)
                break;
            String[] parts = response.trim().split("\\s+");
            if(parts.length < 2)
                break;
            int aVal = Integer.parseInt(parts[0]);
            int bVal = Integer.parseInt(parts[1]);
            if(aVal == bVal)
                return;
            aValues[i] = aVal;
            if(i == 1)
                baseB = bVal;
        }
        for (int j = 1; j <= m; j++) {
            if(j == 1) {
                bValues[j] = baseB;
            } else {
                out.println(1 + " " + j);
                out.flush();
                String response = br.readLine();
                if(response == null)
                    break;
                String[] parts = response.trim().split("\\s+");
                if(parts.length < 2)
                    break;
                int aVal = Integer.parseInt(parts[0]);
                int bVal = Integer.parseInt(parts[1]);
                if(aVal == bVal)
                    return;
                bValues[j] = bVal;
            }
        }
        HashMap<Integer, Integer> mapA = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            mapA.put(aValues[i], i);
        }
        for (int j = 1; j <= m; j++) {
            int val = bValues[j];
            if(mapA.containsKey(val)) {
                int i = mapA.get(val);
                out.println(i + " " + j);
                out.flush();
                br.readLine();
                return;
            }
        }
        out.println("1 1");
        out.flush();
    }
}