import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.In;
import java.util.Stack;

public class Dijkstra_T {

    private double[] distTo;        // distTo[v] = khoảng cách ngắn nhất từ v → t
    private DirectedEdge[] edgeTo;  // edgeTo[v] = cạnh cuối cùng trên đường đi ngắn nhất v → t
    private IndexMinPQ<Double> pq;

    public Dijkstra_T(EdgeWeightedDigraph G, int t) {
        // Kiểm tra trọng số âm
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        int V = G.V();
        distTo = new double[V];
        edgeTo = new DirectedEdge[V];
        pq = new IndexMinPQ<>(V);

        for (int v = 0; v < V; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[t] = 0.0;

        // Xây đồ thị đảo ngược
        EdgeWeightedDigraph R = new EdgeWeightedDigraph(V);
        for (DirectedEdge e : G.edges()) {
            R.addEdge(new DirectedEdge(e.to(), e.from(), e.weight()));  // đảo chiều cạnh
        }

        // Chạy Dijkstra từ t trên đồ thị đảo
        pq.insert(t, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : R.adj(v)) {   // duyệt các cạnh đi ra từ v trong đồ thị đảo
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();   // v là đỉnh đang xét trong đồ thị đảo
        int w = e.to();     // w là đỉnh tiếp theo trong đồ thị đảo → trong đồ thị gốc là w → v
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;  // lưu cạnh đảo ngược (sẽ dùng để khôi phục đường đi gốc)
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    // Khoảng cách ngắn nhất từ v tới t
    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // Trả về đường đi ngắn nhất từ v → t (dưới dạng các DirectedEdge trong đồ thị gốc)
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(new DirectedEdge(e.to(), e.from(), e.weight()));
        }
        return path;
    }

    public static void main(String[] args) {
        In in = new In("tinyEWD.txt");           // file dữ liệu mẫu của Sedgewick
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        int t = 6;  // ví dụ: tìm đường ngắn nhất từ mọi đỉnh tới đỉnh 6

        Dijkstra_T sp = new Dijkstra_T(G, t);

        StdOut.println("Đường đi ngắn nhất từ mọi đỉnh tới đích t = " + t + ":\n");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%d → %d (%.2f): ", v, t, sp.distTo(v));
            if (sp.hasPathTo(v)) {
                for (DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e.from() + " -> " + e.to() + " ");
                }
            } else {
                StdOut.print("không có đường");
            }
            StdOut.println();
        }
    }
}