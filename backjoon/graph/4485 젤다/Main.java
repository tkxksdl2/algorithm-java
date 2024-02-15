import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static List<Integer> answers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n > 0) {
                ProblemSolver solver = new ProblemSolver(br, n);
                solver.setCave();
                int answer = solver.solve();

                answers.add(answer);
            } else break;
        }

        for (int i=0; i<answers.size(); i++){
            System.out.printf("Problem %d: %d\n", i+1, answers.get(i));
        }

        br.close();
    }

}

class ProblemSolver {
    BufferedReader br;
    int n;
    int[][] cave;
    int[][] valueTable;
    int[] dy = {-1, 1, 0, 0};
    int[] dx = {0, 0, -1 , 1};

    public ProblemSolver(BufferedReader br, int n) {
        this.br = br;
        this.n = n;
    }

    public void setCave() throws IOException {
        cave = new int[n][n];

        for (int i = 0; i < n; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++){
                cave[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public int solve() {
        valueTable = new int[n][n];
        Arrays.stream(valueTable).forEach(row -> {
            Arrays.fill(row, Integer.MAX_VALUE);
        });

        PriorityQueue<MovingPoint> q = new PriorityQueue<>(Comparator.comparingInt(point -> point.curValue));
        q.add(new MovingPoint(0, 0, cave[0][0]));
        valueTable[0][0] = cave[0][0];

        while (!q.isEmpty()) {
            MovingPoint curPoint = q.poll();

            if (curPoint.isAt(n-1, n-1)) continue;

            for(int i = 0; i < 4; i++){
                int ny = curPoint.y + dy[i];
                int nx = curPoint.x + dx[i];

                if (ny >= 0 && ny < n && nx >= 0 && nx < n) {
                    int nextValue = curPoint.curValue + cave[ny][nx];

                    if (nextValue < valueTable[ny][nx]){
                        valueTable[ny][nx] = nextValue;
                        q.add(new MovingPoint(ny, nx, nextValue));
                    }
                }
            }
        }
        return valueTable[n-1][n-1];
    }

    class MovingPoint {
        int y;
        int x;
        int curValue;

        public MovingPoint(int y, int x, int curValue) {
            this.y = y;
            this.x = x;
            this.curValue = curValue;
        }

        public boolean isAt(int dy, int dx) {
            return dy == y && dx == x;
        }
    }
}