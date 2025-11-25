import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TopMStudent {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("Student.txt"))) {
            int n = Integer.parseInt(br.readLine());
            MinPQ<Student> pq = new MinPQ<>(n);
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String middleName = parts[0].trim(); // Họ + đệm
                    String firstName = parts[1].trim(); // Tên
                    Date dob = sdf.parse(parts[2].trim()); // yyyy-MM-dd
                    double score = Double.parseDouble(parts[3].trim());
                    Student st = new Student(middleName, firstName, dob, score);
                    pq.insert(st);
                    if (pq.size() > 100) {
                        pq.delMin();
                    }
                }
            }

            while (pq.size() > 0) {
                Student s = pq.delMin();
                System.out.println(s.toString());
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}