import java.io.*;
import java.util.*;

public class CountUnicodeCharsWords {
    public static void main(String[] args) throws IOException {
        String filename = args[0];

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

        Map<Integer, Integer> charFreq = new HashMap<>(); // key: codepoint, value: số lần xuất hiện
        Map<String, Integer> wordFreq = new HashMap<>();  // key: từ, value: số lần xuất hiện

        String line;
        while ((line = br.readLine()) != null) {
            // --- Đếm ký tự ---
            //line.codePoints()
            //        .filter(cp -> !Character.isWhitespace(cp)) // bỏ khoảng trắng
            //        .forEach(cp -> charFreq.put(cp, charFreq.getOrDefault(cp, 0) + 1));

            // --- Đếm từ ---
            String[] words = line.trim().split("\\s+"); // tách từ theo khoảng trắng
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }
        }
        br.close();

        // --- In kết quả ký tự ---
        //StdOut.println("=== Số lần xuất hiện từng ký tự ===");
        //for (Map.Entry<Integer, Integer> entry : charFreq.entrySet()) {
        //    int cp = entry.getKey();
        //    int count = entry.getValue();
        //    System.out.println(Character.toChars(cp) + " : " + count);
        //}

        // --- In kết quả từ ---
        System.out.println("\n=== Số lần xuất hiện từng từ ===");
        for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
