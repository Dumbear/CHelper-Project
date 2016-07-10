package com.dumbear.chelper.workspace;

import java.util.Arrays;

public class Moneymanager {
    static final int MAX_N = 50;
    static final int MAX_B = 10;
    static final int MAX_HALF_SUM_B = MAX_N / 2 * MAX_B;

    public int getbest(int[] a, int[] b, int X) {
        Integer[] order = getOrder(a, b);
        int result = 0;
        int[][][] maxMoney = new int[a.length + 1][a.length / 2 + 1][MAX_HALF_SUM_B + 1];
        for (int sumB = 1; sumB <= MAX_HALF_SUM_B; ++sumB) {
            for (int[][] x : maxMoney) {
                for (int[] y : x) {
                    Arrays.fill(y, -1);
                }
            }
            maxMoney[0][0][0] = 0;
            int totalSumB = 0;
            for (int i = 0; i < a.length; ++i) {
                int k = order[i];
                totalSumB += b[k];
                for (int j = 0; j <= i && j <= a.length / 2; ++j) {
                    for (int currentSumB = 0; currentSumB <= sumB; ++currentSumB) {
                        if (maxMoney[i][j][currentSumB] == -1) {
                            continue;
                        }
                        int money1 = maxMoney[i][j][currentSumB] + a[k] * sumB + a[k] * (totalSumB - currentSumB);
                        maxMoney[i + 1][j][currentSumB] = Math.max(maxMoney[i + 1][j][currentSumB], money1);
                        if (j + 1 > a.length / 2 || currentSumB + b[k] > sumB) {
                            continue;
                        }
                        int money2 = maxMoney[i][j][currentSumB] + a[k] * (currentSumB + b[k]);
                        maxMoney[i + 1][j + 1][currentSumB + b[k]] = Math.max(maxMoney[i + 1][j + 1][currentSumB + b[k]], money2);
                    }
                }
            }
            if (maxMoney[a.length][a.length / 2][sumB] != -1) {
                result = Math.max(result, maxMoney[a.length][a.length / 2][sumB] + X * sumB);
            }
        }
        return result;
    }

    Integer[] getOrder(int[] a, int[] b) {
        int n = a.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; ++i) {
            order[i] = i;
        }
        Arrays.sort(order, (x, y) -> a[x] * b[y] - a[y] * b[x]);
        return order;
    }
}
