public class DFA {
    private String pat;      // pattern
    int[][] dfa;       // bảng chuyển trạng thái DFA

    // Xây dựng DFA từ pattern
    public DFA(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;                    // bảng mã ASCII
        dfa = new int[R][M];

        // Trạng thái 0: gặp ký tự pat.charAt(0) thì chuyển sang trạng thái 1
        dfa[pat.charAt(0)][0] = 1;

        // X là trạng thái "restart" (trong trường hợp mismatch
        for (int X = 0, j = 1; j < M; j++) {
            // Sao chép tất cả các trường hợp mismatch từ trạng thái X
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            // Trường hợp match với ký tự hiện tại
            dfa[pat.charAt(j)][j] = j + 1;
            // Cập nhật lại trạng thái restart X
            X = dfa[pat.charAt(j)][X];
        }
    }

       
    public void printDFA() {
        StdOut.println("DFA table cho pattern: " + pat);
        StdOut.print("     ");
        for (int j = 0; j < pat.length(); j++) {
            StdOut.printf("%3d ", j);
        }
        StdOut.println();

        char[] chars = {'B', 'D', 'C'}; // chỉ in các ký tự xuất hiện trong pattern
        for (char c : chars) {
            StdOut.printf("%3c: ", c);
            for (int j = 0; j < pat.length(); j++) {
                StdOut.printf("%3d ", dfa[c][j]);
            }
            StdOut.println();
        }
        StdOut.println();
    }

    // Test
    public static void main(String[] args) {
        String pat = "BDCBDCDC";
        DFA kmp = new DFA(pat);
        kmp.printDFA();
    }
}