import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

public class VietnameseSuffixArray {
    private suffixWord[] suffies;
    private Collator collator;
    public VietnameseSuffixArray(String[] words) {
        int n = words.length;
        suffies = new suffixWord[n];
        //Khoi tao cho Tieng Viet
        collator = Collator.getInstance(new Locale("vi", "VN"));
        for (int i = 0; i < n; i++) {
            suffies[i] = new suffixWord(words, i, collator);
        }
        Arrays.sort(suffies);
    }

    public int length() {
        return suffies.length;
    }

    private static class suffixWord implements Comparable<suffixWord> {
        private final String[] word;
        private final int index;
        private final Collator collator;

        public suffixWord(String[] word, int index, Collator collator) {
            this.word = word;
            this.index = index;
            this.collator = collator;
        }

        private int length() {
            return word.length - index;
        }

        private String stringAt(int i) {
            return word[index + i];
        }
        @Override
        public int compareTo(suffixWord that) {
            if (this == that) return 0;
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                int cmp = collator.compare(this.stringAt(i), that.stringAt(i));
                if (cmp != 0) return cmp;
            }
            return this.length() - that.length();
        }
        @Override
        public String toString() {
            return String.join(" ", Arrays.copyOfRange(word, index, word.length));
        }
    }

    public int lcp(int i) {
        if (i < 1 || i >= suffies.length) throw new IllegalArgumentException("illegal index: " + i);
        return lcpsuffixWord(suffies[i], suffies[i-1]);
    }
    private int lcpsuffixWord(suffixWord s, suffixWord t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            int cmp = collator.compare(s.stringAt(i), t.stringAt(i));
            if (cmp != 0) return i;
        }
        return n;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File(args[0])));
        String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
        String[] words = s.split(" ");
        VietnameseSuffixArray suffix = new VietnameseSuffixArray(words);
        int max = 0;
        for (int i = 1; i < suffix.length(); i++) {
            if (suffix.lcp(i) > max) max = suffix.lcp(i);
        }
        System.out.println(max);
    }

}