import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static int sy, sx, ey, ex;
    static int[][] treeDistMap;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        sy = 0; sx = 0; ey = 0; ex = 0;

        treeDistMap = new int[n][m];
        Arrays.stream(treeDistMap).forEach(row -> {
            Arrays.fill(row, Integer.MAX_VALUE);
        });

        Queue<Point> q1 = new LinkedList<>();

        for(int i = 0; i < n; i++) {
            String row = br.readLine();
            for(int j = 0; j < m; j++) {

                char s = row.charAt(j);
                if (s == '+') {
                    q1.add(new Point(i,j,0));
                    treeDistMap[i][j] = 0;
                } else if (s == 'V'){
                    sy = i; sx = j;
                } else if (s == 'J'){
                    ey = i; ex = j;
                }
            }
        }

        while(!q1.isEmpty()) {
            Point cur = q1.poll();
            int nextDist = cur.getMinDist() + 1;

            for (int di = 0; di < 4; di++){
                int ny = cur.getY() + dy[di];
                int nx = cur.getX() + dx[di];

                if (isInIndexBound(ny,nx) && treeDistMap[ny][nx] > nextDist){

                    treeDistMap[ny][nx] = nextDist;
                    q1.add(new Point(ny, nx, nextDist));
                }
            }
        }

        PriorityQueue<Point> q2 = new PriorityQueue<>();
        q2.add(new Point(sy, sx, treeDistMap[sy][sx]));
        visited = new boolean[n][m];
        visited[sy][sx] = true;

        while(!q2.isEmpty()) {
            Point cur = q2.poll();

            for (int di = 0; di < 4; di++){
                int ny = cur.getY() + dy[di];
                int nx = cur.getX() + dx[di];
                int curMinDist = cur.getMinDist();

                if (isInIndexBound(ny,nx) && !visited[ny][nx]) {
                    int nextMinDist = Math.min(curMinDist, treeDistMap[ny][nx]);
                    if (ny == ey && nx == ex) {
                        System.out.println(nextMinDist);
                        return;
                    }

                    visited[ny][nx] = true;
                    q2.add(new Point(ny, nx, nextMinDist));
                }
            }
        }
    }

    private static boolean isInIndexBound(int ny, int nx) {
        return ny >= 0 && ny < n && nx >= 0 && nx < m;
    }
}

class Point implements Comparable<Point> {
    int y, x, minDist;

    public Point(int y, int x, int minDist) {
        this.y = y;
        this.x = x;
        this.minDist = minDist;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getMinDist() {
        return minDist;
    }

    @Override
    public int compareTo(Point o) {
        return Integer.compare(o.minDist, this.minDist);
    }
}