package EGOI;
import java.io.*;
import java.util.*;

public class egoi21p1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        int k2 = maxPowerInRange(a, b, 2);
        int k5 = maxPowerInRange(a, b, 5);

        System.out.println(Math.min(k2, k5));
    }
    static int maxPowerInRange(long a, long b, int p) {
        int k = 0;
        long pow = p;
        while (pow <= b) {
            long ceilDiv = (a + pow - 1) / pow;
            if (ceilDiv <= b / pow) {
                k++;
            } else {
                break;
            }
            if (pow > b / p) break;
            pow *= p;
        }
        return k;
    }
}