package EGOI;
import java.io.*;
import java.util.*;

public class egoi23p2 {
    static class SegTree {
        int n;
        long[] tree;
        int[] max_idx;

        public SegTree(int n) {
            this.n = n;
            tree = new long[4 * n];
            max_idx = new int[4 * n];
            build(1, 0, n - 1);
        }

        private void build(int node, int start, int end) {
            if (start == end) {
                max_idx[node] = start;
                return;
            }
            int mid = (start + end) / 2;
            build(2 * node, start, mid);
            build(2 * node + 1, mid + 1, end);
            max_idx[node] = max_idx[2 * node];
        }

        private void update(int node, int start, int end, int idx, long val) {
            if (start == end) {
                tree[node] += val;
                return;
            }
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(2 * node, start, mid, idx, val);
            } else {
                update(2 * node + 1, mid + 1, end, idx, val);
            }
            // If there's a tie, the left child (smaller index) is preferred
            if (tree[2 * node] >= tree[2 * node + 1]) {
                tree[node] = tree[2 * node];
                max_idx[node] = max_idx[2 * node];
            } else {
                tree[node] = tree[2 * node + 1];
                max_idx[node] = max_idx[2 * node + 1];
            }
        }

        public void add(int idx, long val) {
            update(1, 0, n - 1, idx, val);
        }

        public int getMaxPerson() {
            return max_idx[1];
        }
    }

    static class Node {
        int person;
        int end_time;
        int parent;
        ArrayList<Integer> children;
        ArrayList<Integer> medals_created;

        public Node(int person, int end_time, int parent) {
            this.person = person;
            this.end_time = end_time;
            this.parent = parent;
            this.children = new ArrayList<>();
            this.medals_created = new ArrayList<>();
        }
    }

    static class Event {
        int person;
        long val;

        public Event(int person, long val) {
            this.person = person;
            this.val = val;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();

        ArrayList<Node> nodes = new ArrayList<>();
        int[] curr_node = new int[N];
        Arrays.fill(curr_node, -1);

        for (int i = 0; i < N; ++i) {
            if (curr_node[i] == -1) {
                curr_node[i] = nodes.size();
                nodes.add(new Node(i, M, -1));
            }
        }

        for (int i = 0; i < M; ++i) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            int u = curr_node[y];
            if (u != -1) {
                nodes.get(u).end_time = i;

                if (curr_node[x] == -1) {
                    curr_node[x] = nodes.size();
                    nodes.add(new Node(x, M, -1));
                }
                int v = curr_node[x];

                nodes.get(u).parent = v;
                nodes.get(v).children.add(u);
                curr_node[y] = -1;
            }

            if (curr_node[x] == -1) {
                curr_node[x] = nodes.size();
                nodes.add(new Node(x, M, -1));
            }
            int v = curr_node[x];
            nodes.get(v).medals_created.add(i);
        }

        int numNodes = nodes.size();
        int[] in = new int[numNodes];
        int[] outTime = new int[numNodes];
        int[] medal_to_timer = new int[M];
        int timer = 0;

        // Iterative DFS to avoid StackOverflowError
        int[] stackNode = new int[numNodes + 1];
        int[] stackChildIdx = new int[numNodes + 1];

        for (int i = 0; i < numNodes; ++i) {
            if (nodes.get(i).parent == -1) {
                int head = 0;
                stackNode[head] = i;
                stackChildIdx[head] = 0;
                in[i] = timer;

                while (head >= 0) {
                    int u = stackNode[head];
                    int cIdx = stackChildIdx[head];
                    Node node = nodes.get(u);

                    if (cIdx < node.children.size()) {
                        int child = node.children.get(cIdx);
                        stackChildIdx[head]++; // increment for next iteration

                        head++;
                        stackNode[head] = child;
                        stackChildIdx[head] = 0;
                        in[child] = timer;
                    } else {
                        for (int m : node.medals_created) {
                            medal_to_timer[m] = timer;
                            timer++;
                        }
                        outTime[u] = timer - 1;
                        head--;
                    }
                }
            }
        }

        // Initialize events lists
        @SuppressWarnings("unchecked")
        ArrayList<Event>[] events = new ArrayList[timer + 1];
        for (int i = 0; i <= timer; i++) {
            events[i] = new ArrayList<>();
        }

        for (int i = 0; i < numNodes; ++i) {
            Node n = nodes.get(i);
            for (int m : n.medals_created) {
                int t = medal_to_timer[m];
                if (t <= t) {
                    events[t].add(new Event(n.person, n.end_time - m));
                    events[t + 1].add(new Event(n.person, -(n.end_time - m)));
                }
            }
            if (n.parent != -1) {
                int p = n.parent;
                Node parentNode = nodes.get(p);
                int L = in[i];
                int R = outTime[i];
                if (L <= R) {
                    events[L].add(new Event(parentNode.person, parentNode.end_time - n.end_time));
                    events[R + 1].add(new Event(parentNode.person, -(parentNode.end_time - n.end_time)));
                }
            }
        }

        SegTree st = new SegTree(N);
        int[] ans = new int[N];

        for (int t = 0; t < timer; ++t) {
            for (Event ev : events[t]) {
                st.add(ev.person, ev.val);
            }
            int winner = st.getMaxPerson();
            ans[winner]++;
        }

        for (int i = 0; i < N; ++i) {
            out.print(ans[i] + (i == N - 1 ? "" : " "));
        }
        out.println();
        out.flush();
    }

    // Fast I/O
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String line = br.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}