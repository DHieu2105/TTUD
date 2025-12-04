import edu.princeton.cs.algs4.*;
import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class Rabin_Karp {
    private String pat;         // pattern
    private long hashPat;       // pattern hash
    private int M;              // pattern length
    private int R;              // alphabet size
    private Alphabet alphabet;  // dùng Alphabet tự viết
    private long Q;             // số nguyên tố lớn
    private long rM;            // R^(M-1) % Q

    public Rabin_Karp(String pattern, Alphabet alphabet) {
        this.pat = pattern;
        this.alphabet = alphabet;
        this.M = pattern.length();
        this.R = alphabet.R();
        this.Q = longRandomPrime();

        // Tính R^(M-1) % Q
        rM = 1;
        for (int i = 1; i < M; i++) {
            rM = (rM * R) % Q;
        }

        hashPat = hash(pattern, M);
    }

    // Sinh số nguyên tố ngẫu nhiên ~ 31 bit
    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    // Tính hash cho đoạn văn bản độ dài M
    private long hash(String key, int m) {
        long h = 0;
        for (int i = 0; i < m; i++) {
            h = (h * R + alphabet.toIndex(key.charAt(i))) % Q;
        }
        return h;
    }

    // Kiểm tra khớp chính xác tại vị trí i
    private boolean check(String txt, int i) {
        for (int j = 0; j < M; j++) {
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        }
        return true;
    }

    // Tìm vị trí đầu tiên của pattern trong txt
    public int search(String txt) {
        int n = txt.length();
        if (n < M) return n;

        long txtHash = hash(txt, M);

        if (hashPat == txtHash && check(txt, 0))
            return 0;

        for (int i = M; i < n; i++) {
            // Xóa ký tự đầu, thêm ký tự cuối
            txtHash = (txtHash + Q - (rM * alphabet.toIndex(txt.charAt(i - M)) % Q)) % Q;
            txtHash = (txtHash * R + alphabet.toIndex(txt.charAt(i))) % Q;

            int offset = i - M + 1;
            if (hashPat == txtHash && check(txt, offset))
                return offset;
        }
        return n; // không tìm thấy
    }

    public static void main(String[] args) {
        String vietnameseAlphabet =
            "aáàảãạăắằẳẵặâấầẩẫậeéèẻẽẹêếềểễệiíìỉĩị" +
            "oóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ" +
            "bcdghklmnpqrstvx" +
            "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊ" +
            "OÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐ" +
            "BCDGHKLMNPQRSTVX .,;:!?\"'()-0123456789\n\t";

        Alphabet alphabet = new Alphabet(vietnameseAlphabet);

        // Tìm từ "triệu" – có dấu, có thể viết hoa/thường
        Rabin_Karp rk = new Rabin_Karp("sách", alphabet);

        try {
            System.setIn(new FileInputStream("vbTV.txt"));
        } catch (FileNotFoundException e) {
            StdOut.println("Không tìm thấy file vbTV.txt");
            return;
        }

        String text = StdIn.readAll();
        int pos = rk.search(text);

        if (pos == text.length()) {
            StdOut.println("Không tìm thấy từ \"sách\"");
        } else {
            StdOut.println("Tìm thấy \"sách\" tại vị trí: " + pos);
            StdOut.println("Đoạn văn xung quanh: \"" + 
                text.substring(Math.max(0, pos-20), Math.min(text.length(), pos+30)) + "\"");
        }
    }
}