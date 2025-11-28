import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class sinhvien {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream(new File("students.csv")));
        ST<String, String> st = new ST<String, String>();
        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            String[] tokens = line.split(",");
            String id = tokens[0];
            String name = tokens[1];
            st.put(id, name);
        }
        for (String key : st.keys()) {
            StdOut.println(key+" :"+st.get(key));
        }
    }
}