import java.util.ArrayList;
import java.util.List;

public class Solution {
    static Employee[] employees;
    static int[][] dp;

    class Employee {
        int sale;

        List<Integer> child;

        Employee(int sale) {
            this.sale = sale;
            this.child = new ArrayList<Integer>();
        }
    }

    public int solution(int[] sales, int[][] links) {
        employees = new Employee[sales.length + 1];
        dp = new int[employees.length][2];
        for (int i = 0; i < sales.length; i++)
            employees[i + 1] = new Employee(sales[i]);

        for (int[] link : links) {
            Employee parents = employees[link[0]];
            parents.child.add(link[1]);
        }

        dfs(1);

        return Math.min(dp[1][0], dp[1][1]);
    }

    public void dfs(int employeeIdx) {
        Employee employee = employees[employeeIdx];
        if (employee.child.isEmpty()) {
            dp[employeeIdx][1] = employee.sale;
            return;
        }

        for (int nextIdx : employee.child)
            dfs(nextIdx);

        int minChildSaleSum = 0;
        int minSaleDiff = Integer.MAX_VALUE;
        for (int childIdx : employee.child) {
            minChildSaleSum += Math.min(dp[childIdx][0], dp[childIdx][1]);
            minSaleDiff = Math.min(minSaleDiff, dp[childIdx][1] - dp[childIdx][0]);
        }

        dp[employeeIdx][1] = minChildSaleSum + employee.sale;
        dp[employeeIdx][0] = minChildSaleSum + Math.max(0, minSaleDiff);

        return;
    }
}
