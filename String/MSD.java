import edu.princeton.cs.algs4.Insertion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MSD {
    public static int R = 65536;
    public static int M = 15;
    public static String[] aux;

    public static int charAt(String s, int d) {
        if (d < s.length()) { return s.charAt(d); }
        else { return -1; }
    }
    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a,0,N-1,0);
    }
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            InsertionSort(a, lo, hi, d);
            return;
        }
        int[] count = new int[R +2];
        //tan suat
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i],d) + 2]++;
        }
        //vi tri
        for (int i=0 ; i<R+1 ; i++) {
            count[i+1] += count[i];
        }
        //
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i],d) +1] ++] = a[i];
        }
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i-lo];
        }

        for (int i = lo; i <= hi; i++) {
            sort(a,lo+count[i], lo + count[i+1] -1,d+1);
        }

    }

    private static void InsertionSort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j-1],a[j],d); j--) {
                String t = a[j];
                a[j] = a[j-1];
                a[j-1] = t;
            }
        }
    }

    private static boolean less(String v, String w, int d) {
        for (int i = d; i <= Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("name1.txt"))); // //file test
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