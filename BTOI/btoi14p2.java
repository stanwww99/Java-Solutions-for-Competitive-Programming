package Baltic_OI;

import java.io.*;
import java.util.*;

public class btoi14p2 {
    static final long MOD1 = 1_000_000_007L;
    static final long MOD2 = 1_000_000_009L;
    static final long BASE = 91138233L & 0x7FFFFFFFL;

    static class HashPair {
        long h1, h2;
        HashPair(long a, long b){ h1=a; h2=b; }
        public boolean equals(Object o){
            if(!(o instanceof HashPair)) return false;
            HashPair p=(HashPair)o;
            return h1==p.h1 && h2==p.h2;
        }
        public int hashCode(){
            return (int)((h1*31 + h2) ^ (h1>>>32) ^ (h2>>>16));
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String U = br.readLine().trim();
        int n = U.length();
        if ((n & 1) == 0) {
            System.out.println("NOT POSSIBLE");
            return;
        }
        int m = (n - 1) / 2;
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1; pow2[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow1[i] = (pow1[i-1] * BASE) % MOD1;
            pow2[i] = (pow2[i-1] * BASE) % MOD2;
        }

        long[] pref1 = new long[n + 1];
        long[] pref2 = new long[n + 1];
        for (int i = 0; i < n; i++) {
            int c = U.charAt(i);
            pref1[i+1] = (pref1[i] * BASE + c) % MOD1;
            pref2[i+1] = (pref2[i] * BASE + c) % MOD2;
        }
        BiHash getter = new BiHash(pref1, pref2, pow1, pow2);
        Set<HashPair> candidates = new HashSet<>();
        int candidateIndex = -1;

        for (int skip = 0; skip < n; skip++) {
            HashPair hFirst = getHashInT(0, m, skip, getter, n);
            HashPair hSecond = getHashInT(m, m, skip, getter, n);
            if (hFirst != null && hSecond != null && hFirst.equals(hSecond)) {
                if (!candidates.contains(hFirst)) {
                    candidates.add(hFirst);
                    candidateIndex = skip;
                }
                if (candidates.size() > 1) break;
            }
        }

        if (candidates.isEmpty()) {
            System.out.println("NOT POSSIBLE");
        } else if (candidates.size() > 1) {
            System.out.println("NOT UNIQUE");
        } else {

            int skip = candidateIndex;
            StringBuilder sb = new StringBuilder(m);
            for (int t = 0, uIdx = 0; t < m; ) {
                if (uIdx == skip) { uIdx++; continue; }
                sb.append(U.charAt(uIdx));
                uIdx++; t++;
            }
            System.out.println(sb.toString());
        }
    }
    static HashPair getHashInT(int start, int len, int skip, BiHash getter, int n) {
        if (len == 0) return new HashPair(0,0);
        int uStart = start + (start >= skip ? 1 : 0);
        int uEnd = uStart + len - 1;
        if (uEnd < skip || uStart > skip) {
            return getter.substr(uStart, uEnd);
        } else {
            int l1 = skip - uStart;
            int l2 = len - l1;
            HashPair p1 = (l1 > 0) ? getter.substr(uStart, skip - 1) : new HashPair(0,0);
            HashPair p2 = (l2 > 0) ? getter.substr(skip + 1, skip + l2) : new HashPair(0,0);
            long newH1 = ( (p1.h1 * getter.pow1[l2]) % MOD1 + p2.h1 ) % MOD1;
            long newH2 = ( (p1.h2 * getter.pow2[l2]) % MOD2 + p2.h2 ) % MOD2;
            return new HashPair(newH1, newH2);
        }
    }
    static class BiHash {
        long[] pref1, pref2, pow1, pow2;
        BiHash(long[] p1, long[] p2, long[] pw1, long[] pw2){
            pref1 = p1; pref2 = p2; pow1 = pw1; pow2 = pw2;
        }
        HashPair substr(int l, int r) {
            if (l > r) return new HashPair(0,0);
            long x1 = (pref1[r+1] - (pref1[l] * pow1[r-l+1]) % MOD1 + MOD1) % MOD1;
            long x2 = (pref2[r+1] - (pref2[l] * pow2[r-l+1]) % MOD2 + MOD2) % MOD2;
            return new HashPair(x1, x2);
        }
    }
}