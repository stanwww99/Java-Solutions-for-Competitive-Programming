package Woburn_Challenge;
import java.io.*;
import java.util.*;

public class wc17c3s2 {
    static class Pair {
        long a, b;
        Pair(long a, long b) { this.a = a; this.b = b; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        List<Pair> c = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long s = Long.parseLong(st.nextToken());
            long e = Long.parseLong(st.nextToken());
            c.add(new Pair(s, e));
        }

        // Sort by end (b) ascending, then by start (a) ascending
        c.sort((p1, p2) -> {
            if (p1.b < p2.b) return -1;
            if (p1.b > p2.b) return 1;
            return Long.compare(p1.a, p2.a);
        });

        // Build filtered array c2 following the same logic as the C++ code:
        // while (n2>0 && c[i].a <= c2[n2-1].a) n2--;
        List<Pair> c2 = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Pair cur = c.get(i);
            while (!c2.isEmpty() && cur.a <= c2.get(c2.size() - 1).a) {
                c2.remove(c2.size() - 1);
            }
            c2.add(cur);
        }

        // Two-pointer greedy loop translated from C++
        int ans = 0;
        int i = 0;
        int j = 0;
        int n2 = c2.size();
        while (i < n) {
            // advance j while next c2's start is < current c[i].b
            while (j + 1 < n2 && c2.get(j + 1).a < c.get(i).b) j++;
            ans++;
            // advance i while c[i].a < c2[j].b
            while (i < n && c.get(i).a < c2.get(j).b) i++;
        }

        System.out.println(ans);
    }
}