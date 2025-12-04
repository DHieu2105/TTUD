import java.io.*;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class KWIK {

    private KWIK() {}

    // Chuẩn hóa để tìm không phân biệt dấu (nếu muốn)
    public static String unaccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        return temp.replaceAll("\\p{M}", "");
    }

    public static void main(String[] args) throws IOException {
        In in = new In(args[0]);
        int context = Integer.parseInt(args[1]);  
        
        String text = in.readAll()
                .replaceAll("\\s+", " ")    
                .trim();

        int n = text.length();

        SuffixArray sa = new SuffixArray(text);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String query;
        while (!(query = br.readLine()).isEmpty()) {
            query = query.trim();
            if (query.isEmpty()) continue;

            StdOut.printf("→ Đang tìm: \"%s\" (ngữ cảnh ±%d ký tự)%n", query, context);

            int found = 0;
            
            for (int i = sa.rank(query); i < n; i++) {
                int pos = sa.index(i);
                int end = pos + query.length();

                if (end > n) break;
                if (!text.substring(pos, end).equals(query)) break;

                // Lấy ngữ cảnh: 15 ký tự trước và sau
                int from = Math.max(0, pos - context);
                int to   = Math.min(n, pos + query.length() + context);

                String snippet = text.substring(from, to);

                // Đánh dấu từ khóa bằng chữ in đậm (dùng ký tự Unicode hoặc màu nếu muốn)
                String highlighted = snippet.replace(query, "❱❱" + query + "❰❰");

                StdOut.println(highlighted);
                found++;
            }

            StdOut.println(found > 0 ? 
                "\nTìm thấy " + found + " kết quả.\n" : 
                "Không tìm thấy.\n");
        }
    }
}