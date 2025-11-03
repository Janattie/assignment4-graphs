package graph.io;

import graph.model.Graph;
import java.util.List;

public class GraphIO {
    public static class InputEdge {
        public int u, v;
        public double w;
    }
    public static class InputData {
        public boolean directed;
        public int n;
        public List<InputEdge> edges;
        public Integer source;
        public String weight_model;
    }

    public static Graph fromInputData(InputData in) {
        Graph g = new Graph(in.n, in.directed);
        for (InputEdge e : in.edges) {
            g.addEdge(e.u, e.v, e.w);
        }
        return g;
    }
}
