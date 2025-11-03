package graph;

import graph.model.Graph;
import graph.topo.TopologicalSort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesTests {

    @Test
    void singleNodeNoEdges_topoTrivial() {
        Graph dag = new Graph(1, true);
        var order = TopologicalSort.kahn(dag, new util.Metrics());
        assertEquals(List.of(0), order);
    }

    @Test
    void pureChain_topoOrderMatches() {
        Graph dag = new Graph(5, true);
        dag.addEdge(0,1,1); dag.addEdge(1,2,1); dag.addEdge(2,3,1); dag.addEdge(3,4,1);
        var order = TopologicalSort.kahn(dag, new util.Metrics());
        assertEquals(List.of(0,1,2,3,4), order);
    }
}
