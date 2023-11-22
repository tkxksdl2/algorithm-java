import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int lineCnt = sc.nextInt();
        sc.nextLine();
        int ans = 0;

        List<Integer>[] edges = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++)
            edges[i] = new ArrayList<Integer>();

        for (int i = 0; i < lineCnt; i++) {
            int[] se = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            edges[se[0]].add(se[1]);
            edges[se[1]].add(se[0]);
        }

        boolean[] visited = new boolean[n + 1];
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(1);
        visited[1] = true;

        while (q.size() > 0) {
            int start = q.poll();
            for (int end : edges[start]) {
                if (!visited[end]) {
                    visited[end] = true;
                    q.add(end);
                    ans++;
                }
            }
        }

        System.out.println(ans);
    }
}