import java.math.BigInteger;
import java.util.Random;

public class Rabin_Karp {
    private String pat;
    private long hashPat;
    private int M; // patterm length
    private int R; // alphabet_TiengViet size
    private Alphabet alphabet;
    // private int R = 256;
    private long Q; // large prime number
    private long rM; // R^(M-1) % Q

    public Rabin_Karp(String patterm, Alphabet alphabet) {
        this.pat = patterm;
        this.M = patterm.length();
        this.alphabet = alphabet;
        this.R = alphabet.R;
        this.Q = longRandomPrime();
        this.rM = 1;
        for (int i = 1; i <= M - 1; i++) {
            rM = (R * rM) % Q;
        }
        hashPat = hash(pat, M);
    }

    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int i = 0; i < M; i++) {
            h = (h * R + alphabet.toIndex(key.charAt(i))) % Q;
        }
        return h;
    }

    private boolean check(String txt, int i) {
        for (int j = 0; j < M; j++)
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true;
    }

    public int search(String txt) {
        int n = txt.length();
        if (n < M)
            return n;
        long txtHash = hash(txt, M);

        if ((hashPat == txtHash) && check(txt, 0))
            return 0;

        for (int i = M; i < n; i++) {
            txtHash = (txtHash + Q - rM * alphabet.toIndex(txt.charAt(i - M)) % Q) % Q;
            txtHash = (txtHash * R + alphabet.toIndex(txt.charAt(i))) % Q;

            int offset = i - M + 1;
            if ((hashPat == txtHash) && check(txt, offset))
                return offset;
        }
        return n;
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

        Rabin_Karp r = new Rabin_Karp("Ngọc Anh", alphabet);
        System.out.println(r.search("hẹ hẹ Nanh"));
    }
}