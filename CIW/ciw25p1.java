package CIW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ciw25p1 {

    static class Course {
        int s, f, b, e, mandatory, v;
        long value;

        public Course(int s, int f, int b, int e, int mandatory, int v) {
            this.s = s;
            this.f = f;
            this.b = b;
            this.e = e;
            this.mandatory = mandatory;
            this.v = v;
            long days = f - s + 1;
            long lectureDuration = e - b + 1;
            this.value = days * lectureDuration * v;
        }
    }
    static boolean conflict(Course a, Course b) {
        if (Math.max(a.s, b.s) > Math.min(a.f, b.f))
            return false;

        return (a.b <= b.e && b.b <= a.e);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Course[] courses = new Course[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int mandatory = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            courses[i] = new Course(s, f, b, e, mandatory, v);
        }
        int[] conflictMask = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (conflict(courses[i], courses[j])) {
                    conflictMask[i] |= (1 << j);
                    conflictMask[j] |= (1 << i);
                }
            }
        }
        int mandatoryMask = 0;
        for (int i = 0; i < N; i++) {
            if (courses[i].mandatory == 1) {
                mandatoryMask |= (1 << i);
            }
        }
        boolean mandatoryConflict = false;
        for (int i = 0; i < N; i++) {
            if ((mandatoryMask & (1 << i)) != 0) {
                if ((conflictMask[i] & mandatoryMask) != 0) {
                    mandatoryConflict = true;
                    break;
                }
            }
        }
        if (mandatoryConflict) {
            System.out.println(-1);
            return;
        }

        long ans = -1;
        int totalSubsets = 1 << N;

        for (int mask = 0; mask < totalSubsets; mask++) {
            if (Integer.bitCount(mask) < M)
                continue;

            if ((mask & mandatoryMask) != mandatoryMask)
                continue;

            boolean valid = true;
            for (int i = 0; i < N && valid; i++) {
                if ((mask & (1 << i)) != 0) {
                    if ((mask & conflictMask[i]) != 0) {
                        valid = false;
                    }
                }
            }
            if (!valid)
                continue;
            long sum = 0;
            for (int i = 0; i < N; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += courses[i].value;
                }
            }
            ans = Math.max(ans, sum);
        }

        System.out.println(ans);
    }
}
