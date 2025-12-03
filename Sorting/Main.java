import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
//    public static void main(String[] args) {
//        MinPQ<Integer> m = new MinPQ<>(20);
//        m.insert(2);
//        m.insert(3);
//        m.insert(5);
//        m.insert(6);
//        m.insert(7);
//        m.insert(8);
//        m.insert(9);
//        m.display();
//        System.out.println();
//        m.delMin();
//        m.display();
//        System.out.println();
//        m.delMin();
//        m.display();
//    }

//    public static void main(String[] args)throws IOException {
//        System.setIn(new FileInputStream(new File("tinyPQ.txt")));
//        MaxPQ<String> pq = new MaxPQ<String>(30);
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) pq.insert(item);
//            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
//        }
//        StdOut.println("(" + pq.size() + " left on pq)");
//    }

    public static void main(String[] args) {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(10);

        pq.insert(0, 5);
        pq.insert(1, 20);
        pq.insert(2, 15);
        pq.insert(3, 30);

        StdOut.println("Max index = " + pq.minIndex()); // expect 3
        StdOut.println("Max key = " + pq.min());     // expect 30

        int removed = pq.delMin();
        StdOut.println("Deleted index = " + removed);   // expect 3
        StdOut.println("New max key = " + pq.min()); // expect 20

        pq.changeKey(2, 3); // tăng key của index 2 từ 15 -> 50
        StdOut.println("After increase, max key = " + pq.min()); // expect 50
        StdOut.println("Max index = " + pq.minIndex()); // expect 2

        pq.changeKey(2, 0); // giảm key của index 2 xuống 1
        StdOut.println("After decrease, max key = " + pq.min()); // expect 20
    }
}