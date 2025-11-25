import java.util.HashMap;

public class BangDiem {

    private HashMap<String, Double> diem = new HashMap<>();

    public void setDiem(String mon, double d) {
        diem.put(mon, d);
    }

    public double tinhTBC() {
        if (diem.isEmpty()) return 0;
        double sum = 0;
        for (double d : diem.values()) {
            sum += d;
        }
        return sum / diem.size();
    }

    public HashMap<String, Double> getDiem() {
        return diem;
    }
}
