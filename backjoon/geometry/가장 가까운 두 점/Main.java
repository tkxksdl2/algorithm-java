import java.io.*;
import java.util.*;

public class Main {
    static Point[] points;

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getDistance(Point p) {
            return (int) (Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
        }

        int getXDist(Point p) {
            return (int) Math.pow(this.x - p.x, 2);
        }

        int getYDist(Point p) {
            return (int) Math.pow(this.y - p.y, 2);
        }
    }

    static Comparator<Point> xComparator = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            return p1.x - p2.x;
        }
    };

    static Comparator<Point> yComparator = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            return p1.y - p2.y;
        }
    };

    public static void main(String[] args) throws IOException {
        int minDistance;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        points = new Point[n];
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            points[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(points, xComparator);
        minDistance = getMinDistance(0, n);

        System.out.println(minDistance);
    }

    static int getMinDistance(int s, int e) {
        int m = (s + e) / 2;
        int min;

        if (e - s <= 3)
            return brute(s, e);

        int left = getMinDistance(s, m);
        int right = getMinDistance(m + 1, e);

        min = Math.min(left, right);

        List<Point> candidate = new ArrayList<Point>();
        makeAndSortCandidate(candidate, s, m, left, min);

        for (int i = 0; i < candidate.size() - 1; i++) {
            for (int j = i + 1; j < candidate.size(); j++) {
                Point p1 = candidate.get(i);
                Point p2 = candidate.get(j);

                if (p1.getYDist(p2) < min) {
                    min = Math.min(min, p1.getDistance(p2));
                } else
                    break;
            }
        }

        return min;
    }

    private static int brute(int s, int e) {
        int min = Integer.MAX_VALUE;
        for (int i = s; i < e; i++) {
            for (int j = i + 1; j < e; j++) {
                Point p1 = points[i];
                Point p2 = points[j];
                min = Math.min(min, p1.getDistance(p2));
            }
        }
        return min;
    }

    private static void makeAndSortCandidate(List<Point> candidate, int s, int m, int e, int min) {
        candidate.add(points[m]);

        for (int i = 1; m - i >= s; i++) {
            Point p = points[m - i];
            if (p.getXDist(points[m]) < min) {
                candidate.add(p);
            } else
                break;
        }

        for (int i = 1; m + i < e; i++) {
            Point p = points[m + i];
            if (p.getXDist(points[m]) < min) {
                candidate.add(p);
            } else
                break;
        }

        Collections.sort(candidate, yComparator);
    }
}
