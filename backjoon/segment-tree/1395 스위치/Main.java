import java.io.*;
import java.util.*;

public class Main {
    static List<Integer> answers = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        SegmentTree tree = new SegmentTree(n+1);
        tree.build(1, n, 1);

        for (int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            if (order == 0)
                tree.convert(start, end, 1, n, 1);
            if (order == 1)
                answers.add(tree.query(start, end, 1, n, 1));
        }

        for (int ans : answers)
            System.out.println(ans);

        br.close();
    }


    static class SegmentTree {
        int[] tree;
        int[] convertCount;

        public SegmentTree(int n) {
            this.tree = new int[4*n];
            this.convertCount = new int[4*n];
        }

        private void build(int left, int right, int treeIdx) {
            if (left == right)
                return;

            int mid = (left + right) / 2;
            build(left, mid, treeIdx*2);
            build(mid+1, right, treeIdx*2 + 1);
            tree[treeIdx] = tree[treeIdx*2] + tree[treeIdx*2 + 1];

        }

        private int query(int left, int right, int tLeft, int tRight, int treeIdx) {
            lazyConvert(treeIdx, tLeft, tRight);

            if (left > tRight || right < tLeft) return 0;

            if (left <= tLeft && tRight <= right) {
                return tree[treeIdx];
            };

            int tMid = (tLeft + tRight) / 2;
            return query(left, right, tLeft, tMid, treeIdx * 2)
                    + query(left, right, tMid+1, tRight, treeIdx * 2 + 1);
        }

        private void convert(int left, int right, int tLeft, int tRight, int treeIdx) {
            lazyConvert(treeIdx, tLeft, tRight);
            if (left > tRight || right < tLeft) return;

            if (left <= tLeft && tRight <= right){
                convertCount[treeIdx]++;
                lazyConvert(treeIdx, tLeft, tRight);
                return;
            }

            int tMid = (tLeft + tRight) / 2;
            convert(left, right, tLeft, tMid, treeIdx * 2);
            convert(left, right, tMid+1, tRight, treeIdx * 2 + 1);

            tree[treeIdx] = tree[treeIdx*2] + tree[treeIdx*2 + 1];
        }

        private void lazyConvert(int treeIdx, int tLeft, int tRight) {
            if (convertCount[treeIdx] == 0) return;

            if (convertCount[treeIdx] % 2 == 1) {
                tree[treeIdx] = (tRight - tLeft + 1) - tree[treeIdx];
            }
            if (tLeft != tRight){
                convertCount[treeIdx * 2] += convertCount[treeIdx];
                convertCount[treeIdx * 2 + 1] += convertCount[treeIdx];
            }
            convertCount[treeIdx] = 0;
        }
    }


}