import java.io.File;
import java.util.*;

public class FileIndexFrequence {

    private ST<String, Map<File, Integer>> st;

    public FileIndexFrequence() {
        st = new ST<>();
    }

    // Đọc toàn bộ file
    public void readFile(String[] allFile) {
        for (String filename : allFile) {

            File file = new File(filename);
            In in = new In(file);

            while (!in.isEmpty()) {
                String word = in.readString();

                // tạo map rỗng nếu cần
                if (!st.contains(word)) {
                    st.put(word, new HashMap<>());
                }

                Map<File, Integer> fileMap = st.get(word);
                fileMap.put(file, fileMap.getOrDefault(file, 0) + 1);
            }
        }
    }

    // Lấy file -> số lần xuất hiện
    public Map<File, Integer> query(String word) {
        return st.get(word);
    }

    // ⭐ LẤY DANH SÁCH CÁC FILE CÓ CHỨA TỪ ĐÓ, SẮP XẾP TẦN SUẤT GIẢM DẦN
    public List<Map.Entry<File, Integer>> querySorted(String word) {
        Map<File, Integer> fileMap = st.get(word);

        if (fileMap == null) return null;

        // chuyển sang list để sort
        List<Map.Entry<File, Integer>> list = new ArrayList<>(fileMap.entrySet());

        // sort giảm dần theo số lần xuất hiện
        list.sort((a, b) -> b.getValue() - a.getValue());

        return list;
    }

    public static void main(String[] args) {
        StdOut.println("Indexing files...");
        FileIndexFrequence freq = new FileIndexFrequence();
        freq.readFile(args);

        // thử với từ
        String word = "it";

        StdOut.println("Files containing: " + word);
        List<Map.Entry<File, Integer>> result = freq.querySorted(word);

        if (result == null) {
            StdOut.println("Không tìm thấy từ!");
            return;
        }
        
        
        
        for (Map.Entry<File, Integer> entry : result) {
            StdOut.println(entry.getKey().getName() + " : " + entry.getValue());
        }
    }
}
