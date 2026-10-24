package ECOO;

import java.io.*;
import java.util.*;
public class ecoo17r3p2 {
    static final long MOD = 1_000_000_007L;

    static class Node {
        HashMap<Integer, Node> children = new HashMap<>();
        int maxChildIndex = 0;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int tc = 0; tc < 10; tc++) {
            int N = Integer.parseInt(br.readLine());
            Node root = new Node();
            for (int i = 0; i < N; i++) {
                String id = br.readLine();
                String[] parts = id.split("\\.");
                if (parts.length == 1 && parts[0].equals("0")) {
                    continue;
                }
                Node cur = root;
                for (int p = 1; p < parts.length; p++) {
                    int idx = Integer.parseInt(parts[p]);
                    cur.maxChildIndex = Math.max(cur.maxChildIndex, idx);
                    Node child = cur.children.get(idx);
                    if (child == null) {
                        child = new Node();
                        cur.children.put(idx, child);
                    }
                    cur = child;
                }
            }
            HashMap<Node, Long> memo = new HashMap<>();
            long ans = dfsSize(root, memo);
            System.out.println(ans % MOD);
        }
    }

    static long dfsSize(Node node, HashMap<Node, Long> memo) {
        if (memo.containsKey(node)) return memo.get(node);
        long sumChildrenSizes = 0;
        int presentCount = node.children.size();
        for (Node child : node.children.values()) {
            sumChildrenSizes += dfsSize(child, memo);
            if (sumChildrenSizes >= Long.MAX_VALUE / 4) sumChildrenSizes %= MOD; // keep numbers manageable
        }
        long m = node.maxChildIndex;
        // size = 1 + (m - presentCount) + sumChildrenSizes
        long res = (1 + ((m - presentCount) % MOD) + (sumChildrenSizes % MOD)) % MOD;
        memo.put(node, res);
        return res;
    }
}