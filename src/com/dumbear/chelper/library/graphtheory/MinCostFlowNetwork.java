package com.dumbear.chelper.library.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinCostFlowNetwork {
    public static class Edge {
        public int v;
        public int c;
        public int w;
        public int b;

        public Edge(int v, int c, int w, int b) {
            this.v = v;
            this.c = c;
            this.w = w;
            this.b = b;
        }
    }

    public int n;
    public List<Edge>[] edges;
    public Edge[] path;
    public int flow;
    public int cost;

    public MinCostFlowNetwork(int n) {
        this.n = n;
        edges = new List[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        path = new Edge[n];
    }

    public void insert(int u, int v, int c, int w) {
        insert(u, v, c, w, true);
    }

    public void insert(int u, int v, int c, int w, boolean directed) {
        edges[u].add(new Edge(v, c, w, edges[v].size()));
        edges[v].add(new Edge(u, 0, -w, edges[u].size() - 1));
        if (!directed) {
            edges[v].add(new Edge(u, c, w, edges[u].size()));
            edges[u].add(new Edge(v, 0, -w, edges[v].size() - 1));
        }
    }

    public void maxFlow(int from, int to) {
        flow = 0;
        cost = 0;
        while (true) {
            int f = flowOnce(from, to);
            if (f == 0) {
                break;
            }
            flow += f;
            for (int v = to; v != from; v = edges[path[v].v].get(path[v].b).v) {
                cost += path[v].w * f;
                path[v].c -= f;
                edges[path[v].v].get(path[v].b).c += f;
            }
        }
    }

    private int flowOnce(int from, int to) {
        int[] d = new int[n], bfs = new int[n], hash = new int[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        hash[from] = 1;
        d[from] = 0;
        bfs[0] = from;
        for (int s = 0, t = 1; s != t; hash[bfs[s]] = 0, s = (s + 1) % n) {
            int v = bfs[s];
            for (Edge edge : edges[v]) {
                if (edge.c > 0 && d[v] + edge.w < d[edge.v]) {
                    d[edge.v] = d[v] + edge.w;
                    path[edge.v] = edge;
                    if (hash[edge.v] == 0) {
                        hash[edge.v] = 1;
                        bfs[t] = edge.v;
                        t = (t + 1) % n;
                    }
                }
            }
        }
        if(d[to] == Integer.MAX_VALUE) {
            return 0;
        }
        int f = Integer.MAX_VALUE;
        for (int v = to; v != from; v = edges[path[v].v].get(path[v].b).v) {
            if (path[v].c < f) {
                f = path[v].c;
            }
        }
        return f;
    }
}
