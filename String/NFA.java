import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.*;
import java.util.*;

public class NFA {
    private char[] re;          // biểu thức chính quy
    private Digraph G;          // đồ thị epsilon-transition
    private int M;              // số trạng thái = độ dài regex
    private String regexp;      // lưu lại để in

    public NFA(String regexp) {
        this.regexp = regexp;
        this.re = regexp.toCharArray();
        this.M = re.length;
        G = new Digraph(M + 1);  // có thêm trạng thái accept M
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            int lp = i;

            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);   // ( ... | ...
                    G.addEdge(or, i);        // | ... )
                } else {
                    lp = or;
                }
            }

            // Kleene star *
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            // Epsilon transition cho các ký tự meta
            if (re[i] == '(' || re[i] == ')' || re[i] == '*') {
                G.addEdge(i, i + 1);
            }
        }
    }

    // IN BẢNG TRẠNG THÁI NFA ĐẸP NHƯ DFA
    public void printNFA() {
        StdOut.println("════════════════════════════════════════════════");
        StdOut.println("        NFA STATES FOR REGEX: \"" + regexp + "\"");
        StdOut.println("════════════════════════════════════════════════");
        StdOut.println("Số trạng thái: " + (M + 1) + " (0 → " + M + ", trạng thái " + M + " là accept)");
        StdOut.println();
    
        // Header
        StdOut.print("State │");
        for (int i = 0; i <= M; i++) {
            StdOut.printf("%4d", i);
        }
        StdOut.println("\n      ├" + "────┼".repeat(M + 1));
    
        // In từng trạng thái
        for (int v = 0; v <= M; v++) {
            StdOut.printf("%5d │", v);
    
            // Hiển thị ký tự tại trạng thái v (nếu có)
            if (v < M) {
                char c = re[v];
                if (c == '(' || c == ')' || c == '|' || c == '*') {
                    StdOut.print(" ε→ ");
                } else {
                    StdOut.printf(" %c→ ", c);
                }
            } else {
                    StdOut.print(" ACC");
                }
    
            // In các cạnh đi ra
            for (int w = 0; w <= M; w++) {
                boolean hasEdge = false;
                for (int x : G.adj(v)) {
                    if (x == w) {
                        hasEdge = true;
                        break;
                    }
                }
                if (hasEdge) {
                    StdOut.printf("%4d", w);
                } else {
                    StdOut.print("    ");
                }
            }
            StdOut.println();
        }
    
        StdOut.println("\nGiải thích:");
        StdOut.println("   số trong ô: có cạnh từ trạng thái hiện tại → trạng thái đó");
        StdOut.println("   ε→ : chuyển epsilon (không tiêu thụ ký tự)");
        StdOut.println("   X→ : chuyển khi gặp ký tự X");
        StdOut.println("   ACC: trạng thái chấp nhận");
        StdOut.println("════════════════════════════════════════════════\n");
    }

    // Hàm recognizes (giữ nguyên, có thể thêm trace nếu cần)
    public boolean recognizes(String txt) {
        Bag<Integer> pc = epsilonClosure(0);
    
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            Bag<Integer> next = new Bag<>();
    
            for (int v : pc) {
                if (v < M && (re[v] == c || re[v] == '.')) {
                    next.add(v + 1);
                }
            }
            pc = epsilonClosure(next);
            if (pc.isEmpty()) return false;  // cái này thì được, vì Bag có .isEmpty()
        }
    
        // Sửa lỗi contains ở đây
        for (int state : pc) {
            if (state == M) {
                return true;
            }
        }
        return false;
    }

    // Tính epsilon closure
    private Bag<Integer> epsilonClosure(Bag<Integer> states) {
        Bag<Integer> reach = new Bag<>();
        for (int v : states) {
            DirectedDFS dfs = new DirectedDFS(G, v);
            for (int w = 0; w < G.V(); w++) {
                if (dfs.marked(w)) reach.add(w);
            }
        }
        return reach;
    }
    private Bag<Integer> epsilonClosure(int v) {
        Bag<Integer> bag = new Bag<>();
        bag.add(v);
        return epsilonClosure(bag);
    }

    // MAIN TEST
    public static void main(String[] args) {
        String[] patterns = {
            ".*Hiếu",
            "(Nguyễn|Trần).*"
        };

        for (String pat : patterns) {
            NFA nfa = new NFA(pat);
            nfa.printNFA();

            // Test một vài chuỗi
            //String[] tests = {"Nguyễn Văn Hiếu", "Trần Thị Lan"};
            //for (String s : tests) {
            //    boolean res = nfa.recognizes(s);
            //    StdOut.printf("NFA(\"%s\").recognizes(\"%s\") = %s\n", pat, s, res ? "true" : "false");
            //}
            //StdOut.println("\n" + "═".repeat(60) + "\n");
        }
    }
}