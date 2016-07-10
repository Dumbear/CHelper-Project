package com.dumbear.chelper.workspace;

import java.util.ArrayList;
import java.util.List;

public class BipartiteConstruction {
    private static final int N = 20;
    private static final int BASE = 3;

    public int[] construct(int K) {
        List<Integer> edges = new ArrayList<>();
        for (int i = 0; i < N - 1; ++i) {
            for (int x = 0; x < BASE; ++x) {
                edges.add(encode(i, i));
            }
            edges.add(encode(i, i + 1));
        }
        for (int i = 0; i < N && K > 0; ++i) {
            for (int x = 0; x < K % BASE; ++x) {
                edges.add(encode(N - 1, i));
            }
            K /= BASE;
        }
        int[] result = new int[edges.size() + 1];
        result[0] = N;
        for (int i = 0; i < edges.size(); ++i) {
            result[i + 1] = edges.get(i);
        }
        return result;
    }

    private int encode(int x, int y) {
        return x * N + y;
    }
}
