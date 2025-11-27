import edu.princeton.cs.algs4.*;

public class Alphabet {
    public static final Alphabet ASCII  = new Alphabet(128);
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
    public static final Alphabet UNICODE16 = new Alphabet(65536);
    public static final Alphabet DNA = new Alphabet("ACGT");
    public static final char[] VIETNAMESE = {
            // Nguyên âm thường
            'a','á','à','ả','ã','ạ','ă','ắ','ằ','ẳ','ẵ','ặ','â','ấ','ầ','ẩ','ẫ','ậ',
            'e','é','è','ẻ','ẽ','ẹ','ê','ế','ề','ể','ễ','ệ',
            'i','í','ì','ỉ','ĩ','ị',
            'o','ó','ò','ỏ','õ','ọ','ô','ố','ồ','ổ','ỗ','ộ','ơ','ớ','ờ','ở','ỡ','ợ',
            'u','ú','ù','ủ','ũ','ụ','ư','ứ','ừ','ử','ữ','ự',
            'y','ý','ỳ','ỷ','ỹ','ỵ',
            // Phụ âm thường
            'b','c','d','đ','g','h','k','l','m','n','p','q','r','s','t','v','x',

            // Nguyên âm in hoa
            'A','Á','À','Ả','Ã','Ạ','Ă','Ắ','Ằ','Ẳ','Ẵ','Ặ','Â','Ấ','Ầ','Ẩ','Ẫ','Ậ',
            'B','C','D','Đ',
            'E','É','È','Ẻ','Ẽ','Ẹ','Ê','Ế','Ề','Ể','Ễ','Ệ',
            'I','Í','Ì','Ỉ','Ĩ','Ị',
            'O','Ó','Ò','Ỏ','Õ','Ọ','Ô','Ố','Ồ','Ổ','Ỗ','Ộ','Ơ','Ớ','Ờ','Ở','Ỡ','Ợ',
            'U','Ú','Ù','Ủ','Ũ','Ụ','Ư','Ứ','Ừ','Ử','Ữ','Ự',
            'Y','Ý','Ỳ','Ỷ','Ỹ','Ỵ',
            // Phụ âm in hoa
            'G','H','K','L','M','N','P','Q','R','S','T','V','X'
    };

    public char[] alphabet;
    public int[] inverse;
    public final int R;

    public Alphabet(String alpha) {
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < alpha.length(); i++) {
            char c = alpha.charAt(i);
            if (unicode[c]) {
                throw new IllegalArgumentException("Illegal alphabet, character is repeated");
            }
            unicode[c] = true;
        }
        alphabet = alpha.toCharArray();
        inverse = new int[Character.MAX_VALUE];
        R = alphabet.length;
        for (int i = 0; i < inverse.length; i++) {
            inverse[i] = -1;
        }
        for (int i = 0; i < R ; i++) {

            inverse[alphabet[i]] = i;
        }
    }

    public Alphabet(int radix) {
        this.R = radix;
        alphabet = new char[R];
        inverse = new int[R];
        for ( int i=0; i < radix; i++ ) {
            alphabet[i] = (char) i;
        }
        for (int i=0; i < R; i++) {
            inverse[i] = i;
        }
    }

    public Alphabet() {
        this(256);
    }

    public boolean contains(char c) {
        return inverse[c] != -1;
    }

    public int R() { return R; }
    public int radix() { return R; }

    public int lgR() {
        int lgr = 0;
        for (int t = R-1 ; t >= 0 ; t/=2) {
            lgr++;
        }
        return lgr;
    }

    public int toIndex(char c) {
        if (c > inverse.length || inverse[c] == -1 ) {
            throw new IllegalArgumentException("Illegal index");
        }
        return inverse[c];
    }
    public int[] toIndices(String s) {
        char[] source = s.toCharArray();
        int[] result = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = toIndex(source[i]);
        }
        return result;
    }

    public char toChar(int i) {
        if (i < 0 || i >= R) {
            throw new IllegalArgumentException("Illegal index");
        }
        return alphabet[i];
    }
    public char[] toChars(int[] indices) {
        char[] result = new char[indices.length];
        for (int i = 0; i < indices.length; i++) {
            result[i] = toChar(indices[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int[]  encoded2 = Alphabet.DNA.toIndices("AACGAACGGTTTACCCCG");
        String decoded2 = new String (Alphabet.DNA.toChars(encoded2));
        StdOut.println(decoded2);
    }
}