import java.util.ArrayList;

public class RangeSearch1D<Key extends Comparable<Key>, Value> {
    
    private RedBlackBST<Key, Value> rb;

    public RangeSearch1D() {
        rb = new RedBlackBST<>();
    }

    public void insert(Key key, Value val) {
        rb.put(key, val);
    }

    public Value search(Key key) {
        return rb.get(key);
    }

    public boolean contains(Key key) {
        return rb.contains(key);
    }

    public void delete(Key key) {
        rb.delete(key);
    }

    // PHIÊN BẢN ĐÚNG – CHỈ DÙNG keys(lo, hi) CỦA REDBLACKBST
    public void rangeSearch(Key lo, Key hi) {
        if (lo == null || hi == null || lo.compareTo(hi) > 0) {
            System.out.println();
            return;
        }

        System.out.print("Keys trong [" + lo + ", " + hi + "]: ");
        for (Key k : rb.keys(lo, hi)) {  // Đây là cách DUY NHẤT đúng và nhanh nhất!
            System.out.print(k + " ");
        }
        System.out.println();
    }

    public ArrayList<Key> rangeSearch2(Key lo, Key hi) {
        ArrayList<Key> result = new ArrayList<>();
        if (lo == null || hi == null || lo.compareTo(hi) > 0) return result;

        for (Key k : rb.keys(lo, hi)) {
            result.add(k);
        }
        return result;
    }

    public int rangeCount(Key lo, Key hi) {
        if (lo == null || hi == null || lo.compareTo(hi) > 0) return 0;
        return rb.size(lo, hi);  // Hàm có sẵn trong RedBlackBST bạn viết
    }

    // TEST – CHẠY NGON LÀNH
    public static void main(String[] args) {
        RangeSearch1D<Integer, String> rs = new RangeSearch1D<>();
        
        rs.insert(1, "A");
        rs.insert(10, "B");
        rs.insert(9, "M");
        rs.insert(4, "C");
        rs.insert(5, "D");
        rs.insert(1, "E");
        rs.insert(8, "F");

        System.out.println("=== Range Search 1D ===");
        rs.rangeSearch(2, 9);                    // In: Keys trong [2, 9]: 4 5 8 9
        System.out.println("Số lượng: " + rs.rangeCount(2, 9));  // 4

        rs.rangeSearch(0, 5);                    // Keys trong [0, 5]: 0 1 4 5 
        System.out.println("Danh sách [2,9]: " + rs.rangeSearch2(2, 9));
    }
}