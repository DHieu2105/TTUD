import java.io.File;
import java.util.*;

public class FileIndexFrequenceMultiple {

    private ST<String, ST<File, Integer>> st;

    public FileIndexFrequenceMultiple() {
        st = new ST<>();
    }

    // Đọc toàn bộ file
    public void readFiles(String[] allFiles) {
        for (String filename : allFiles) {

            File file = new File(filename);
            In in = new In(file);

            while (!in.isEmpty()) {
                String word = in.readString();

                // tạo ST rỗng nếu cần
                if (!st.contains(word)) {
                    st.put(word, new ST<>());
                }

                ST<File, Integer> fileST = st.get(word);
                if (fileST.contains(file)) {
                    fileST.put(file, fileST.get(file) + 1);
                } else {
                    fileST.put(file, 1);
                }
            }
        }
    }

    // Lấy file -> số lần xuất hiện
    public ST<File, Integer> query(String word) {
        return st.get(word);
    }

    // ⭐ LẤY DANH SÁCH CÁC FILE CÓ CHỨA TỪ ĐÓ, SẮP XẾP TẦN SUẤT GIẢM DẦN
    public List<Map.Entry<File, Integer>> querySorted(String word) {
        ST<File, Integer> fileST = st.get(word);

        if (fileST == null) return null;

        // chuyển sang list để sort
        List<Map.Entry<File, Integer>> list = new ArrayList<>();
        for (File f : fileST.keys()) {
            list.add(new AbstractMap.SimpleEntry<>(f, fileST.get(f)));
        }

        // sort giảm dần theo số lần xuất hiện
        list.sort((a, b) -> b.getValue() - a.getValue());

        return list;
    }

    public static void main(String[] args) {
        StdOut.println("Indexing files...");
        FileIndexFrequenceMultiple freq = new FileIndexFrequenceMultiple();
        freq.readFiles(args);

        // mảng từ khoá
        String[] keywords = {"it"};

        for (String word : keywords) {
            StdOut.println("Files containing: " + word);
            List<Map.Entry<File, Integer>> result = freq.querySorted(word);

            if (result == null) {
                StdOut.println("Không tìm thấy từ!");
                continue;
            }

            for (Map.Entry<File, Integer> entry : result) {
                StdOut.println(entry.getKey().getName() + " : " + entry.getValue());
            }

            StdOut.println(); // thêm dòng trống giữa các từ khoá
        }
    }
}
