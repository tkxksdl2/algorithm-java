import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] dp = new int[n + 1];
        int[] acc = new int[n + 1];
        Arrays.fill(dp, 0);
        Arrays.fill(acc, 0);

        if (n >= 2)
            dp[2] = 3;
        if (n >= 4) {
            for (int i = 4; i <= n; i = i + 2)
                dp[i] = 2;
        }

        for (int i = 2; i <= n; i++) {
            dp[i] += dp[i - 2] * 3;
            if (i >= 5)
                dp[i] += acc[i - 4] * 2;

            acc[i] = acc[i - 2] + dp[i];
        }

        System.out.println(dp[n]);
    }
}
