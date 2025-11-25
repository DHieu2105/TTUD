public class KMP {
    private int[][] dfa;
    private String pattern;
    private int R;
    private int M;
    private Alphabet alphabet;

    // public KMP(String pattern) {
    // this.pattern = pattern;
    // this.M = pattern.length();
    // this.R = 256;
    // this.dfa = new int[R][M];
    //
    // dfa[pattern.charAt(0)][0] = 1;
    // for (int i = 1, X =0; i < M; i++){
    // for (int j = 0; j < R; j++){
    // dfa[j][i] = dfa[j][X];
    // }
    // dfa[pattern.charAt(i)][i] = i+1;
    // X = dfa[pattern.charAt(i)][X];
    // }
    // }

    // Tiengviet
    public KMP(String pattern, Alphabet alphabet) {
        this.pattern = pattern;
        this.M = pattern.length();
        this.alphabet = alphabet;
        this.R = alphabet.R;
        this.dfa = new int[R][M];

        dfa[alphabet.toIndex(pattern.charAt(0))][0] = 1;
        for (int i = 1, X = 0; i < M; i++) {
            for (int j = 0; j < R; j++) {
                dfa[j][i] = dfa[j][X];
            }
            dfa[alphabet.toIndex(pattern.charAt(i))][i] = i + 1;
            X = dfa[alphabet.toIndex(pattern.charAt(i))][X];
        }
    }

    public int search(String txt) {
        int N = txt.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[alphabet.toIndex(txt.charAt(i))][j];
        }
        if (j == M)
            return i - M;
        return N;
    }

    public static void main(String[] args) {
        String vietnameseAlphabet =
                // chữ thường
                "aàáảãạăằắẳẵặâầấẩẫậ " +
                        "b" +
                        "c" +
                        "dđ" +
                        "eèéẻẽẹêềếểễệ" +
                        "g" +
                        "h" +
                        "iìíỉĩị" +
                        "k" +
                        "l" +
                        "m" +
                        "n" +
                        "oòóỏõọôồốổỗộơờớởỡợ" +
                        "p" +
                        "q" +
                        "r" +
                        "s" +
                        "t" +
                        "uùúủũụưừứửữự" +
                        "v" +
                        "x" +
                        "yỳýỷỹỵ" +

                        // chữ hoa
                        "AÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬ" +
                        "B" +
                        "C" +
                        "DĐ" +
                        "EÈÉẺẼẸÊỀẾỂỄỆ" +
                        "G" +
                        "H" +
                        "IÌÍỈĨỊ" +
                        "K" +
                        "L" +
                        "M" +
                        "N" +
                        "OÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢ" +
                        "P" +
                        "Q" +
                        "R" +
                        "S" +
                        "T" +
                        "UÙÚỦŨỤƯỪỨỬỮỰ" +
                        "V" +
                        "X" +
                        "YỲÝỶỸỴ";
        Alphabet alphabet = new Alphabet(vietnameseAlphabet);
        KMP dfa = new KMP("đới", alphabet);
        System.out.println(dfa.search("mđới"));
    }
}