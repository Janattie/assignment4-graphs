package graph.dagsp;

import graph.model.Edge;
import graph.model.Graph;

import java.util.*;

public class DagLongestPath {
    public static class Result {
        public final double maxLen;
        public final List<Integer> path;
        public Result(double m, List<Integer> p) { maxLen = m; path = p; }
    }

    public static Result longestPath(Graph dag, List<Integer> topo) {
        double[] best = new double[dag.n];
        int[] parent = new int[dag.n];
        Arrays.fill(best, Double.NEGATIVE_INFINITY);
        Arrays.fill(parent, -1);

        int[] indeg = new int[dag.n];
        for (var e : dag.edges) indeg[e.v]++;
        for (int i = 0; i < dag.n; i++) if (indeg[i] == 0) best[i] = 0;

        for (int u : topo) {
            if (best[u] == Double.NEGATIVE_INFINITY) continue;
            for (Edge e : dag.adj.get(u)) {
                double nd = best[u] + e.w;
                if (nd > best[e.v]) { best[e.v] = nd; parent[e.v] = u; }
            }
        }

        double mx = Double.NEGATIVE_INFINITY; int end = -1;
        for (int i = 0; i < dag.n; i++) if (best[i] > mx) { mx = best[i]; end = i; }

        List<Integer> path = new ArrayList<>();
        if (end != -1 && mx > Double.NEGATIVE_INFINITY) {
            for (int cur = end; cur != -1; cur = parent[cur]) path.add(cur);
            Collections.reverse(path);
        }
        return new Result(mx, path);
    }
}
