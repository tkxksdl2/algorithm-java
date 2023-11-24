import java.io.*;
import java.util.*;

public class Main {
    static int[] dy = { -1, 1, 0, 0 };
    static int[] dx = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int year = 0;

        int[][] icebergs = new int[n][m];
        Queue<Integer> yIces = new LinkedList<Integer>();
        Queue<Integer> xIces = new LinkedList<Integer>();

        for (int y = 0; y < n; y++) {
            StringTokenizer rowSt = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                int value = Integer.parseInt(rowSt.nextToken());
                if (value > 0) {
                    icebergs[y][x] = value;
                    yIces.add(y);
                    xIces.add(x);
                }
            }
        }

        boolean flag = true;
        while (flag) {
            year++;

            Queue<Integer> nextYIces = new LinkedList<Integer>();
            Queue<Integer> nextXIces = new LinkedList<Integer>();
            int[][] nextIcebergs = new int[n][m];

            while (!yIces.isEmpty()) {
                int y = yIces.poll();
                int x = xIces.poll();

                int zeroCnt = 0;
                for (int i = 0; i < 4; i++) {
                    int ny = y + dy[i];
                    int nx = x + dx[i];

                    if (icebergs[ny][nx] == 0)
                        zeroCnt++;
                }
                int nextValue = icebergs[y][x] - zeroCnt;
                if (nextValue > 0) {
                    nextIcebergs[y][x] = nextValue;
                    nextYIces.add(y);
                    nextXIces.add(x);
                }
            }

            if (nextYIces.isEmpty()) {
                year = 0;
                break;
            }

            boolean[][] visited = new boolean[n][m];
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[] { nextYIces.peek(), nextXIces.peek() });
            int nextIceCnt = 0;
            visited[nextYIces.peek()][nextXIces.peek()] = true;

            while (!q.isEmpty()) {
                int[] points = q.poll();
                int y = points[0], x = points[1];

                nextIceCnt++;

                for (int i = 0; i < 4; i++) {
                    int ny = y + dy[i];
                    int nx = x + dx[i];

                    if (nextIcebergs[ny][nx] > 0 && !visited[ny][nx]) {
                        visited[ny][nx] = true;
                        q.add(new int[] { ny, nx });
                    }
                }
            }

            if (nextIceCnt != nextXIces.size()) {
                flag = false;
            }

            icebergs = nextIcebergs;
            yIces = nextYIces;
            xIces = nextXIces;
        }
        System.out.println(year);
    }

}
