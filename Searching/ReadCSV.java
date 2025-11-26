import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ReadCSV {

    static class Student {
        String maSV;
        String hoTen;

        Student(String maSV, String hoTen) {
            this.maSV = maSV;
            this.hoTen = hoTen;
        }
    }

    public static void main(String[] args) throws IOException {

        Alphabet val = new Alphabet(new String(Alphabet.VIETNAMESE));

        BufferedReader br = new BufferedReader(new FileReader("students.csv"));

        String line;
        Student[] list = new Student[1000];
        int n = 0;

        br.readLine(); 

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            String ma = parts[0];
            String ten = parts[1];
            list[n++] = new Student(ma, ten);
        }
        br.close();

        StdOut.println("=== DANH SÁCH SV ===");
        for (int i = 0; i < n; i++) {
            StdOut.println(list[i].hoTen + " | " + list[i].maSV);
        }

        StdOut.println("\n=== SV có tên HIẾU ===");
        for (int i = 0; i < n; i++) {
            String[] t = list[i].hoTen.split(" ");
            String ten = t[t.length - 1];
            if (ten.equalsIgnoreCase("Hiếu")) {
                StdOut.println(list[i].hoTen + " | " + list[i].maSV);
            }
        }

        StdOut.println("\n=== SV có họ NGUYỄN ===");
        for (int i = 0; i < n; i++) {
            String[] t = list[i].hoTen.split(" ");
            String ho = t[0];
            if (ho.equalsIgnoreCase("Nguyen")) {
                StdOut.println(list[i].hoTen + " | " + list[i].maSV);
            }
        }
    }
}
