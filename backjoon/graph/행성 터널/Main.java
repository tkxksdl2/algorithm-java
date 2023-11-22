import java.io.*;
import java.util.*;

public class Main {
    static int[] parents;

    static class Edge implements Comparable<Edge> {
        int s;
        int e;
        int v;

        Edge(int s, int e, int v) {
            this.s = s;
            this.e = e;
            this.v = v;
        }

        @Override
        public int compareTo(Edge e) {
            return v - e.v;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] x = new int[n][2], y = new int[n][2], z = new int[n][2];
        int[][][] coordinates = { x, y, z };
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int[][] c : coordinates)
                c[i] = new int[] { Integer.parseInt(st.nextToken()), i };
        }
        for (int[][] c : coordinates)
            Arrays.sort(c, (a, b) -> a[0] - b[0]);

        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        for (int i = 0; i < n - 1; i++) {
            for (int[][] c : coordinates) {
                int[] sData = c[i], eData = c[i + 1];
                edges.add(new Edge(sData[1], eData[1], Math.abs(sData[0] - eData[0])));
            }
        }

        parents = new int[n];
        for (int i = 0; i < n; i++)
            parents[i] = i;

        int ans = 0;

        while (!edges.isEmpty()) {
            Edge edge = edges.poll();

            int sp = getParents(edge.s);
            int ep = getParents(edge.e);

            if (sp != ep) {
                parents[Math.max(sp, ep)] = Math.min(sp, ep);

                ans += edge.v;
            }
        }
        System.out.println(ans);
    }

    static int getParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = getParents(parents[n]);
    }
}
