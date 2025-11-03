package graph.dagsp;

import graph.model.Edge;
import graph.model.Graph;
import util.Metrics;

import java.util.*;

public class DagShortestPaths {
    public static class Result {
        public final double[] dist;
        public final int[] parent;
        public Result(double[] d, int[] p) { dist = d; parent = p; }
    }

    public static Result shortestPaths(Graph dag, List<Integer> topo, int source, Metrics metrics) {
        double[] dist = new double[dag.n];
        int[] parent = new int[dag.n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        for (int u : topo) {
            if (dist[u] == Double.POSITIVE_INFINITY) continue;
            for (Edge e : dag.adj.get(u)) {
                double nd = dist[u] + e.w;
                if (nd < dist[e.v]) {
                    dist[e.v] = nd;
                    parent[e.v] = u;
                    metrics.incRelaxations();
                }
            }
        }
        return new Result(dist, parent);
    }

    public static List<Integer> reconstructPath(int[] parent, int s, int t) {
        List<Integer> path = new ArrayList<>();
        for (int cur = t; cur != -1; cur = parent[cur]) path.add(cur);
        Collections.reverse(path);
        if (path.isEmpty() || path.get(0) != s) return List.of();
        return path;
    }
}
