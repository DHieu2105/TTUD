import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.NoSuchElementException;

public class Lop {
    static simpleTable<Integer, Student> st = new simpleTable<>();

    public Lop(In in) {
        if (in == null)
            throw new IllegalArgumentException("argument is not null");
        try {
            String[] tokens = in.readLine().split(",");
            String[] sub = new String[tokens.length - 3];
            for (int i = 0; i < sub.length; i++) {
                sub[i] = tokens[i + 3];
            }
            for (int i = 0; !in.isEmpty(); i++) {
                String[] parts = in.readLine().split(",");
                try {
                    Integer key = Integer.parseInt(parts[0]);
                    Student stu = new Student(parts, sub);
                    st.put(key, stu);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format");
        }
    }

    public static void main(String[] args) {
        In in = new In(new File("students2.csv"));
        Lop lop = new Lop(in);
        for (Integer ma : st.keys()) {
            Student student = st.get(ma);
            student.tinh_diemTB();
            System.out.println(student);
        }
        int s = 1007;
        Student sv = st.get(s);
        for (String m : sv.bangDiem().keys()) {
            StdOut.println(m + " " + sv.bangDiem().get(m));
        }
        // In in = new In(new File("students.csv"));
        // Lop lop = new Lop(in);
        // for (Integer ma : st.keys()) {
        // Student stu = st.get(ma);
        // System.out.println(stu);
        // }
        // for (String fileName : args){
        // File file = new File(fileName);
        // In in1 = new In(file);
        // String mon = fileName.substring(0,fileName.length()-4);
        // in1.readLine();
        // while (!in1.isEmpty()) {
        // String[] line = in1.readLine().split(",");
        // Integer key = Integer.parseInt(line[0]);
        // Student stu = st.get(key);
        // stu.bangDiem().put(mon,Double.parseDouble(line[1]));
        // }
        // }
        // StdOut.println();
        // for (Integer ma : st.keys()) {
        // Student stu = st.get(ma);
        // stu.tinh_diemTB();
        // System.out.println(stu);
        // }

    }
}