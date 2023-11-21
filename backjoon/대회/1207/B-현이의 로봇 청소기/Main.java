import java.util.*;

public class Main {
    static int[] dy = { -1, 1, 0, 0 };
    static int[] dx = { 0, 0, -1, 1 };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nmk = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        int n = nmk[0], m = nmk[1], k = nmk[2];

        int[][] heights = new int[n][m];
        for (int i = 0; i < n; i++) {
            int[] row = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            heights[i] = row;
        }

        int ans = 0;

        boolean[][] visited = new boolean[n][m];
        Queue<int[]> q = new LinkedList<int[]>();

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (visited[y][x])
                    continue;

                int[] start = { y, x };
                q.add(start);
                visited[y][x] = true;
                ans++;
                while (q.size() > 0) {
                    int[] p = q.poll();
                    int cy = p[0], cx = p[1];

                    for (int d = 0; d < 4; d++) {
                        int ny = cy + dy[d], nx = cx + dx[d];

                        if (ny >= 0 && nx >= 0 && ny < n && nx < m && !visited[ny][nx]
                                && Math.abs(heights[cy][cx] - heights[ny][nx]) <= k) {
                            visited[ny][nx] = true;
                            int[] next = { ny, nx };
                            q.add(next);
                        }
                    }
                }
            }
        }

        System.out.println(ans);
    }
}
