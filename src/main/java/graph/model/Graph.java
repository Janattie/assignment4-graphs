package graph.model;

import java.util.*;

public class Graph {
    public final int n;
    public final boolean directed;
    public final List<Edge> edges;
    public final List<List<Edge>> adj;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        this.edges = new ArrayList<>();
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, double w) {
        Edge e = new Edge(u, v, w);
        edges.add(e);
        adj.get(u).add(e);
        if (!directed) {
            Edge e2 = new Edge(v, u, w);
            edges.add(e2);
            adj.get(v).add(e2);
        }
    }

    public static Graph buildCondensation(Graph g, List<List<Integer>> sccs) {
        int compCount = sccs.size();
        int[] compId = new int[g.n];
        Arrays.fill(compId, -1);
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) compId[v] = i;
        }
        Graph dag = new Graph(compCount, true);
        Set<Long> seen = new HashSet<>();
        for (Edge e : g.edges) {
            int cu = compId[e.u];
            int cv = compId[e.v];
            if (cu != cv) {
                long key = (((long) cu) << 32) ^ (cv & 0xffffffffL);
                if (!seen.contains(key)) {
                    seen.add(key);
                    dag.addEdge(cu, cv, e.w);
                }
            }
        }
        return dag;
    }

    public static int whichComponent(int v, List<List<Integer>> sccs) {
        for (int i = 0; i < sccs.size(); i++) {
            for (int x : sccs.get(i)) if (x == v) return i;
        }
        return -1;
    }
}
