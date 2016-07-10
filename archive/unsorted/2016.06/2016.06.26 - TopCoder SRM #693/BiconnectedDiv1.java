package com.dumbear.chelper.workspace;

import java.util.Arrays;

public class BiconnectedDiv1 {
    private int[] w1;
    private int[] w2;
    private int[][] cache;

    public int minimize(int[] w1, int[] w2) {
        this.w1 = w1;
        this.w2 = w2;
        cache = new int[w1.length + 1][w1.length + 1];
        for (int[] c : cache) {
            Arrays.fill(c, -1);
        }
        int total = 0;
        for (int w : w1) {
            total += w;
        }
        for (int w : w2) {
            total += w;
        }
        return total - findMaxSum(0, w1.length);
    }

    private int findMaxSum(int x, int y) {
        if (cache[x][y] != -1) {
            return cache[x][y];
        }
        cache[x][y] = 0;
        for (int i = x + 2; i <= y - 2; ++i) {
            cache[x][y] = Math.max(cache[x][y], findMaxSum(x, i) + findMaxSum(i, y) + w2[i - 1]);
        }
        for (int i = x + 1; i < y - 1; ++i) {
            cache[x][y] = Math.max(cache[x][y], findMaxSum(x, i + 1) + findMaxSum(i, y) + w1[i]);
        }
        return cache[x][y];
    }
}
