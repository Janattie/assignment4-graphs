package graph.topo;

import graph.model.Edge;
import graph.model.Graph;
import util.Metrics;

import java.util.*;

public class TopologicalSort {
    public static List<Integer> kahn(Graph dag, Metrics metrics) {
        int[] indeg = new int[dag.n];
        for (Edge e : dag.edges) indeg[e.v]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < dag.n; i++) if (indeg[i] == 0) { q.add(i); metrics.incKahnPushes(); }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove(); metrics.incKahnPops();
            order.add(u);
            for (Edge e : dag.adj.get(u)) {
                int v = e.v;
                if (--indeg[v] == 0) { q.add(v); metrics.incKahnPushes(); }
            }
        }
        if (order.size() != dag.n) throw new IllegalStateException("Graph is not a DAG");
        return order;
    }
}
