package com.dumbear.chelper.workspace;

import java.util.Arrays;

public class HardProof {
    public int minimumCost(int[] D) {
        int n = (int) Math.round(Math.sqrt(D.length));
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                cost[i][j] = D[i * n + j];
            }
        }
        int result = Integer.MAX_VALUE;
        Arrays.sort(D);
        for (int i = 0, j = 0; i < D.length; ++i) {
            while (j < D.length && !canProof(n, cost, D[i], D[j])) {
                ++j;
            }
            if (j < D.length && D[j] - D[i] < result) {
                result = D[j] - D[i];
            }
        }
        return result;
    }

    boolean canProof(int n, int[][] cost, int minCost, int maxCost) {
        boolean[][] g = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                g[i][j] = cost[i][j] >= minCost && cost[i][j] <= maxCost;
            }
        }
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (g[i][k] && g[k][j]) {
                        g[i][j] = true;
                    }
                }
            }
        }
        for (int i = 1; i < n; ++i) {
            if (!g[0][i] || !g[i][0]) {
                return false;
            }
        }
        return true;
    }
}
