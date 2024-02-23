import java.io.*;
import java.util.*;

public class Main {
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static char[][] map;
    static int[][] intMap;
    static List<Point> swans = new ArrayList<>();
    static int y, x;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        map = new char[y][x];
        intMap = new int[y][x];

        for (int i = 0; i < y; i++) {
            String row = br.readLine();
            for(int j = 0; j < x; j++){
                char c = row.charAt(j);
                if (c == 'L'){
                    swans.add(new Point(i, j, 0));
                    c = '.';
                }else if (c == 'X')
                    intMap[i][j] = Integer.MAX_VALUE;

                map[i][j] = c;
            }
        }

        setIntMap();
        System.out.println(findPath(swans.get(0), swans.get(1)));
    }

    private static void setIntMap() {
        Queue<Point> q = new LinkedList<>();

        addContourPointsToQueue(q);

        while(!q.isEmpty()) {
            Point cur = q.poll();

            for (int d = 0; d < 4; d++){
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];
                int nDist = cur.dist + 1;

                if (isInBound(ny, nx) && map[ny][nx] == 'X' && nDist < intMap[ny][nx]) {
                    intMap[ny][nx] = nDist;
                    q.add(new Point(ny,nx,nDist));
                }
            }
        }
    }

    private static void addContourPointsToQueue(Queue<Point> q) {
        for (int cy = 0; cy < y; cy++) {
            for (int cx =0; cx < x; cx++) {
                if (map[cy][cx] != 'X') continue;

                for (int d = 0; d < 4; d++){
                    int ny = cy + dy[d];
                    int nx = cx + dx[d];
                    if (isInBound(ny, nx) && map[ny][nx] == '.'){
                        q.add(new Point(cy,cx, 1));
                        intMap[cy][cx] = 1;
                        break;
                    }
                }
            }
        }
    }

    private static int findPath(Point start, Point end) {
        int[][] minDistMap = new int[y][x];
        Arrays.stream(minDistMap).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));

        PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(point -> point.dist));
        minDistMap[start.y][start.x] = 0;
        pq.add(start);

        while(!pq.isEmpty()) {
            Point cur = pq.poll();

            if (cur.y == end.y && cur.x == end.x) return cur.dist;

            for (int d = 0; d < 4; d++){
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if (isInBound(ny, nx)) {
                    int nDist = Math.max(cur.dist, intMap[ny][nx]);

                    if (nDist < minDistMap[ny][nx]) {
                        minDistMap[ny][nx] = nDist;
                        pq.add(new Point(ny, nx, nDist));
                    }
                }
            }
        }

        return 0;
    }

    private static boolean isInBound(int ny, int nx) {
        return ny>=0 && nx>=0 && ny<y && nx<x;
    }
}

class Point {
    int y;
    int x;
    int dist;

    public Point(int y, int x, int dist) {
        this.y = y;
        this.x = x;
        this.dist = dist;
    }
}
