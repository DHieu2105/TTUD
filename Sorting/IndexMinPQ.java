public class IndexMinPQ<Key extends Comparable<Key>> {
    private int n;  //number of element in heap
    private int[] pq;   //priority queue min of index
    private int[] qp;   //index of element in heap
    private Key[] keys;     //real values

    //Initialization
    public IndexMinPQ(int maxN) {
        n = 0;
        pq = new int[maxN+1];
        qp = new int[maxN+1];
        keys = (Key[]) new Comparable[maxN+1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    public int size() { return n; }     //return size of heap
    public boolean isEmpty() { return n == 0; }     //check empty
    public int minIndex() { return pq[1]; }       //return index of min value
    public Key min() { return keys[pq[1]]; }    //return value min
    public boolean contains(int i ) { return qp[i] != -1; }

    //insert element with index and key
    public void insert(int i, Key key) {
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    //delete min and return min index
    public int delMin() {
        int min = pq[1];
        exch(1,n--);
        sink(1);

        qp[min] = -1;   //delete in heap
        keys[min] = null;      //
        pq[n+1] = -1;

        return min;
    }

    //change value
    public void changeKey(int i, Key key) {
        int index = qp[i];
        keys[i] = key;
        swim(index);
        sink(index);
    }

    //comparator
    public boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    //swap
    public void exch(int i, int j) {
        int tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    //floating small element
    public void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }
    //large element sink
    public void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if(j<n && greater(j,j+1)) j++;
            if(greater(j,k)) break;
            exch(k,j);
            k = j;
        }
    }

    //delete index i
    public void delete(int i) {
        int index = qp[i];
        exch(index,n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

}