package graph;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import graph.dagsp.DagLongestPath;
import graph.dagsp.DagShortestPaths;
import graph.io.GraphIO;
import graph.model.Graph;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import org.junit.jupiter.api.Test;
import util.Metrics;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FromJsonDatasetTest {

    private static GraphIO.InputData load(String relPath) throws Exception {
        try (JsonReader r = new JsonReader(new FileReader(Path.of(relPath).toFile()))) {
            return new Gson().fromJson(r, GraphIO.InputData.class);
        }
    }

    @Test
    void pipeline_on_tasks_json() throws Exception {
        GraphIO.InputData input = load("data/tasks.json");
        assertTrue(input.directed);
        assertEquals(8, input.n);

        Graph g = GraphIO.fromInputData(input);
        Metrics m = new Metrics();

        var tarjan = new TarjanSCC(g, m);
        List<List<Integer>> sccs = tarjan.computeSCC();
        assertEquals(6, sccs.size());

        boolean foundCycle123 = sccs.stream().anyMatch(comp -> {
            Set<Integer> set = new HashSet<>(comp);
            return set.contains(1) && set.contains(2) && set.contains(3) && set.size() == 3;
        });
        assertTrue(foundCycle123);

        Graph dag = Graph.buildCondensation(g, sccs);
        assertEquals(6, dag.n);
        assertEquals(4, dag.edges.size());

        List<Integer> topo = TopologicalSort.kahn(dag, m);
        assertEquals(dag.n, topo.size());
        boolean[] seen = new boolean[dag.n];
        for (int v : topo) {
            assertTrue(0 <= v && v < dag.n);
            seen[v] = true;
        }
        for (int i = 0; i < dag.n; i++) assertTrue(seen[i]);

        int comp4 = Graph.whichComponent(4, sccs);
        int comp5 = Graph.whichComponent(5, sccs);
        int comp6 = Graph.whichComponent(6, sccs);
        int comp7 = Graph.whichComponent(7, sccs);
        assertTrue(comp4 >= 0 && comp5 >= 0 && comp6 >= 0 && comp7 >= 0);

        DagShortestPaths.Result sp = DagShortestPaths.shortestPaths(dag, topo, comp4, m);
        assertEquals(0.0, sp.dist[comp4], 1e-9);
        assertEquals(2.0, sp.dist[comp5], 1e-9);
        assertEquals(7.0, sp.dist[comp6], 1e-9);
        assertEquals(8.0, sp.dist[comp7], 1e-9);

        DagLongestPath.Result lp = DagLongestPath.longestPath(dag, topo);
        assertEquals(8.0, lp.maxLen, 1e-9);
        assertFalse(lp.path.isEmpty());
    }
}
