import edu.princeton.cs.algs4.Insertion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Quick3String {
    public static int charAt(String s, int i) {
        if (i >= s.length()) return -1;
        return s.charAt(i);
    }

    public static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1,0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int i = lo + 1;
        int v = charAt(a[lo], d);
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else i++;
        }

        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("nameclass.txt"))); // //file test
        String[] a = new String[20];
        for (int i = 0; i < a.length; i++) {
            if (StdIn.hasNextLine()) {
                a[i] = StdIn.readLine();
            }
        }
        sort(a);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}