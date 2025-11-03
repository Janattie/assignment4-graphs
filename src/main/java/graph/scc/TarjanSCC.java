package graph.scc;

import graph.model.Graph;
import graph.model.Edge;
import util.Metrics;

import java.util.*;

public class TarjanSCC {
    private final Graph g;
    private final Metrics metrics;

    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] inStack;
    private final Deque<Integer> st = new ArrayDeque<>();
    private final List<List<Integer>> result = new ArrayList<>();

    public TarjanSCC(Graph g, Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
        this.disc = new int[g.n];
        this.low = new int[g.n];
        this.inStack = new boolean[g.n];
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
    }

    public List<List<Integer>> computeSCC() {
        long start = System.nanoTime();
        for (int v = 0; v < g.n; v++) if (disc[v] == -1) dfs(v);
        metrics.addTimeScc(System.nanoTime() - start);
        return result;
    }

    private void dfs(int u) {
        metrics.incDfsVisits();
        disc[u] = low[u] = time++;
        st.push(u);
        inStack[u] = true;

        for (Edge e : g.adj.get(u)) {
            metrics.incDfsEdges();
            int v = e.v;
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int v = st.pop();
                inStack[v] = false;
                comp.add(v);
                if (v == u) break;
            }
            result.add(comp);
        }
    }
}
