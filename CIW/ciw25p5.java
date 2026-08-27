package CIW;

import java.io.*;
import java.util.*;

public class ciw25p5 {
    static int nextVar;
    static List<String> gates;
    static int zeroVar;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);
        int C = Integer.parseInt(parts[2]);

        gates = new ArrayList<>();
        nextVar = N + M;

        // Generate a constant 0 (XOR any input with itself)
        zeroVar = nextVar++;
        gates.add("XOR 0 0");

        // Compute all partial products (AND of each x_i with y_j)
        int[][] pp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                pp[i][j] = nextVar++;
                gates.add("AND " + i + " " + (N + j));
            }
        }

        int maxCol = N + M - 1; // columns 0 .. N+M-1
        List<Integer>[] partials = new ArrayList[maxCol + 1];
        for (int k = 0; k <= maxCol; k++) {
            partials[k] = new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                partials[i + j].add(pp[i][j]);
            }
        }

        int[] productBitVars = new int[maxCol + 1];
        List<Integer> nextCarries = new ArrayList<>();

        // Column-by-column carry-save reduction
        for (int k = 0; k <= maxCol; k++) {
            List<Integer> bits = new ArrayList<>(nextCarries);
            bits.addAll(partials[k]);
            nextCarries = new ArrayList<>();

            while (bits.size() >= 3) {
                int a = bits.remove(bits.size() - 1);
                int b = bits.remove(bits.size() - 1);
                int c = bits.remove(bits.size() - 1);
                int[] res = fullAdder(a, b, c);
                bits.add(res[0]);
                nextCarries.add(res[1]);
            }
            if (bits.size() == 2) {
                int a = bits.remove(bits.size() - 1);
                int b = bits.remove(bits.size() - 1);
                int[] res = halfAdder(a, b);
                bits.add(res[0]);
                nextCarries.add(res[1]);
            }
            if (bits.size() == 1) {
                productBitVars[k] = bits.get(0);
            } else {
                productBitVars[k] = zeroVar;
            }
        }

        // The last N+M gates must output the product bits (LSB to MSB)
        for (int k = 0; k <= maxCol; k++) {
            int v = productBitVars[k];
            gates.add("OR " + v + " " + v); // copy the value
            nextVar++;
        }

        // Output the APL program
        StringBuilder sb = new StringBuilder();
        sb.append(gates.size()).append("\n");
        for (String g : gates) {
            sb.append(g).append("\n");
        }
        System.out.print(sb);
    }

    // Full adder: returns {sum, carry}
    static int[] fullAdder(int a, int b, int c) {
        int t1 = nextVar++;
        gates.add("XOR " + a + " " + b);
        int sum = nextVar++;
        gates.add("XOR " + t1 + " " + c);
        int t2 = nextVar++;
        gates.add("AND " + a + " " + b);
        int t3 = nextVar++;
        gates.add("AND " + c + " " + t1);
        int carry = nextVar++;
        gates.add("OR " + t2 + " " + t3);
        return new int[]{sum, carry};
    }

    // Half adder: returns {sum, carry}
    static int[] halfAdder(int a, int b) {
        int sum = nextVar++;
        gates.add("XOR " + a + " " + b);
        int carry = nextVar++;
        gates.add("AND " + a + " " + b);
        return new int[]{sum, carry};
    }
}
