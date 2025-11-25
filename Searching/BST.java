import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import edu.princeton.cs.algs4.Queue;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    public class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BST() {
    }

    public int size() {
        return size(root);
    }

    public int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    public Boolean isEmpty() {
        return size() == 0;
    }

    public Boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("argument to put() is null");
        if (val == null) {
            delete(key);
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else {
            x.val = val;
        }
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("argument to deleteMin() is empty");
        root = deleteMin(root);

    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("argument to deleteMax() is empty");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.size = (x.left.size) + (x.right.size) + 1;
        return x;
    }

    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.left == null)
                return x.right;
            if (x.right == null)
                return x.left;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = (x.left.size) + (x.right.size) + 1;
        return x;
    }

    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("argument to min() is empty");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("argument to max() is empty");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        return max(x.right);
    }

    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null)
                continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    public static void main(String[] args) throws FileNotFoundException {
        BST<String, Integer> table = new BST<>();
        System.setIn(new FileInputStream(new File("tinyST.txt")));
        int i = 0;
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            table.put(key, i);
            i++;
        }
        for (String x : table.levelOrder()) {
            System.out.println(x + ": " + table.get(x));
        }
    }

}