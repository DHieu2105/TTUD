import edu.princeton.cs.algs4.EdgeWeightedDigraph; // ← ĐÚNG tên gói
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Alphabet;

import java.io.File;
import java.util.*;

public class FileIndexFrequenceMultiple {

    // Cấu trúc: từ → (file → số lần xuất hiện)
    private ST<String, ST<File, Integer>> index;

    // Bảng chữ cái tiếng Việt + ký tự cho phép
    private static final Alphabet VIET_ALPHABET = new Alphabet(
        "aáàảãạăắằẳẵặâấầẩẫậ" +
        "eéèẻẽẹêếềểễệ" +
        "iíìỉĩị" +
        "oóòỏõọôốồổỗộơớờởỡợ" +
        "uúùủũụưứừửữự" +
        "yýỳỷỹỵđ" +
        "bcdghklmnpqrstvx" +
        "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊ" +
        "OÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐ" +
        "BCDGHKLMNPQRSTVX .,;:!?\"'()-0123456789\n\t"
    );

    public FileIndexFrequenceMultiple() {
        index = new ST<>();
    }

    // Chuẩn hóa từ: chỉ giữ ký tự hợp lệ + chuyển về chữ thường (giữ nguyên dấu)
    private static String normalize(String word) {
        if (word == null || word.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (VIET_ALPHABET.contains(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    // Đọc và lập chỉ mục tất cả file
    public void buildIndex(String[] filenames) {
        for (String filename : filenames) {
            File file = new File(filename);
            if (!file.exists() || !file.isFile()) {
                StdOut.println("Không tìm thấy hoặc không phải file: " + filename);
                continue;
            }

            In in = new In(file);
            String content = in.readAll();
            in.close();

            String[] words = content.split("[\\s\\p{Punct}]+"); // tách từ cực chuẩn

            for (String raw : words) {
                String word = normalize(raw);
                if (word.isEmpty()) continue;

                // Lấy hoặc tạo ST cho từ này
                index.putIfAbsent(word, new ST<>());
                ST<File, Integer> files = index.get(word);

                // Cập nhật số lần xuất hiện trong file hiện tại
                files.put(file, files.getOrDefault(file, 0) + 1);
            }
        }
    }

    // Tổng số lần xuất hiện của từ trong TOÀN BỘ file
    public int totalFrequency(String keyword) {
        String word = normalize(keyword);
        if (word.isEmpty() || !index.contains(word)) return 0;

        int total = 0;
        for (int count : index.get(word).values()) {
            total += count;
        }
        return total;
    }

    // In kết quả đẹp + sắp xếp giảm dần theo tần suất
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Cách dùng: java FileIndexFrequenceMultiple file1.txt file2.txt ...");
            return;
        }

        FileIndexFrequenceMultiple indexer = new FileIndexFrequenceMultiple();
        indexer.buildIndex(args);

        // Danh sách từ bạn muốn kiểm tra (có thể thay đổi thoải mái)
        String[] keywords = { "tôi", "việt", "nam", "yêu", "the", "and", "of", "it", "times", "đẹp", "học" };

        // Tạo danh sách để sắp xếp
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        for (String kw : keywords) {
            list.add(new AbstractMap.SimpleEntry<>(kw, indexer.totalFrequency(kw)));
        }

        // Sắp xếp giảm dần theo số lần xuất hiện
        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        // In kết quả đẹp như báo cáo
        StdOut.println("\n══════════════════════════════════════");
        StdOut.println("     THỐNG KÊ TẦN SUẤT TỪ (toàn bộ file)");
        StdOut.println("══════════════════════════════════════");
        StdOut.printf("%-12s %8s\n", "Từ khóa", "Số lần");
        StdOut.println("──────────────────────────────────────");

        for (var e : list) {
            String star = e.getValue() > 0 ? "" : " (không có)";
            StdOut.printf("%-15s → %4d lần%s\n", e.getKey(), e.getValue(), star);
        }

        StdOut.println("══════════════════════════════════════");
        StdOut.printf("Đã xử lý %,d file thành công!\n", args.length);
    }
}