//https://www.acmicpc.net/problem/2839

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] dp = new int[Math.max(n + 1, 6)];
        int inValid = 10000;
        Arrays.fill(dp, inValid);
        dp[3] = 1;
        dp[5] = 1;

        if (n >= 6)
            for (int i = 6; i <= n; i++) {
                dp[i] = Math.min(dp[i - 3] + 1, dp[i - 5] + 1);
            }

        System.out.println(dp[n] >= inValid ? -1 : dp[n]);
    }
}
