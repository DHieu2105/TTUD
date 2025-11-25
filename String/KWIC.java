import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import edu.princeton.cs.algs4.SuffixArray;

public class KWIC {
    public KWIC() {}

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File(args[2])));
        In in = new In(args[0]);
        String s = in.readAll().replaceAll("\\s+", " ").trim();
        SuffixArray sa = new SuffixArray(s);
        int context = Integer.parseInt(args[1]);
        int n = sa.length();

        while (StdIn.hasNextLine()) {
            String querry = StdIn.readLine();
            System.out.println(querry);
            for (int i = sa.rank(querry); i < n; i++) {
                int from1 = sa.index(i);
                int to1 = Math.min(n, from1 + querry.length());
                if (!querry.equals(s.substring(from1, to1))) break;
                int from2 = Math.max(0, from1 - context);
                int to2 = Math.min(n, from1+ context + querry.length());
                StdOut.println(s.substring(from2,to2));
            }
            StdOut.println();
        }

    }
}