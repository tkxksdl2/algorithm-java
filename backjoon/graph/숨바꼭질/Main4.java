
import java.util.*;

public class Main4 {
    static int MAX_X = 100000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuffer sb = new StringBuffer();
        int s = sc.nextInt();
        int e = sc.nextInt();

        int[] dists = new int[MAX_X + 1];
        int[] backTrack = new int[MAX_X + 1];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[s] = 0;

        Queue<Integer> q = new LinkedList<Integer>();

        q.add(s);

        while (q.size() > 0) {
            int cur = q.poll();
            if (cur == e)
                break;

            int dist = dists[cur];

            int[] nexts = { cur - 1, cur + 1, cur * 2 };
            for (int next : nexts) {
                if (next >= 0 && next <= MAX_X
                        && dist + 1 < dists[next]) {
                    q.add(next);
                    dists[next] = dist + 1;
                    backTrack[next] = cur;
                }
            }
        }
        System.out.println(dists[e]);

        Stack<Integer> routes = new Stack<Integer>();
        int x = e;
        for (int i = dists[e]; i >= 0; i--) {
            routes.push(x);
            x = backTrack[x];
        }
        while (!routes.isEmpty()) {
            sb.append(routes.pop()).append(" ");
        }
        System.out.println(sb.toString());
    }
}
