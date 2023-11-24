import java.util.*;

class Solution {

    class Road {
        int s;
        int e;
        int v;
        int flag;

        Road(int s, int e, int v, int flag) {
            this.s = s;
            this.e = e;
            this.v = v;
            this.flag = flag;
        }
    }

    class Route implements Comparable<Route> {
        int cur;
        int time;
        int mazeState;

        Route(int cur, int time, int mazeState) {
            this.cur = cur;
            this.time = time;
            this.mazeState = mazeState;
        }

        @Override
        public int compareTo(Route o) {
            return this.time - o.time;
        }
    }

    public int isTrapActive(int mazeState, int trapIdx) {
        return 1 & (mazeState >> trapIdx);
    }

    public int activeTrap(int mazeState, int trapIdx) {
        return mazeState ^ (1 << trapIdx);
    }

    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        int answer = Integer.MAX_VALUE;

        List<List<Road>> graphs = new ArrayList<List<Road>>();
        for (int i = 0; i <= n; i++)
            graphs.add(new ArrayList<Road>());

        for (int[] road : roads) {
            graphs.get(road[0]).add(new Road(road[0], road[1], road[2], 0));
            graphs.get(road[1]).add(new Road(road[1], road[0], road[2], 1));
        }

        Map<Integer, Integer> trapIdxMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < traps.length; i++) {
            trapIdxMap.put(traps[i], i);
        }

        int[][] dp = new int[(int) Math.pow(2, traps.length)][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        PriorityQueue<Route> queue = new PriorityQueue<Route>();
        queue.add(new Route(start, 0, 0));

        while (!queue.isEmpty()) {
            Route route = queue.poll();
            if (route.cur == end) {
                answer = Math.min(answer, route.time);
                break;
            }
            for (Road road : graphs.get(route.cur)) {
                int nextState = route.mazeState;
                int curFlag;

                if (trapIdxMap.get(road.s) != null) {
                    if (trapIdxMap.get(road.e) != null) {
                        curFlag = isTrapActive(route.mazeState, trapIdxMap.get(road.s))
                                ^ isTrapActive(route.mazeState, trapIdxMap.get(road.e));
                        nextState = activeTrap(route.mazeState, trapIdxMap.get(road.e));

                    } else {
                        curFlag = isTrapActive(route.mazeState, trapIdxMap.get(road.s));
                    }
                } else {
                    if (trapIdxMap.get(road.e) != null) {
                        curFlag = isTrapActive(route.mazeState, trapIdxMap.get(road.e));
                        nextState = activeTrap(route.mazeState, trapIdxMap.get(road.e));
                    } else {
                        curFlag = 0;
                    }
                }

                if (road.flag == curFlag && route.time + road.v < dp[nextState][road.e]) {
                    dp[nextState][road.e] = route.time + road.v;
                    queue.add(new Route(road.e, route.time + road.v, nextState));
                }
            }

        }

        return answer;
    }
}