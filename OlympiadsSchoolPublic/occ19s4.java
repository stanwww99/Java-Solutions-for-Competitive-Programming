package OlympiadsSchoolPublic;
import java.io.*;
import java.util.*;

public class occ19s4 {

    static TreeSet<Integer> breaks = new TreeSet<>();
    static TreeMap<Integer, Integer> segs = new TreeMap<>();
    static long[] A;
    static int N;

    static void addSeg(int len) {
        segs.put(len, segs.getOrDefault(len, 0) + 1);
    }

    static void removeSeg(int len) {
        int c = segs.get(len);
        if (c == 1) segs.remove(len);
        else segs.put(len, c - 1);
    }

    static void insertBreak(int i) {
        if (!breaks.add(i)) return;
        int r = breaks.higher(i);
        int l = breaks.lower(i);
        removeSeg(r - l);
        addSeg(i - l);
        addSeg(r - i);
    }

    static void removeBreak(int i) {
        if (!breaks.remove(i)) return;
        int r = breaks.higher(i);
        int l = breaks.lower(i);
        removeSeg(i - l);
        removeSeg(r - i);
        addSeg(r - l);
    }

    static void update(int i, long x) {
        if (i > 1) removeBreak(i - 1);
        if (i < N) removeBreak(i);

        A[i] = x;

        if (i > 1 && A[i - 1] > A[i]) insertBreak(i - 1);
        if (i < N && A[i] > A[i + 1]) insertBreak(i);
    }

    static int answer() {
        return segs.lastKey();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        A = new long[N + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) A[i] = Long.parseLong(st.nextToken());

        // sentinels
        breaks.add(0);
        breaks.add(N);

        // initialize breakpoints
        for (int i = 1; i < N; i++) {
            if (A[i] > A[i + 1]) breaks.add(i);
        }

        // initialize segments
        Integer prev = null;
        for (int b : breaks) {
            if (prev != null) addSeg(b - prev);
            prev = b;
        }

        System.out.println(answer());

        for(int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            long x = Long.parseLong(st.nextToken());
            update(i, x);
            System.out.println(answer());
        }
    }
}