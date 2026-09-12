package FHC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class fhc15c1p1 {
    static int[] sieveOfEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        int primacity[] = new int[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for (int p = 2; p <= n; p++) {
            if (isPrime[p]) {
                for (int i = p; i <= n; i += p) {
                    primacity[i] += 1;
                    if(i != p) {
                        isPrime[i] = false;
                    }

                }
            }
        }
        return primacity;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int[] primacity = sieveOfEratosthenes(10000000);
        for(int i = 0; i < T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int count = 0;
            for(int j = A; j <= B; j++) {
                if(primacity[j] == K) {
                    count++;
                }
            }
            System.out.println("Case #" + (i + 1) + ": " + count);

        }
    }

}

