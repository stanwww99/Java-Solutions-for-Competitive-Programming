package FHC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class fhc15c2p1 {
    static boolean canSort(Deque<Integer> a, boolean fromFront) {
        Deque<Integer> dest = new ArrayDeque<>();
        if (fromFront) {
            dest.addLast(a.pollFirst());
        } else {
            dest.addLast(a.pollLast());
        }
        while (!a.isEmpty()) {
            if (a.peekFirst() == dest.peekFirst() - 1) {
                dest.addFirst(a.pollFirst());
            } else if (a.peekFirst() == dest.peekLast() + 1) {
                dest.addLast(a.pollFirst());
            } else if (a.peekLast() == dest.peekFirst() - 1) {
                dest.addFirst(a.pollLast());
            } else if (a.peekLast() == dest.peekLast() + 1) {
                dest.addLast(a.pollLast());
            } else {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            Deque<Integer> a = new ArrayDeque<>();
            for (int i = 0; i < N; i++) {
                a.addLast(Integer.parseInt(st.nextToken()));
            }
            if (canSort(new ArrayDeque<>(a), false) || canSort(new ArrayDeque<>(a), true)) {
                System.out.println("Case #" + t + ": yes");
            } else {
                System.out.println("Case #" + t + ": no");
            }
        }
    }
}
