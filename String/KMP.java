import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

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
            "aáàảãạăắằẳẵặâấầẩẫậeéèẻẽẹêếềểễệiíìỉĩị" +
            "oóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ" +
            "bcdđghklmnpqrstvx" +
            "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊ" +
            "OÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ" +
            "BCDĐGHKLMNPQRSTVX .,;:!?\"'()-0123456789\n\t";
    
        Alphabet alphabet = new Alphabet(vietnameseAlphabet);
    
        KMP kmp = new KMP("tri", alphabet);   // tìm từ "tri" không phân biệt hoa thường + dấu
    
        try {
            System.setIn(new FileInputStream("vbTV.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file vbTV.txt");
            return;
        }
    
        String text = StdIn.readAll();
        int pos = kmp.search(text);
    
        if (pos == text.length()) {
            System.out.println("Không tìm thấy từ \"tri\"");
        } else {
            System.out.println("Tìm thấy \"tri\" tại vị trí: " + pos);
        }
    }
}