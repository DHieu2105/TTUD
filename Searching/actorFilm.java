import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class actorFilm {

    public static void main(String[] args) throws FileNotFoundException {

        simpleTable<String, Queue<String>> st = new simpleTable<>();

        System.setIn(new FileInputStream(new File("movies.txt")));

        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();

            String[] tokens = line.split("/");

            String movie = tokens[0].trim();

            for (int i = 1; i < tokens.length; i++) {
                String actor = tokens[i].trim();

                if (st.contains(actor)) {
                    st.get(actor).add(movie);
                } else {
                    Queue<String> q = new LinkedList<>();
                    q.add(movie);
                    st.put(actor, q);
                }
            }
        }

        StdOut.println("Các phim của Brown, Bryan (I):");
        Queue<String> q = st.get("Brown, Bryan (I)");
        if (q != null) {
            for (String movie : q) {
                StdOut.println(" - " + movie);
            }
        } else {
            StdOut.println("Không có dữ liệu.");
        }
    }
}
