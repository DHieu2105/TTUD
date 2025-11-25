import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class SuffixArray {
    public Suffix[] suffixes;

    public SuffixArray(String text) {
        int n = text.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(text, i);
        }
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private int index;
        private String text;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private int length() {
            return text.length() - index;
        }

        private char charAt(int i) {
            return text.charAt(index + i);
        }

        // private String substring(int start, int end) {}

        public int compareTo(Suffix that) {
            if (this == that)
                return 0;
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i))
                    return -1;
                if (this.charAt(i) > that.charAt(i))
                    return 1;
            }
            return this.length() - that.length();
        }

        public String toString() {
            return this.text.substring(index);
        }
    }

    public int length() {
        return suffixes.length;
    }

    public int index(int i) {
        if (i < 0 || i >= suffixes.length)
            throw new IllegalArgumentException("Index out of bounds");
        return suffixes[i].index;
    }

    public int lcp(int i) {
        if (i < 1 || i >= suffixes.length)
            throw new IllegalArgumentException("Index out of bounds");
        return lcpSuffix(suffixes[i], suffixes[i - 1]);
    }

    private int lcpSuffix(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i))
                return i;
        }
        return n;
    }

    public String select(int i) {
        if (i < 0 || i >= suffixes.length)
            throw new IllegalArgumentException("Index out of bounds");
        return suffixes[i].toString();
    }

    public int rank(String query) {
        int lo = 0, hi = suffixes.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, suffixes[mid]);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }

    private static int compare(String s, Suffix suffix) {
        int n = Math.min(s.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) > suffix.charAt(i))
                return 1;
            if (s.charAt(i) < suffix.charAt(i))
                return -1;
        }
        return s.length() - suffix.length();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("vbTV.txt")));
        String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
        String[] words = s.split(" ");
        VietnameseSuffixArray suffix = new VietnameseSuffixArray(words);
        int max = 0;
        for (int i = 1; i < suffix.length(); i++) {
            if (suffix.lcp(i) > max)
                max = suffix.lcp(i);
        }
        System.out.println(max);
    }

    // public static void main(String[] args) throws IOException {
    // System.setIn(new FileInputStream(new File("abra.txt")));
    // String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
    //
    // SuffixArray suffix = new SuffixArray(s);
    //
    // // StdOut.println("rank(" + args[0] + ") = " + suffix.rank(args[0]));
    //
    // StdOut.println(" i ind lcp rnk select");
    // StdOut.println("---------------------------");
    //
    // for (int i = 0; i < s.length(); i++) {
    // int index = suffix.index(i);
    // String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) +"\"";
    // //assert s.substring(index).equals(suffix.select(i));
    // int rank = suffix.rank(s.substring(index));
    // if (i == 0) {
    // StdOut.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
    // }
    // else {
    // int lcp = suffix.lcp(i);
    // StdOut.printf("%3d %3d %3d %3d %s\n", i, index, lcp, rank, ith);
    // }
    // }
    // }

}