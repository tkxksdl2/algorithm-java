import java.util.*;

public class Main {
    static long nums[];

    static String numsToKey(long a, long b) {
        long min = Math.min(a, b);
        long max = Math.max(a, b);
        return Long.toString(min) + "-" + Long.toString(max);
    }

    static long[] keyToNums(String key) {
        return Arrays.stream(key.split("-")).mapToLong(Long::parseLong).toArray();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();

        long ans = 0;
        nums = Arrays.stream(sc.nextLine().split(" "))
                .mapToLong(Long::parseLong).toArray();

        long sum = Arrays.stream(nums).sum();
        Map<Long, Long> count = new HashMap<Long, Long>();
        for (long num : nums) {
            if (count.containsKey(num)) {
                count.put(num, count.get(num) + 1);
            } else
                count.put(num, (long) 1);
        }

        Arrays.sort(nums);
        Set<String> numberComb = new HashSet<String>();

        long target = nums[nums.length - 1];

        for (int j = nums.length - 2; j > 0; j--) {
            long a = nums[j];
            long b = sum - target * 2 - a;
            if (count.containsKey(b))
                numberComb.add(numsToKey(a, b));
        }

        target = nums[nums.length - 2];
        long a = nums[nums.length - 1];
        long b = sum - target * 2 - a;
        if (count.containsKey(b))
            numberComb.add(numsToKey(a, b));

        b = target;
        target = nums[nums.length - 3];
        if (sum - a - b == target * 2)
            numberComb.add(numsToKey(a, b));

        for (String s : numberComb) {
            long[] comb = keyToNums(s);
            a = comb[0];
            b = comb[1];
            ans += a == b ? count.get(a) * (count.get(b) - 1) / 2 : count.get(a) * count.get(b);
        }

        System.out.println(ans);
    }
}