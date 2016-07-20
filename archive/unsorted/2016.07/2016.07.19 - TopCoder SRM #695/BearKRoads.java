package com.dumbear.chelper.workspace;

public class BearKRoads {
    private int[] x;
    private int[] a;
    private int[] b;
    private int K;
    private int[] visitedTimes;

    public int maxHappy(int[] x, int[] a, int[] b, int K) {
        this.x = x;
        this.a = a;
        this.b = b;
        this.K = K;
        visitedTimes = new int[x.length];
        return dfs(0);
    }

    private int dfs(int depth) {
        if (depth == K) {
            return 0;
        }
        int bestRoad = -1;
        int bestCount = -1;
        for (int i = 0; i < a.length; ++i) {
            int count = countCitizens(a[i], b[i]);
            if (count > bestCount) {
                bestCount = count;
                bestRoad = i;
            }
        }
        int result = 0;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == a[bestRoad] || a[i] == b[bestRoad] || b[i] == a[bestRoad] || b[i] == b[bestRoad]) {
                int count = countCitizens(a[i], b[i]);
                ++visitedTimes[a[i]];
                ++visitedTimes[b[i]];
                result = Math.max(result, count + dfs(depth + 1));
                --visitedTimes[a[i]];
                --visitedTimes[b[i]];
            }
        }
        return result;
    }

    private int countCitizens(int from, int to) {
        return (visitedTimes[from] == 0 ? x[from] : 0) + (visitedTimes[to] == 0 ? x[to] : 0);
    }
}
