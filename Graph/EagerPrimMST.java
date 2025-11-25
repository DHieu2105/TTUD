import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

public class EagerPrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public EagerPrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        distTo[0] = 0.0;
        pq.insert(0, 0.0);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            visit(G, v);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++)
            if (edgeTo[v] != null)
                mst.enqueue(edgeTo[v]);
        return mst;
    }

    public double weight() {
        double total = 0.0;
        for (Edge e : edges())
            total += e.weight();
        return total;
    }

    public static void main(String[] args) {
        In in = new In("tinyG.txt");
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        EagerPrimMST mst = new EagerPrimMST(G);
        for (Edge e : mst.edges())
            StdOut.println(e);
    }
}
