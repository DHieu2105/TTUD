import edu.princeton.cs.algs4.*;
import java.nio.charset.StandardCharsets;

public class GREP {

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Cách dùng: java GREP \"mẫu\" \"input.txt\"");
            return;
        }

        String regexp = "(.*" + args[0] + ".*)";

        Alphabet viet = new Alphabet(new String(Alphabet.VIETNAMESE));

        NFA nfa = new NFA(regexp, viet);

        // đọc file nhưng xử lý UTF-8 đúng cách
        In in = new In(args[1]);

        while (!in.isEmpty()) {

            String txt = in.readLine();
            if (txt == null) break;

            // sửa lỗi encoding ISO → UTF-8
                    
            // IN RA CẢ DÒNG CHỨA CỤM TỪ
            if (nfa.recognizes(txt)) {
                StdOut.println(txt);
            }
        }
    }
}
