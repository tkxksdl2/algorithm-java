import java.io.*;
import java.util.*;

class Main {
    static int[] indexArr;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        indexArr = new int[n];

        Map<Integer, Integer> indexMap = new HashMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++){
            int key = Integer.parseInt(st.nextToken());
            indexMap.put(key, i);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int key = Integer.parseInt(st.nextToken());
            indexArr[indexMap.get(key)] = i;
        }

        SegmentTree tree = new SegmentTree(n);
        long count = 0;
        for (int idx : indexArr) {
            int a = tree.countSmallerNum(idx, 0, n-1, 1);
            count += a;
            tree.updateToZero(idx,0, n-1, 1);
        }
        System.out.println(count);
    }

    static class SegmentTree {
        int[] tree;

        public SegmentTree(int n) {
            this.tree = new int[4*n];
            build(0, n-1, 1);
        }

        public void build(int left, int right, int node) {
            if (left == right) {
                tree[node] = 1;
                return;
            }

            build(left, (left + right) / 2, node * 2);
            build((left + right) / 2 + 1, right, node * 2 + 1);
            tree[node] = tree[node*2] + tree[node*2+1];
        }

        public int countSmallerNum(int target, int left, int right, int node) {
            if (target <= left) return 0;

            if (right < target) return tree[node];

            return countSmallerNum(target, left, (left+right) /2 , node * 2)
                    + countSmallerNum(target, (left+right) /2 + 1, right , node * 2 + 1);
        }

        public void updateToZero(int target, int left, int right, int node) {
            if (target < left || right < target) return;

            if (left == target && left == right) {
                tree[node] = 0;
                return;
            }

            updateToZero(target, left, (left+right)/2, node*2);
            updateToZero(target, (left+right) /2 + 1, right, node*2 + 1);
            tree[node] = tree[node*2] + tree[node*2+1];
        }
    }

}
