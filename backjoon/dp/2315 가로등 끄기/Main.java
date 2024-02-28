import java.util.*;
import java.io.*;

class Main {
    static StringTokenizer st;
    static int[][] dp;
    static int[] weights;
    static int[] dists;
    static int n, m;
    static int MAX_VALUE = 1000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken()) - 1;

        dp = new int[n][n];
        weights = new int[n];
        dists = new int[n];

        for (int i = 0; i < n; i ++) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            dists[i] = d;
            weights[i] = w;
            if (i >= 1 ) weights[i] += weights[i-1];
        }

        Arrays.stream(dp).forEach(row -> {
            Arrays.fill(row,MAX_VALUE);
        });
        dp[m][m] = 0;

        for (int i = 2; i <= n; i++){
            for (int l = Math.max(m-i+1, 0); l <= Math.min(n-i, m); l++) {
                int r = l+i - 1;

                // 우측
                int d1 = dp[l][r-1] + Math.abs(dists[r-1] - dists[r]) * getBoundExcludeWeight(l, r-1);
                int d2 = dp[r-1][l] + Math.abs(dists[l] - dists[r]) * getBoundExcludeWeight(l,r-1);
                dp[l][r] = Math.min(d1,d2);
                //좌측
                int d3 = dp[l+1][r] + Math.abs(dists[r] - dists[l]) * getBoundExcludeWeight(l+1, r);
                int d4 = dp[r][l+1] + Math.abs(dists[l+1] - dists[l]) * getBoundExcludeWeight(l+1, r);
                dp[r][l] = Math.min(d3, d4);
            }
        }
        System.out.println(Math.min(dp[0][n-1], dp[n-1][0]));
    }

    public static int getBoundExcludeWeight(int a, int b) {
        int left = Math.min(a, b);
        int right = Math.max(a, b);
        return getWeight(left-1) + getWeight(n-1) - getWeight(right);
    }
    public static int getWeight(int i){
        return i < 0 ? 0 : weights[i];
    }
}
