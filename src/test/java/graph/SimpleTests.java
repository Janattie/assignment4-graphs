package graph;

import graph.model.Graph;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;
import util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTests {

    @Test
    void sccAndTopo_onSmallGraph() {

        Graph g = new Graph(4, true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1);
        g.addEdge(2,3,2);

        Metrics m = new Metrics();
        var tarjan = new TarjanSCC(g, m);
        var sccs = tarjan.computeSCC();
        assertEquals(2, sccs.size());

        var dag = graph.model.Graph.buildCondensation(g, sccs);
        var topo = TopologicalSort.kahn(dag, m);
        assertEquals(dag.n, topo.size());
    }

    @Test
    void dagShortestAndLongestPaths() {

        Graph dag = new Graph(4, true);
        dag.addEdge(0,1,2);
        dag.addEdge(0,2,1);
        dag.addEdge(2,3,1);
        dag.addEdge(1,3,5);

        List<Integer> topo = List.of(0,2,1,3);
        Metrics m = new Metrics();

        var sp = DagShortestPaths.shortestPaths(dag, topo, 0, m);
        assertEquals(0.0, sp.dist[0], 1e-9);
        assertEquals(2.0, sp.dist[2], 1e-9);
        assertEquals(2.0, sp.dist[3], 1e-9);

        var lp = DagLongestPath.longestPath(dag, topo);
        assertTrue(lp.maxLen >= 0);
        assertFalse(lp.path.isEmpty());
    }
}
