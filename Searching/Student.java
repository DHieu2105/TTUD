import java.util.Objects;

public class Student {

    private String maSV;
    private String tenSV;
    private String ngaySinh;
    private BangDiem bangDiem;

    public Student(String maSV, String tenSV, String ngaySinh) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.ngaySinh = ngaySinh;
        this.bangDiem = new BangDiem();
    }

    public void setDiem(String mon, double diem) {
        bangDiem.setDiem(mon, diem);
    }

    public double tinhTBC() {
        return bangDiem.tinhTBC();
    }

    public String getMaSV() { return maSV; }
    public String getTenSV() { return tenSV; }
    public String getNgaySinh() { return ngaySinh; }
    public BangDiem getBangDiem() { return bangDiem; }

    // HashMap key phải có equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return Objects.equals(maSV, s.maSV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSV);
    }

    @Override
    public String toString() {
        return maSV + " - " + tenSV;
    }
}   
