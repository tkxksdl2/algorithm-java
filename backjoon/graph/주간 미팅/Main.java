import java.io.*;
import java.util.*;

public class Main {
    static int n, v, e;
    static int a, b;
    static List<Integer> houses = new ArrayList<>();
    static int[][] distmap;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        distmap = new int[v+1][v+1];
        Arrays.stream(distmap).forEach(row -> Arrays.fill(row, Integer.MAX_VALUE));

        st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++){
            int houseIndex = Integer.parseInt(st.nextToken());
            houses.add(houseIndex);
        }

        for (int i = 0; i < e; i++){
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());
            distmap[start][end] = dist;
            distmap[end][start] = dist;
        }

        for (int start: new int[] { a, b }) {
            distmap[start][start] = 0;
            dijkstra(start);
        }
        for (int start: houses) {
            answer += distmap[start][a] < Integer.MAX_VALUE ? distmap[start][a] : -1;
            answer += distmap[start][b] < Integer.MAX_VALUE ? distmap[start][b] : -1;
        }
        System.out.println(answer);
    }

    private static void dijkstra(int start){
        Queue<Edge> q = new LinkedList<>();

        for (int end = 1; end <= v; end++){
            if (distmap[start][end] < Integer.MAX_VALUE){
                q.add(new Edge(start, end, distmap[start][end]));
            }
        }

        while(!q.isEmpty()) {
            Edge cur = q.poll();
            int mid = cur.end;

            if (distmap[start][mid] < cur.dist) continue;

            for (int end = 1; end <= v; end++) {
                if(distmap[mid][end] < Integer.MAX_VALUE
                        && cur.dist + distmap[mid][end] < distmap[start][end]) {
                    distmap[start][end] = cur.dist + distmap[mid][end];
                    distmap[end][start] = cur.dist + distmap[mid][end];

                    q.add(new Edge(start, end, cur.dist + distmap[mid][end]));
                }
            }
        }
    }

}

class Edge  {
    int start;
    int end;
    int dist;

    public Edge(int start, int end, int dist) {
        this.start = start;
        this.end = end;
        this.dist = dist;
    }
}