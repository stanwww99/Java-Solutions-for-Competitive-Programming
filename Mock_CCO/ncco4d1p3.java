package Mock_CCO;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ncco4d1p3 {
    private static final int BUFFER_SIZE = 1 << 16;
    private static DataInputStream din;
    private static byte[] buffer;
    private static int bufferPointer, bytesRead;

    static {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public static String nextLine() throws IOException {
        byte[] buf = new byte[256]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    public static int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    public static long nextLong() throws IOException {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }

    public static double nextDouble() throws IOException {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }

    public static char nextChar() throws IOException {
        byte c = read();
        while (c <= ' ')
            c = read();
        char ret = (char) c;
        while (read() > ' ')
            ;
        return ret;
    }

    private static void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private static byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    private static class Pair implements Comparable<Pair>{
        int a, b;
        public Pair(int a_, int b_) {
            a = a_;
            b = b_;
        }
        @Override
        public int compareTo(Pair o) {
            return Integer.compare(b, o.b);
        }
        @Override
        public String toString() {
            return a + ", " + b;
        }
    }

    public static void main(String[] args) throws IOException {
        int N = nextInt() + 1;
        int M = nextInt();
        @SuppressWarnings("unchecked")
        ArrayList<Pair>[] adj = new ArrayList[N];
        for(int i = 1; i < N; i++)
            adj[i] = new ArrayList<>();
        for(int i = 0; i < M; i++){
            int src = nextInt();
            int dest = nextInt();
            int dist = nextInt();
            adj[src].add(new Pair(dest, dist));
            adj[dest].add(new Pair(src, dist));
        }

        int[] first = new int[N];
        int[] second = new int[N];
        boolean[] is = new boolean[N];
        Arrays.fill(first, Integer.MAX_VALUE);
        Arrays.fill(second, Integer.MAX_VALUE);
        LinkedList<Integer> Q = new LinkedList<>();
        first[1] = 0;
        is[1] = true;
        Q.add(1);
        while(!Q.isEmpty()) {
            int x = Q.poll();
            is[x] = false;
            for(Pair p: adj[x]) {
                int v = p.a;
                int w = p.b;
                if(first[v] > first[x] + w) {
                    second[v] = first[v];
                    first[v] = first[x] + w;
                    if(!is[v]) {
                        Q.add(v);
                        is[v] = true;
                    }
                }else if(second[v] > first[x] + w && first[v] != first[x] + w) {
                    second[v] = first[x] + w;
                    if(!is[v]) {
                        Q.add(v);
                        is[v] = true;
                    }
                }
                if(second[x] != Integer.MAX_VALUE && second[v] > second[x] + w && first[v] != second[x] + w) {
                    second[v] = second[x] + w;
                    if(!is[v]) {
                        Q.add(v);
                        is[v] = true;
                    }
                }
            }
        }
        System.out.println(second[N - 1] == Integer.MAX_VALUE ? -1 : second[N - 1]);
    }
}