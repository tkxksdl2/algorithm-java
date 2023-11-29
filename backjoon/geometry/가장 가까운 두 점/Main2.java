import java.util.*;
import java.io.*;

public class Main2 {
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
            if (p1.y == p2.y) {
                return p1.x - p2.x;
            }
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

        minDistance = points[0].getDistance(points[1]);
        SortedSet<Point> treeSet = new TreeSet<Point>(yComparator);
        treeSet.add(points[0]);
        treeSet.add(points[1]);

        int low = 0;
        for (int i = 2; i < n; i++) {
            Point mainPoint = points[i];

            while (low < i) {
                Point subPoint = points[low];

                if (mainPoint.getXDist(subPoint) >= minDistance) {
                    treeSet.remove(subPoint);
                    low++;
                } else
                    break;
            }

            int acceptedYDist = (int) Math.ceil(Math.sqrt(minDistance));

            SortedSet<Point> ySubSet = treeSet.subSet(new Point(-100000, mainPoint.y - acceptedYDist),
                    new Point(100000, mainPoint.y + acceptedYDist));

            for (Point subPoint : ySubSet) {
                minDistance = Math.min(minDistance, mainPoint.getDistance(subPoint));
            }

            treeSet.add(mainPoint);
        }

        System.out.println(minDistance);
    }
}
