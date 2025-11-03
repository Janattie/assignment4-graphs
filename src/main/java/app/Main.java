package app;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import graph.dagsp.DagLongestPath;
import graph.dagsp.DagShortestPaths;
import graph.io.GraphIO;
import graph.model.Graph;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import util.Metrics;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = args.length > 0 ? args[0] : "data/tasks.json";
        GraphIO.InputData input = loadInput(inputPath);
        Graph g = GraphIO.fromInputData(input);

        Metrics metrics = new Metrics();


        long t1 = System.nanoTime();
        TarjanSCC tarjan = new TarjanSCC(g, metrics);
        List<List<Integer>> sccs = tarjan.computeSCC();
        long t2 = System.nanoTime();

        System.out.println("SCC components:");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("  C" + i + " = " + sccs.get(i));
        }
        System.out.printf("SCC time: %.3f ms, dfsVisits=%d, dfsEdges=%d%n",
                (t2 - t1) / 1e6, metrics.getDfsVisits(), metrics.getDfsEdges());


        Graph dag = Graph.buildCondensation(g, sccs);
        System.out.println("\nCondensation DAG: n=" + dag.n + ", edges=" + dag.edges.size());


        long t3 = System.nanoTime();
        List<Integer> topo = TopologicalSort.kahn(dag, metrics);
        long t4 = System.nanoTime();
        System.out.println("\nTopological order of components: " + topo);
        System.out.printf("Topo time: %.3f ms, kahnPushes=%d, kahnPops=%d%n",
                (t4 - t3) / 1e6, metrics.getKahnPushes(), metrics.getKahnPops());


        int source = input.source;
        int sourceComp = Graph.whichComponent(source, sccs);
        if (sourceComp == -1) throw new IllegalStateException("Source not in any SCC");
        long t5 = System.nanoTime();
        DagShortestPaths.Result sp = DagShortestPaths.shortestPaths(dag, topo, sourceComp, metrics);
        long t6 = System.nanoTime();

        System.out.printf("%nShortest paths from component %d:%n", sourceComp);
        System.out.println("dist = " + Arrays.toString(sp.dist));
        System.out.println("parent = " + Arrays.toString(sp.parent));
        System.out.printf("DAG-SP time: %.3f ms, relaxations=%d%n",
                (t6 - t5) / 1e6, metrics.getRelaxations());


        DagLongestPath.Result lp = DagLongestPath.longestPath(dag, topo);
        System.out.printf("%nCritical path length = %.2f%nPath (component ids) = %s%n",
                lp.maxLen, lp.path);


        System.out.println("\nOne shortest path example from source component:");
        int bestTarget = -1;
        double bestDist = Double.POSITIVE_INFINITY;
        for (int v = 0; v < dag.n; v++) {
            if (sp.dist[v] < bestDist && v != sourceComp) {
                bestDist = sp.dist[v];
                bestTarget = v;
            }
        }
        if (bestTarget != -1 && bestDist < Double.POSITIVE_INFINITY) {
            List<Integer> path = DagShortestPaths.reconstructPath(sp.parent, sourceComp, bestTarget);
            System.out.println("target component = " + bestTarget + ", dist = " + bestDist);
            System.out.println("path (component ids) = " + path);
        } else {
            System.out.println("No reachable target from source component.");
        }
    }

    private static GraphIO.InputData loadInput(String path) throws Exception {
        try (JsonReader r = new JsonReader(new FileReader(Path.of(path).toFile()))) {
            return new Gson().fromJson(r, GraphIO.InputData.class);
        }
    }
}
