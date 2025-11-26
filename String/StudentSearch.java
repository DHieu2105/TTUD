
public class StudentSearch {

    private static class Student {
        String maSV;
        String fullName;

        Student(String ma, String name) {
            this.maSV = ma;
            this.fullName = name;
        }

        public String toString() {
            return fullName + " | " + maSV;
        }
    }

    public static void main(String[] args) {

        // Dùng Alphabet để kiểm tra ký tự
        Alphabet vietnam = new Alphabet();

        // TST KHÔNG NHẬN ALPHABET
        TST<Student> tst = new TST<>();

        In file = new In("students.csv");

        file.readLine(); // bỏ header

        while (!file.isEmpty()) {
            String line = file.readLine();
            String[] p = line.split(",", 2);

            String ma = p[0].trim();
            String hoten = p[1].trim();

            // kiểm tra ký tự hợp lệ theo Alphabet tiếng Việt
            for (char c : hoten.toCharArray()) {
                if (!vietnam.contains(c) && c != ' ') {
                    System.out.println("Tên chứa ký tự không hợp lệ: " + c);
                }
            }

            // thêm vào TST
            tst.put(hoten, new Student(ma, hoten));
        }

        // ==== 1. In danh sách ====
        System.out.println("=== Danh sách ===");
        for (String key : tst.keys()) {
            System.out.println(key + " | " + tst.get(key).maSV);
        }

        // ==== 2. Tìm tên Hiếu ====
        System.out.println("\n=== Tên Hiếu ===");
        for (String key : tst.keys()) {
            if (key.endsWith("Hiếu")) {
                System.out.println(tst.get(key));
            }
        }

        // ==== 3. Tìm họ Nguyễn ====
        System.out.println("\n=== Họ Nguyễn ===");
        for (String key : tst.keysWithPrefix("Nguyễn")) {
            System.out.println(tst.get(key));
        }
    }
}
