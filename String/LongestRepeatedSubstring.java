import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LongestRepeatedSubstring {

    private LongestRepeatedSubstring() { }

    public static String lrs(String text) {
        int n = text.length();
        SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < n; i++) {
            int length = sa.lcp(i);
            if (length > lrs.length()) {
                // lrs = sa.select(i).substring(0, length);
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        return lrs;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("vbTV.txt")));
        String text = StdIn.readAll().replaceAll("\\s+", " ");
        StdOut.println("'" + lrs(text) + "'");
        //System.out.println(lrs(text).length());
    }
}
