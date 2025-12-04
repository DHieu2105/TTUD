import edu.princeton.cs.algs4.*;

public class StudentSearch {
    private static class Student {
        String maSV;
        String hoTen;  // họ tên đầy đủ

        Student(String ma, String ten, String hoDem) {
            this.maSV = ma;
            this.hoTen = (hoDem + " " + ten).trim();
        }

        public String toString() {
            return hoTen + "|" + maSV;
        }
    }

    public static void main(String[] args) {
        Alphabet viet = new Alphabet(Alphabet.VIETNAMESE);

        TST<Student> tst = new TST<>();
        In file = new In("sinhviencontext.csv");
        file.readLine(); // bỏ header

        while (!file.isEmpty()) {
            String line = file.readLine();
            if (line == null) break;

            String[] p = line.split(",", 3);  // tách đủ 3 cột
            if (p.length < 3) continue;

            String ten = p[0].trim();       // cột A: tên
            String hoDem = p[1].trim();     // cột B: họ đệm
            String maSV = p[2].trim();      // cột C: mã sv

            String hoTenDayDu = (hoDem + " " + ten).trim();

            // Kiểm tra ký tự hợp lệ tiếng Việt (tùy chọn)
            boolean hopLe = true;
            for (char c : hoTenDayDu.toCharArray()) {
                if (!viet.contains(c)) {
                    hopLe = false;
                    break;
                }
            }
            if (!hopLe) continue;

            // Lưu vào TST với key là họ tên đầy đủ
            tst.put(hoTenDayDu, new Student(maSV, ten, hoDem));
        }

        // 1. In toàn bộ danh sách
        System.out.println("=== Danh sách sinh viên ===");
        for (String key : tst.keys()) {
            System.out.println(key + "|" + tst.get(key).maSV);
        }

        // 2. In các SV có TÊN là Hiếu (cột A == "Hiếu")
        System.out.println("\n=== Sinh viên tên Hiếu ===");
        for (String key : tst.keys()) {
            Student sv = tst.get(key);
            // Tách tên từ họ tên đầy đủ: từ cuối cùng
            String ten = key.trim().split("\\s+")[key.trim().split("\\s+").length - 1];
            if (ten.equals("Hiếu")) {
                System.out.println(sv);
            }
        }

        // 3. In các SV họ Nguyễn (họ nằm ở đầu)
        System.out.println("\n=== Sinh viên họ Nguyễn ===");
        for (String key : tst.keys()) {
            String ho = key.trim().split("\\s+")[0];
            if (ho.equals("Nguyễn")) {
                System.out.println(tst.get(key));
            }
        }
    }
}