import java.io.*;
import java.util.*;

public class Main {
    static List<Integer> answer = new ArrayList<>();
    static int[] numbers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        numbers = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<n; i++)
            numbers[i] = Integer.parseInt(st.nextToken());

        MaxSegmentTree tree = new MaxSegmentTree(numbers);

        int [] s = tree.tree;
        int left = 0;
        int right = left + 2*m - 2;
        while(right < n) {
            answer.add(tree.query(left++, right++, 0, n-1, 1));
        }


        StringJoiner joiner = new StringJoiner(" ");
        for (int num : answer) joiner.add(String.valueOf(num));

        System.out.println(joiner.toString());
    }
}

class MaxSegmentTree {
    int[] tree;
    int[] numbers;
    public MaxSegmentTree(int[] numbers) {
        this.numbers = numbers;
        this.tree = new int[4*numbers.length];

        build(0,this.numbers.length-1, 1);
    }

    private void build(int left, int right, int idx){
        if (left == right) {
            tree[idx] = numbers[left];
            return ;
        }

        int mid = (left + right) / 2;
        build(left, mid, idx*2);
        build(mid+1, right, idx*2 +1);
        tree[idx] = Math.max(tree[idx * 2], tree[idx * 2 + 1]);
    }

    public int query(int left, int right, int treeL, int treeR, int idx){
        if (left > treeR || right < treeL) return 0;

        if (treeL >= left && treeR <= right) return tree[idx];

        int mid = (treeL + treeR) / 2;
        return Math.max(query(left, right, treeL, mid, idx * 2)
                ,query(left, right, mid+1, treeR, idx * 2+1));
    }
}