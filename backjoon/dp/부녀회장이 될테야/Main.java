//https://www.acmicpc.net/problem/2775

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int k = sc.nextInt();
            int n = sc.nextInt();

            int[][] dp = new int[k][n + 1];

            int acc = 0;
            for (int x = 1; x <= n; x++) {
                acc += x;
                dp[0][x] = acc;
            }

            for (int y = 1; y < k; y++) {
                int preAcc = 0;
                for (int x = 1; x <= n; x++) {
                    preAcc += dp[y - 1][x];
                    dp[y][x] = preAcc;
                }
            }
            System.out.println(dp[k - 1][n]);
        }
    }
}
