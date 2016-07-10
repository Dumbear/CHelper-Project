package com.dumbear.chelper.workspace;

import com.dumbear.chelper.library.graphtheory.FlowNetwork;

public class SRMDiv0Easy {
    public int get(int N, int[] L, int[] R, int[] X, int[] Y) {
        FlowNetwork g = new FlowNetwork(N + 3);
        int[] delta = new int[N + 1];
        for (int i = 0; i < L.length; ++i) {
            g.insert(L[i], R[i] + 1, Y[i] - X[i]);
            delta[L[i]] -= X[i];
            delta[R[i] + 1] += X[i];
        }
        int in = 0, out = 0;
        for (int i = 0; i <= N; ++i) {
            if (delta[i] > 0) {
                in += delta[i];
                g.insert(N + 1, i, delta[i]);
            }
            if (delta[i] < 0) {
                out -= delta[i];
                g.insert(i, N + 2, -delta[i]);
            }
        }
        g.insert(N, 0, (int) 1e9);
        if (in != out || g.maxFlow(N + 1, N + 2) != in) {
            return -1;
        }
        return g.maxFlow(0, N);
    }
}
