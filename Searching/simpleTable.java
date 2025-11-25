import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class simpleTable <Key extends Comparable<Key>, Value> implements Iterable <Key> {
    private TreeMap<Key, Value> st;

    public simpleTable() {
        st = new TreeMap<>();
    }

    public void put(Key key,Value value) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        if (value == null) st.remove(key);
        else st.put(key, value);
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        return st.get(key);
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        return st.containsKey(key);
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        st.remove(key);
    }

    public int size() { return st.size(); }

    public boolean isEmpty() {return st.isEmpty();}

    public Iterable<Key> keys() {
        return st.keySet();
    }

    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Empty table");
        return st.firstKey();
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Empty table");
        return st.lastKey();
    }

    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        return st.floorKey(key);
    }

    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is not null");
        return st.ceilingKey(key);
    }

    public static void main(String[] args) throws FileNotFoundException {
        simpleTable<String, Integer> table = new simpleTable<>();
        System.setIn(new FileInputStream(new File("tinySt.txt")));
        int i = 0;
        while(!StdIn.isEmpty()) {
            String key = StdIn.readString();
            table.put(key,i);
            i++;
        }
        for (String x : table.keys()) {
            System.out.println(x + ": " + table.get(x));
        }
    }
}