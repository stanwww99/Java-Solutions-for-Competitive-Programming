package AAC;

import java.util.*;
import java.io.*;

public class aac5p4 {
    static int n, x;
    public static boolean ok(int mid) {
        int pos = x;
        for (int i = mid; i > 0; i--) {
            if (pos + i <= n && pos - i > 0) {
                int right1 = Math.abs((pos + i) - (n / 2));
                int right2 = Math.abs((pos + i) - ((n - 1) / 2) - 1);
                int left1 = Math.abs((pos - i) - (n / 2));
                int left2 = Math.abs((pos - i) - ((n - 1) / 2) - 1);
                if (Math.min(right1, right2) > Math.min(left1, left2)) {
                    pos -= i;
                } else {
                    pos += i;
                }
            } else if (pos + i <= n) {
                pos += i;
            } else if (pos - i > 0) {
                pos -= i;
            } else {
                return true;
            }
        }
        if (pos + mid + 1 > n && pos - mid - 1 <= 0)
            return true;
        return false;
    }

    public static void solve() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        x = sc.nextInt();

        // Binary search for the minimal mid for which ok(mid) returns true
        int l = 0, r = n, ans = 0;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (ok(mid)) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        // Simulation to produce the list of moves (the vector "a" in C++)
        int pos = x;
        Queue<Integer> q = new LinkedList<>();
        Deque<Integer> q2 = new LinkedList<>();
        // Push values ans, ans-1, ..., 1 into the queue
        for (int i = ans; i >= 1; i--) {
            q.offer(i);
        }
        ArrayList<Integer> moves = new ArrayList<>();

        // Process the queues until both are empty.
        while (!q.isEmpty() || !q2.isEmpty()) {
            if (!q2.isEmpty()) {
                int i = q2.pollFirst();
                if (pos + i <= n && pos - i > 0) {
                    int right1 = Math.abs((pos + i) - (n / 2));
                    int right2 = Math.abs((pos + i) - ((n - 1) / 2) - 1);
                    int left1 = Math.abs((pos - i) - (n / 2));
                    int left2 = Math.abs((pos - i) - ((n - 1) / 2) - 1);
                    if (Math.min(right1, right2) > Math.min(left1, left2)) {
                        pos -= i;
                        moves.add(-i);
                    } else {
                        pos += i;
                        moves.add(i);
                    }
                } else if (pos + i <= n) {
                    pos += i;
                    moves.add(i);
                } else if (pos - i > 0) {
                    pos -= i;
                    moves.add(-i);
                } else {
                    q2.addFirst(i);
                }
            }
            if (!q.isEmpty()) {
                int i = q.poll();
                if (pos + i <= n && pos - i > 0) {
                    int right1 = Math.abs((pos + i) - (n / 2));
                    int right2 = Math.abs((pos + i) - ((n - 1) / 2) - 1);
                    int left1 = Math.abs((pos - i) - (n / 2));
                    int left2 = Math.abs((pos - i) - ((n - 1) / 2) - 1);
                    if (Math.min(right1, right2) > Math.min(left1, left2)) {
                        pos -= i;
                        moves.add(-i);
                    } else {
                        pos += i;
                        moves.add(i);
                    }
                } else if (pos + i <= n) {
                    pos += i;
                    moves.add(i);
                } else if (pos - i > 0) {
                    pos -= i;
                    moves.add(-i);
                } else {
                    q2.addLast(i);
                }
            }
        }

        // Output the solution: first the number of moves, then each move on its own line.
        System.out.println(moves.size());
        for (int m : moves) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        solve();
    }
}