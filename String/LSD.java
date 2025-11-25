import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LSD {
    public LSD() {}

    public static String[] sort(String[] a,int W) {
        int N = a.length;
        int R = 65536;
        String[] aux = new String[N];

        for (int d = W - 1; d >=0 ; d--) {
            int[] count = new int[R +1];

            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++ ;
            }

            for (int i=0; i<R; i++){
                count[i+1] += count[i];
            }

            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("name.txt")));
        String[] a = new String[20];
        for (int i = 0; i < a.length; i++) {
            if (StdIn.hasNextLine()) {
                a[i] = StdIn.readLine();
            }
        }
        a = sort(a,12);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }

    }
}
