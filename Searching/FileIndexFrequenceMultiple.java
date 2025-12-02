import edu.princeton.cs.algs4.*;
import java.io.File;
import java.util.*;

public class FileIndexFrequenceMultiple {

    private ST<String, ST<File, Integer>> st;

    // Alphabet tiếng Việt (đã sửa dùng HashMap, không lỗi Unicode)
    private static final Alphabet VIET_ALPHABET = new Alphabet(
        "aáàảãạăắằẳẵặâấầẩẫậeéèẻẽẹêếềểễệiíìỉĩị" +
        "oóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ" +
        "bcdghklmnpqrstvx" +
        "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊ" +
        "OÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐ" +
        "BCDGHKLMNPQRSTVX .,;:!?\"'()-0123456789\n\t"
    );

    public FileIndexFrequenceMultiple() {
        st = new ST<>();
    }

    // Chuẩn hóa từ: chỉ giữ chữ cái + chuyển thường + giữ nguyên dấu
    private static String normalizeWord(String word) {
        if (word == null || word.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (VIET_ALPHABET.contains(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    // ĐỌC TẤT CẢ FILE – DÙNG TỪ ĐÃ CHUẨN HÓA LÀM KEY
    public void readFiles(String[] filenames) {
        for (String filename : filenames) {
            File file = new File(filename);
            if (!file.exists()) {
                StdOut.println("Không tìm thấy: " + filename);
                continue;
            }

            In in = new In(file);
            String content = in.readAll();  // Đọc hết nội dung file

            // Tách từ theo khoảng trắng và dấu câu
            String[] words = content.split("[\\s\\p{Punct}]+");

            for (String rawWord : words) {
                if (rawWord.trim().isEmpty()) continue;

                String normalized = normalizeWord(rawWord);  // ← chuẩn hóa
                if (normalized.isEmpty()) continue;

                // Dùng từ đã chuẩn hóa làm key
                if (!st.contains(normalized)) {
                    st.put(normalized, new ST<>());
                }

                ST<File, Integer> fileCount = st.get(normalized);

                if (fileCount.contains(file)) {
                    fileCount.put(file, fileCount.get(file) + 1);
                } else {
                    fileCount.put(file, 1);
                }
            }
        }
    }

    // Tìm kiếm theo từ khóa (sẽ tự chuẩn hóa)
    public List<Map.Entry<File, Integer>> querySorted(String keyword) {
        String normalized = normalizeWord(keyword);
        if (normalized.isEmpty()) return null;

        ST<File, Integer> fileST = st.get(normalized);
        if (fileST == null || fileST.isEmpty()) return null;

        List<Map.Entry<File, Integer>> list = new ArrayList<>();
        for (File f : fileST.keys()) {
            list.add(new AbstractMap.SimpleEntry<>(f, fileST.get(f)));
        }
        list.sort((a, b) -> b.getValue().compareTo(a.getValue())); // giảm dần
        return list;
    }

    // MAIN HOÀN CHỈNH
    public static void main(String[] args) {
        FileIndexFrequenceMultiple indexer = new FileIndexFrequenceMultiple();
        indexer.readFiles(args);  // ← đúng tên phương thức

        String[] keywords = { "học"  };

        for (String kw : keywords) {
            StdOut.println("\n════════════════════════════════");
            StdOut.printf("Tìm kiếm: \"%s\"\n", kw);

            List<Map.Entry<File, Integer>> result = indexer.querySorted(kw);

            if (result == null || result.isEmpty()) {
                StdOut.println("→ Không tìm thấy!");
                continue;
            }

            StdOut.println("→ Kết quả (" + result.size() + " file):");
            for (Map.Entry<File, Integer> e : result) {
                StdOut.printf("   %-30s : %4d lần\n", e.getKey().getName(), e.getValue());
            }
        }

        StdOut.println("\n════════════════════════════════");
    }
}