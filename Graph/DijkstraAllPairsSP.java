import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DijkstraSP(G, v);
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }

    public double dist(int s, int t) {
        return all[s].distTo(t);
    }


    public static void main(String[] args) {
        // Đọc đồ thị từ file (VD: tinyEWD.txt)
        In in = new In("tinyEWD.txt");
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // Tạo đối tượng APSP
        DijkstraAllPairsSP all = new DijkstraAllPairsSP(G);

        // Ví dụ kiểm tra từ s = 2 đến t = 6
        int s = 2;
        int t = 6;

        System.out.println("Khoảng cách ngắn nhất từ " + s + " → " + t + " = " + all.dist(s, t));

        System.out.println("Đường đi:");
        for (DirectedEdge e : all.path(s, t)) {
            System.out.println(e);
        }
    }
}
