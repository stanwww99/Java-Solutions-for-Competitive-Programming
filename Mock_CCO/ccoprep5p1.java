package Mock_CCO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ccoprep5p1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());
        for (int t = 0; t < K; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[] heights = new int[M];
            long bestArea = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    if (st.nextToken().charAt(0) == 'F') {
                        heights[j]++;
                    } else {
                        heights[j] = 0;
                    }
                }
                long area = largestRectangleArea(heights);
                if (area > bestArea) {
                    bestArea = area;
                }
            }

            System.out.println(bestArea * 3);
            if (t != K - 1) {
                br.readLine();
            }
        }
    }

    private static long largestRectangleArea(int[] h) {
        int n = h.length;
        int[] stack = new int[n + 1];
        int top = -1;
        long maxArea = 0;
        stack[++top] = -1;
        for (int i = 0; i < n; i++) {
            while (stack[top] != -1 && h[i] <= h[stack[top]]) {
                int height = h[stack[top--]];
                int width = i - stack[top] - 1;
                long area = (long) height * width;
                if (area > maxArea) {
                    maxArea = area;
                }
            }
            stack[++top] = i;
        }
        while (stack[top] != -1) {
            int height = h[stack[top--]];
            int width = n - stack[top] - 1;
            long area = (long) height * width;
            if (area > maxArea) {
                maxArea = area;
            }
        }

        return maxArea;
    }
}