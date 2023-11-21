import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static int[] getIntArray() {
        return Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
    }

    static double getDist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void main(String[] args) {
        int[] nm = getIntArray();

        boolean[] isValid = new boolean[nm[0]];
        Arrays.fill(isValid, true);

        int[][] spots = new int[nm[0]][2];
        for (int i = 0; i < nm[0]; i++) {
            int[] xy = getIntArray();
            spots[i] = xy;
        }

        int[] P = getIntArray();
        int[] R = getIntArray();

        for (int i = 0; i < nm[1]; i++) {
            int p = P[i];
            int r = R[i + 1];
            int[] xy = spots[p - 1];
            isValid[p - 1] = false;
            for (int j = 0; j < nm[0]; j++) {
                int[] xy2 = spots[j];
                if (getDist(xy[0], xy[1], xy2[0], xy2[1]) <= r) {
                    isValid[j] = false;
                }
            }
        }

        boolean flag = true;
        while (flag) {
            flag = false;

            for (int i = 0; i < nm[0]; i++) {
                {
                    if (!isValid[i])
                        continue;

                    int[] xy = spots[i];
                    for (int j = 0; j < nm[0]; j++) {
                        if (isValid[j])
                            continue;

                        int[] xy2 = spots[j];
                        if (getDist(xy[0], xy[1], xy2[0], xy2[1]) <= R[0]) {
                            isValid[j] = true;
                            flag = true;
                        }
                    }
                }
            }
        }
        int cnt = 0;
        for (boolean b : isValid)
            if (b)
                cnt++;

        System.out.println(cnt);
    }
}
