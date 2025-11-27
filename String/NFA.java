import edu.princeton.cs.algs4.*;

public class NFA {

    private char[] re;              // biểu thức chính quy
    private Digraph G;              // epsilon transitions
    private int M;                  // số trạng thái
    private Alphabet alphabet;      // Alphabet tiếng Việt

    // Constructor hỗ trợ tiếng Việt
    public NFA(String regexp, Alphabet alphabet) {
        this.re = regexp.toCharArray();
        this.alphabet = alphabet;
        this.M = re.length;
        this.G = new Digraph(M + 1);

        Stack<Integer> ops = new Stack<>();

        // Xây dựng NFA theo thuật toán Thompson
        for (int i = 0; i < M; i++) {
            int lp = i;

            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);

            } else if (re[i] == ')') {

                int or = ops.pop();

                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }

            // toán tử sao *
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }

            // Epsilon transitions cho (, *, )
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i + 1);
            }
        }
    }

    // Hàm nhận dạng chuỗi tiếng Việt
    public boolean recognizes(String txt) {
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);

        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v)) pc.add(v);

        // duyệt chuỗi txt
        for (int i = 0; i < txt.length(); i++) {

            char c = txt.charAt(i);
            Bag<Integer> match = new Bag<>();

            for (int v : pc) {

                if (v == M) continue;

                // KIỂM TRA TIẾNG VIỆT
                if (re[v] == c || re[v] == '.') {
                    match.add(v + 1);
                }

            }

            // DFS epsilon từ tập trạng thái match
            pc = new Bag<Integer>();

            // marked[v] = true nếu v reachable từ bất kỳ state trong match qua epsilon
            boolean[] marked = new boolean[G.V()];
            
            for (int s : match) {
                DirectedDFS dfs2 = new DirectedDFS(G, s); // bản 1-source chắc chắn có
                for (int v = 0; v < G.V(); v++) {
                    if (dfs2.marked(v)) marked[v] = true;
                }
            }
            
            // gom tất cả v đã được đánh dấu vào pc
            for (int v = 0; v < G.V(); v++) {
                if (marked[v]) pc.add(v);
            }

            if (!pc.iterator().hasNext()) return false;
        }

        // nếu đạt được trạng thái cuối → match
        for (int v : pc)
            if (v == M) return true;

        return false;
    }

    public static void main(String[] args) {

        // Tạo Alphabet tiếng Việt
        Alphabet viet = new Alphabet(new String(Alphabet.VIETNAMESE));

        // Ví dụ regex tiếng Việt
        NFA nfa = new NFA("á*", viet);

        StdOut.println("Test regex: á*");

        StdOut.println("Chuỗi \"ááá\"  → " + nfa.recognizes("ááá"));     // true
        StdOut.println("Chuỗi \"aaa\"  → " + nfa.recognizes("aaa"));     // false
        
        NFA nfa2 = new NFA("(đ|d)a*", viet);

        StdOut.println("\nTest regex: (đ|d)a*");

        StdOut.println("Chuỗi \"đa\"     → " + nfa2.recognizes("đa"));     // true
        StdOut.println("Chuỗi \"daaaa\" → " + nfa2.recognizes("daaaa")); // true
        StdOut.println("Chuỗi \"ba\"    → " + nfa2.recognizes("ba"));    // false
        
        NFA nfa3 = new NFA("(.*chào.*)", viet);
        StdOut.println(nfa3.recognizes("xin chào bạn"));     // true
        
    }
}
