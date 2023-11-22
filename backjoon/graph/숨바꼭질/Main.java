
import java.util.*;

public class Main {
    static int MAX_X = 100000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();
        int e = sc.nextInt();

        int[] dists = new int[MAX_X + 1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[s] = 0;

        Queue<Integer> q = new LinkedList<Integer>();

        q.add(s);

        while (q.size() > 0) {
            int cur = q.poll();
            if (cur == e)
                continue;

            int dist = dists[cur];

            int[] nexts = { cur - 1, cur + 1, cur * 2 };
            for (int next : nexts) {
                if (next >= 0 && next <= MAX_X
                        && dist + 1 < dists[next]) {
                    q.add(next);
                    dists[next] = dist + 1;
                }
            }
        }
        System.out.println(dists[e]);
    }
}
