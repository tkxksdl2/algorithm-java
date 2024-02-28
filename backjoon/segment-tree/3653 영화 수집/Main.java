import java.io.*;
import java.util.*;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            FenwickTree tree = new FenwickTree(n, m);
            int[] indexArr = new int[n+1];

            for (int i = 1; i <= n; i++) {
                tree.update(i, 1);
                indexArr[n-i+1] = i;
            }

            st = new StringTokenizer(br.readLine());
            int top = n+1;

            for (int i = 0; i < m; i++){
                int book = Integer.parseInt(st.nextToken());
                int idx = indexArr[book];
                sb.append(tree.getUpperSum(idx)).append(" ");

                tree.update(idx, -1);
                tree.update(top, 1);
                indexArr[book] = top++;
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static class FenwickTree {
        int[] tree;

        public FenwickTree(int n, int m) {
            tree = new int[n+m+1];
        }

        public void update(int idx, int value){
            while (idx < tree.length) {
                tree[idx] += value;
                idx = idx + (idx & -idx);
            }
        }

        public int getUpperSum(int idx){
            return query(tree.length-1) - query(idx);
        }

        private int query(int left) {
            int value = 0;
            while (left > 0){
                value += tree[left];
                left = left - (left & -left);
            }
            return value;
        }
    }

}
