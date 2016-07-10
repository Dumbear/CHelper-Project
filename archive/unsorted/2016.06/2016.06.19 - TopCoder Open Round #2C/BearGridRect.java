package com.dumbear.chelper;

import com.dumbear.chelper.library.graphtheory.MinCostFlowNetwork;

public class BearGridRect {
    public int[] findPermutation(int N, int[] r1, int[] r2, int[] c1, int[] c2, int[] cnt) {
        int m = r1.length;
        int total = N * 2 + m * 2 + 2;
        MinCostFlowNetwork g = new MinCostFlowNetwork(total);
        int from = total - 2, to = total - 1;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                int k = 0;
                while (k < m && !inside(i, j, r1[k], r2[k], c1[k], c2[k])) {
                    ++k;
                }
                if (k == m) {
                    g.insert(i, N + j, 1, 0);
                } else {
                    g.insert(i, N * 2 + k, 1, 0);
                    g.insert(N * 2 + m + k, N + j, 1, 0);
                }
            }
        }
        for (int i = 0; i < N; ++i) {
            g.insert(from, i, 1, 0);
            g.insert(N + i, to, 1, 0);
        }
        int sumCnt = 0;
        for (int i = 0; i < m; ++i) {
            sumCnt += cnt[i];
            g.insert(N * 2 + i, N * 2 + m + i, cnt[i], -1);
        }
        g.maxFlow(from, to);
        if (g.flow != N || g.cost != -sumCnt) {
            return new int[]{-1};
        }
        int[] columns = new int[N];
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; ++i) {
            for (MinCostFlowNetwork.Edge e : g.edges[i]) {
                if (e.c == 0 && e.v >= N && e.v < N * 2 && !visited[e.v - N]) {
                    visited[e.v - N] = true;
                    columns[i] = e.v - N;
                } else if (e.c == 0 && e.v >= N * 2 && e.v < N * 2 + m) {
                    for (MinCostFlowNetwork.Edge e2 : g.edges[e.v + m]) {
                        if (e2.c == 0 && e2.v >= N && e2.v < N * 2 && !visited[e2.v - N]) {
                            visited[e2.v - N] = true;
                            columns[i] = e2.v - N;
                            break;
                        }
                    }
                }
            }
        }
        return columns;
    }

    private boolean inside(int r, int c, int r1, int r2, int c1, int c2) {
        return r >= r1 && r <= r2 && c >= c1 && c <= c2;
    }
}
