import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class hashStudent {

    private HashMap<Student, BangDiem> table;

    public hashStudent() {
        table = new HashMap<>();
    }

    // Thêm hoặc cập nhật điểm cho sinh viên
    public void insert(Student s, String mon, double diem) {
        if (s == null) throw new IllegalArgumentException("Student null");

        if (!table.containsKey(s)) {
            table.put(s, s.getBangDiem());
        }
        table.get(s).setDiem(mon, diem);
    }

    // Lấy điểm của 1 môn
    public Double get(Student s, String mon) {
        if (s == null) throw new IllegalArgumentException("Student null");
        if (!table.containsKey(s)) return null;

        return table.get(s).getDiem().get(mon);
    }

    // Xóa 1 môn hoặc xóa luôn SV nếu bảng điểm rỗng
    public void delete(Student s, String mon) {
        if (s == null) throw new IllegalArgumentException("Student null");

        if (!table.containsKey(s)) return;

        BangDiem bd = table.get(s);
        bd.getDiem().remove(mon);

        if (bd.getDiem().isEmpty()) {
            table.remove(s);
        }
    }

    public static void main(String[] args) {

        HashMap<Student, BangDiem> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {

            br.readLine(); // bỏ dòng tiêu đề

            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");

                if (arr.length < 7) {
                    System.out.println("Dòng lỗi: " + line);
                    continue;
                }

                String maSV = arr[0].trim();
                String tenSV = arr[1].trim();
                String ngaySinh = arr[2].trim();

                Student s = new Student(maSV, tenSV, ngaySinh);

                s.setDiem("Toan", Double.parseDouble(arr[3].trim()));
                s.setDiem("Ly",   Double.parseDouble(arr[4].trim()));
                s.setDiem("Hoa",  Double.parseDouble(arr[5].trim()));
                s.setDiem("Van",  Double.parseDouble(arr[6].trim()));

                map.put(s, s.getBangDiem());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // In TBC từng sinh viên
        System.out.println("==== DANH SÁCH VÀ ĐIỂM TRUNG BÌNH ====");
        for (Student s : map.keySet()) {
            System.out.printf("%s | TBC = %.2f\n", s, s.tinhTBC());
        }

    }
}
