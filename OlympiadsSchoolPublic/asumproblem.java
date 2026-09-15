package OlympiadsSchoolPublic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class asumproblem {
    static int n;
    static long sum;
    static BufferedReader in;
    public static void solve (int l, int r, int dl, int dr) throws IOException {
        if (l > r) return;
        int m = ((l+r+1)>>1), k = n-m+1;
        int low = dl, high = dr, mid, ans=-1,ret;
        while (low <= high) {
            mid = low+high>>1;
            System.out.println("? " + mid);
            System.out.flush();
            ret = Integer.parseInt(in.readLine());
            if (ret >= k) {low=mid+1;ans=mid;}
            else high = mid-1;
        }
        sum += ans;
        solve (l,m-1,dl,ans); solve (m+1,r,ans,dr);
    }
    public static void main(String[]args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(in.readLine()); sum = 0;
        solve(1,n,0,10000000);
        System.out.println("! " + sum);
        System.out.flush();
    }
}