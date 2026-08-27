package CIW;
import java.io.*;
import java.util.StringTokenizer;

public class ciw25p4 {
    static long N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Long.parseLong(st.nextToken());
        M = Long.parseLong(st.nextToken());

        long total = N * M;
        long need = (total + 1) / 2;

        long lo = 1, hi = maxConc(N, M);
        long ans = hi;
        while (lo <= hi) {
            long mid = lo + ((hi - lo) >> 1);
            if (countLE(mid) >= need) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        System.out.println(ans);
    }

    static long maxConc(long n, long m) {
        long pow = pow10(digits(m));
        return n * pow + m;
    }

    static int digits(long x) {
        if (x == 0) return 1;
        int d = 0;
        while (x > 0) { x /= 10; d++; }
        return d;
    }

    static long pow10(int d) {
        long p = 1;
        while (d-- > 0) p *= 10L;
        return p;
    }

    static long countLE(long x) {
        long res = 0;
        int maxL = digits(M);
        for (int L = 1; L <= maxL; L++) {
            long powL = pow10(L);
            long jLo = (L == 1) ? 1 : pow10(L - 1);
            long jHi = Math.min(powL - 1, M);
            if (jLo > jHi) continue;
            long iMaxPossible = (x - jLo) >= 0 ? (x - jLo) / powL : -1;
            if (iMaxPossible <= 0) continue;
            long fullI = Math.min(N, iMaxPossible);
            long iFull = (x - jHi) >= 0 ? (x - jHi) / powL : -1;
            if (iFull >= 1) {
                long takeFull = Math.min(N, iFull);
                if (takeFull >= 1) {
                    res += takeFull * (jHi - jLo + 1);
                }
            }

            long from = Math.max(1, (iFull + 1));
            long to = Math.min(N, fullI);
            if (from <= to) {
                long cntI = to - from + 1;
                long term1 = cntI * (x - jLo + 1);
                long sumI = (from + to) * cntI / 2;
                long term2 = powL * sumI;
                long add = term1 - term2;
                if (add > 0) res += add;
            }
        }
        return res;
    }
}
