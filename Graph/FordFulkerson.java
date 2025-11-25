import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;

public class FordFulkerson {
    private static final double FLOATING_POINT_EPSILON = 1E-11;

    private final int V;            // số đỉnh
    private boolean[] marked;       // marked[v] = true nếu v thuộc source side của residual graph
    private FlowEdge[] edgeTo;      // last edge trên đường tăng flow
    private double value;           // giá trị max flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        V = G.V();
        validate(s);
        validate(t);
        if (s == t) throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(G, s, t)) throw new IllegalArgumentException("Initial flow is infeasible");

        value = excess(G, t);

        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }

        assert check(G, s, t);
    }

    public double value() { return value; }

    public boolean inCut(int v) {
        validate(v);
        return marked[v];
    }

    private void validate(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v);
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        marked[s] = true;

        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.poll();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }

        return marked[t];
    }

    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else excess += e.flow();
        }
        return excess;
    }

    private boolean isFeasible(FlowNetwork G, int s, int t) {
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) return false;
            }
        }
        return true;
    }

    private boolean check(FlowNetwork G, int s, int t) {
        if (!isFeasible(G, s, t)) return false;
        if (!inCut(s)) return false;
        if (inCut(t)) return false;

        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to())) mincutValue += e.capacity();
            }
        }
        return Math.abs(mincutValue - value) <= FLOATING_POINT_EPSILON;
    }

    // ======== MIN-CUT METHODS =========

    // trả về tập A (đỉnh thuộc source side)
    public Set<Integer> minCut() {
        Set<Integer> cut = new HashSet<>();
        for (int v = 0; v < V; v++) {
            if (marked[v]) cut.add(v);
        }
        return cut;
    }

    // trả về giá trị min-cut
    public double minCutValue(FlowNetwork G) {
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to())) {
                    mincutValue += e.capacity();
                }
            }
        }
        return mincutValue;
    }

    // ======== MAIN TEST =========
    public static void main(String[] args) {
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);

        int s = 0, t = G.V() - 1;
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        StdOut.println("Max flow = " + maxflow.value());

        StdOut.print("Min-cut vertices: ");
        for (int v : maxflow.minCut()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Min-cut value = " + maxflow.minCutValue(G));
    }
}
