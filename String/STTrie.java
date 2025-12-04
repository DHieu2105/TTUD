import edu.princeton.cs.algs4.Queue;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

public class STTrie<Value> {

    //private static final int R = 256;
    private Node root;
    private Alphabet alphabet;

    public static class Node {
        private Object val;
        private Node[] next;

        public Node(Alphabet alphabet){
            next = new Node[alphabet.R()];
        }

    }
    public STTrie(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("key is not null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        int idx = alphabet.toIndex(c);
        if (idx < 0) return x;
        return get(x.next[idx], key, d + 1);
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("key is not null");
        root = put(root, key, val, 0);
    }
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node(alphabet);
        if (d == key.length()) { x.val = val; return x;}
        char c = key.charAt(d);
        int idx = alphabet.toIndex(c);
        if (idx == -1) return x;
        x.next[idx] = put(x.next[idx], key, val, d + 1);
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }


    //kwf
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (int i = 0; i < alphabet.R(); i++) {
            char c = alphabet.toChar(i);
            prefix.append(c);
            collect(x.next[i], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
        //for (char c = 0; c < alphabet.R(); c++) {
        //    prefix.append(c);
        //    collect(x.next[c], prefix, results);
        //    prefix.deleteCharAt(prefix.length() - 1);
        //}
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }
    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        for (int i = 0; i<alphabet.R(); i++ ) {
            char ch = alphabet.toChar(i);
            if (c =='_' || ch == c) {
                prefix.append(ch);
                collect(x.next[i], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

    }

    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        int idx = alphabet.toIndex(c); //
        if (idx == -1) return length;
        return longestPrefixOf(x.next[idx], query, d+1, length);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("sinhviencontext.csv")));
        StdIn.readLine();
        String vietnameseAlphabet = Alphabet.VIETNAMESE;


        Alphabet alphabet = new Alphabet(vietnameseAlphabet);
        STTrie<String> trie = new STTrie<String>(alphabet);
        while (StdIn.hasNextLine()) {
            String[] S = StdIn.readLine().split(",");
            String key = S[0] + " " + S[1];
            String val = S[2];
            trie.put(key,val);
        }


        //Bai21
        StdOut.println("longestPrefixOf(\" \"):");
        StdOut.println(trie.longestPrefixOf(" "));
        StdOut.println();
        StdOut.println("keysWithPrefix(\"Anh\"):");
        for (String s : trie.keysWithPrefix("Anh"))
            StdOut.println(s);
        StdOut.println();
        StdOut.println("keysThatMatch(\"____Nguyễn_____\"):");
        for (String s : trie.keysThatMatch("_____Nguyễn____"))
            StdOut.println(s);
        StdOut.println();




        //StdOut.println("longestPrefixOf(\"shellsort\"):");
        //StdOut.println(trie.longestPrefixOf("shellsort"));
        //StdOut.println();

        //StdOut.println("keysWithPrefix(\"shor\"):");
        //for (String s : trie.keysWithPrefix("shor"))
        //    StdOut.println(s);
        //StdOut.println();

        //StdOut.println("keysThatMatch(\".he.l.\"):");
        //for (String s : trie.keysThatMatch(".he..."))
        //    StdOut.println(s);

        //System.out.println(trie.longestPrefixOf("Quang Nguyễn Văn Nam"));
        //for (String s : trie.keysThatMatch("..Nguyễn....")) {
        //    System.out.println(s);
        //}
        //for (String s : trie.keysWithPrefix("Anh ")){
        //    System.out.println(s);
        //}

    }
}
