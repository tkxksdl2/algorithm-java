import java.io.*;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long ans = 0;

        Stack<StackNumber> stack = new Stack<>();

        while(n-- > 0){
            StackNumber num = new StackNumber(Integer.parseInt(br.readLine()), 1);

            while(!stack.isEmpty() && stack.peek().n <= num.n){
                StackNumber top = stack.pop();
                ans += top.count;

                if (top.n == num.n) {
                    num.count += top.count;
                    break;
                }
            }

            stack.add(num);
            ans += stack.size() >= 2 ? 1 : 0;
        }
        System.out.println(ans);
    }
}

class StackNumber {
    int n;
    int count;

    public StackNumber(int n, int count) {
        this.n = n;
        this.count = count;
    }
}
