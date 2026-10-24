package ECOO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ecoo13r1p1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null) return;
        int next = Integer.parseInt(line.trim());
        int waiting = 0;
        int lateCount = 0;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equals("EOF")) break;
            if (line.equals("TAKE")) {
                lateCount++;
                waiting++;
                next++;
                if (next > 999) next = 1;
            } else if (line.equals("SERVE")) {
                if (waiting > 0) waiting--;
            } else if (line.equals("CLOSE")) {
                System.out.println(lateCount + " " + waiting + " " + next);
                // After closing, those waiting are served and next day starts fresh
                waiting = 0;
                lateCount = 0;
            }
            // ignore any other lines (none expected)
        }
    }
}