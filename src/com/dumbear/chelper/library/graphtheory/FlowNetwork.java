package com.dumbear.chelper.library.graphtheory;

import java.util.ArrayList;
import java.util.List;

public class FlowNetwork {
    public static class Edge {
        public int v;
        public int c;
        public int b;

        public Edge(int v, int c, int b) {
            this.v = v;
            this.c = c;
            this.b = b;
        }
    }

    public int n;
    public List<Edge>[] edges;

    public FlowNetwork(int n) {
        this.n = n;
        edges = new List[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
    }

    public void insert(int u, int v, int c) {
        insert(u, v, c, true);
    }

    public void insert(int u, int v, int c, boolean directed) {
        edges[u].add(new Edge(v, c, edges[v].size()));
        edges[v].add(new Edge(u, (directed ? 0 : c), edges[u].size() - 1));
    }

    public int maxFlow(int from, int to) {
        int[] h = new int[n];
        int[] count = new int[n + 1];
        int flow = 0;
        while (h[from] < n) {
            flow += flowOnce(from, to, from, Integer.MAX_VALUE, h, count);
        }
        return flow;
    }

    private int flowOnce(int from, int to, int current, int flow, int[] h, int[] count) {
        if (current == to) {
            return flow;
        }
        int result = 0;
        int minH = n - 1;
        for (Edge e : edges[current]) {
            if (e.c == 0) {
                continue;
            }
            if (h[e.v] + 1 == h[current]) {
                int d = flowOnce(from, to, e.v, Math.min(e.c, flow), h, count);
                if (d != 0) {
                    e.c -= d;
                    edges[e.v].get(e.b).c += d;
                    flow -= d;
                    result += d;
                }
                if (h[from] >= n) {
                    return result;
                }
            }
            minH = Math.min(minH, h[e.v]);
            if (flow == 0) {
                break;
            }
        }
        if (result != 0) {
            return result;
        }
        if (--count[h[current]] == 0) {
            h[from] = n;
        }
        h[current] = minH + 1;
        ++count[h[current]];
        return 0;
    }
}
