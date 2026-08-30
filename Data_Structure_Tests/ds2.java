package Data_Structure_Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ds2 {
    private static Map<Integer, Integer> parent = new HashMap<>();
    private static Map<Integer, Integer> rank = new HashMap<>();
    private static List<Integer> edgeNo = new ArrayList<>();
    public static void makeSet(Integer x) {
        if(!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 1);
        }
    }
    public static int findHelper(int x) {
        Map<Integer, Integer> m = new HashMap<>();
        return find(x, m);
    }
    public static int find(int x, Map<Integer, Integer> m) {
        int temp = parent.get(x);
        if(temp == x) {
            return temp;
        }else if(m.containsKey(x)) {
            return m.get(x);
        }
        parent.put(x, find(temp, m));
        return parent.get(x);
    }
    public static void union(int x, int y, int M) {
        makeSet(x);
        makeSet(y);
        int xRoot = findHelper(x);
        int yRoot = findHelper(y);
        if(xRoot != yRoot) {
            edgeNo.add(M);
            if(rank.get(xRoot) < rank.get(yRoot)) {
                parent.put(xRoot, yRoot);
            }else if(rank.get(xRoot) > rank.get(yRoot)) {
                parent.put(yRoot, xRoot);
            }else {
                parent.put(yRoot, xRoot);
                rank.put(xRoot, rank.get(xRoot) + 1);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i + 1);
        }
        if(edgeNo.size() + 1 == N) {
            for(int i: edgeNo) {
                System.out.println(i);
            }
        }else {
            System.out.println("Disconnected Graph");
        }
    }

}