package SIUCF;
import java.util.*;

public class si17c1p5 {
    static final long MOD = 1000000000L;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();
        long n = sc.nextLong();

        if (n == 0) {
            System.out.println(a % MOD);
            return;
        }
        if (n == 1) {
            System.out.println(b % MOD);
            return;
        }

        long[][] M = {
                {1, 1},
                {1, 0}
        };

        long[][] R = matrixPower(M, n - 1);

        long gn = (R[0][0] * b + R[0][1] * a) % MOD;
        System.out.println(gn);
    }

    static long[][] matrixMultiply(long[][] A, long[][] B) {
        long[][] C = new long[2][2];
        C[0][0] = (A[0][0] * B[0][0] + A[0][1] * B[1][0]) % MOD;
        C[0][1] = (A[0][0] * B[0][1] + A[0][1] * B[1][1]) % MOD;
        C[1][0] = (A[1][0] * B[0][0] + A[1][1] * B[1][0]) % MOD;
        C[1][1] = (A[1][0] * B[0][1] + A[1][1] * B[1][1]) % MOD;
        return C;
    }

    static long[][] matrixPower(long[][] M, long exp) {
        long[][] result = {
                {1, 0},
                {0, 1}
        };

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = matrixMultiply(result, M);
            }
            M = matrixMultiply(M, M);
            exp >>= 1;
        }
        return result;
    }
}
