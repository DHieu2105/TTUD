import java.util.Comparator;

public class MinPQ <Key extends Comparable<Key>>{
    private Key[] pq;
    private int N = 0;
    private Comparator<Key> comparator;

    public MinPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }


    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        if (N == pq.length) {
            System.out.println("MinxPQ overflow");
            return;
        }
        pq[++N] = key;
        swim(N);
    }

    public Key delMin() {
        Key min = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return min;
    }

    public void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public boolean less(int i,int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    public void swim(int k) {
        while (k > 1 && !less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    public void sink(int k) {
        while ( 2*k <= N ) {
            int j = 2*k;
            if (j < N && !less(j,j+1)) j++;
            if (!less(j,k)) break;
            exch(k,j);
            k = j;
        }
    }

    public void display() {
        for (int i = 1; i <= N; i++) {
            System.out.print(pq[i]+" ");
        }
    }


}