package com.dumbear.chelper.workspace;

import java.util.ArrayList;
import java.util.List;

public class TreeSums {
    static final int MODULO = 1000000000;

    static class Node {
        int v;
        int l;

        public Node(int v, int l) {
            this.v = v;
            this.l = l;
        }
    }

    List<Node>[] g;
    Node[] parent;
    int[] nChildren;
    long[] lengthToRoot;
    long[] sumSubtree;
    long[] sum;
    int[] base;

    public long findSums(int N, int seed, int C, int D) {
        generateTree(N, seed, C, D);
        parent = new Node[N];
        nChildren = new int[N];
        lengthToRoot = new long[N];
        sumSubtree = new long[N];
        sum = new long[N];
        base = new int[N];
        findSums(0, -1);
        long result = 0;
        for (int i = 0; i < N; ++i) {
            result ^= sum[i];
        }
        return result;
    }

    void generateTree(int n, int seed, int c, int d) {
        g = (List<Node>[]) new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            g[i] = new ArrayList<>();
        }
        long k = seed;
        for (int i = 0; i < n - 1; ++i) {
            k = (c * k + d) % MODULO;
            int v = (int) (k % (i + 1));
            k = (c * k + d) % MODULO;
            int length = (int) (k % 1000000);
            g[v].add(new Node(i + 1, length));
            g[i + 1].add(new Node(v, length));
        }
    }

    void findSums(int v, int p) {
        nChildren[v] = 1;
        sumSubtree[v] = 0;
        for (Node u : g[v]) {
            if (u.v == p) {
                continue;
            }
            parent[u.v] = new Node(v, u.l);
            lengthToRoot[u.v] = lengthToRoot[v] + u.l;
            findSums(u.v, v);
            nChildren[v] += nChildren[u.v];
            sumSubtree[v] += sumSubtree[u.v] + (long) nChildren[u.v] * u.l;
        }
        sum[v] = sumSubtree[v];
        base[v] = v;
        for (Node u : g[v]) {
            if (u.v == p) {
                continue;
            }
            long currentSum = sum[u.v];
            currentSum += (lengthToRoot[base[u.v]] - lengthToRoot[v]) * (nChildren[v] - nChildren[u.v]);
            currentSum += sumSubtree[v] - sumSubtree[u.v] - (long) nChildren[u.v] * u.l;
            for (int k = base[u.v]; k != v; k = parent[k].v) {
                if (currentSum < sum[v]) {
                    sum[v] = currentSum;
                    base[v] = k;
                }
                if (nChildren[k] >= nChildren[v] - nChildren[k]) {
                    break;
                }
                currentSum += (long) nChildren[k] * parent[k].l;
                currentSum -= (long) (nChildren[v] - nChildren[k]) * parent[k].l;
            }
        }
    }
}
