import java.io.*;
import java.util.*;

class Main {
    static int MAX_LEN = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        SegmentTree tree = new SegmentTree();
        StringBuilder sb = new StringBuilder();

        while (n-- >0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int o = Integer.parseInt(st.nextToken());

            if (o == 1){
                int target = Integer.parseInt(st.nextToken());
                sb.append(tree.find(target, 1, 1, MAX_LEN)).append("\n");
            } else {
                int num = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                tree.update(num, value, 1, 1, MAX_LEN);
            }
        }

        System.out.println(sb);
    }

    static class SegmentTree {
        int[] tree = new int[4*MAX_LEN];

        public SegmentTree() {
        }

        public int find(int target, int idx, int tLeft, int tRight) {
            tree[idx]--;

            if (tLeft == tRight)
                return tRight;

            int leftChild = tree[idx*2];

            if (target <= leftChild)
                return find(target, idx*2, tLeft, (tLeft + tRight) / 2);
            else
                return find(target-leftChild, idx*2+1, (tLeft + tRight) / 2 + 1, tRight);
        }

        public void update(int number, int value, int idx, int tLeft, int tRight) {
            if (number < tLeft || number > tRight) return;

            if (tLeft == tRight) {
                tree[idx] += value;
                return;
            }

            int mid = (tLeft + tRight ) / 2;
            update(number, value, idx*2, tLeft, mid);
            update(number, value, idx*2+1, mid+1, tRight);

            tree[idx] = tree[idx*2] + tree[idx*2+1];
        }
    }
}