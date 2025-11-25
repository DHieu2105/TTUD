import edu.princeton.cs.algs4.Date;
import java.util.ArrayList;

public class Student {
    int id;
    String name;
    Date date;
    simpleTable<String, Double> score;
    ArrayList<String> subjects;
    Double average = 0.0;

    public Student(int id, String name, Date date, simpleTable score) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.score = score;
    }
    public Student(String[] tokens) throws NumberFormatException {
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
        date = new Date(tokens[2]);
        subjects = new ArrayList<>();
    }
    public Student(String[] tokens,String[] sub) throws NumberFormatException {
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
        date = new Date(tokens[2]);
        score = new simpleTable();
        subjects = new ArrayList<>();
        for (String s : sub) {
            subjects.add(s);
        }
        int i=3;
        for (String x : subjects) {
            score.put(x, Double.parseDouble(tokens[i]));
            i++;
        }
    }

    public double tinh_diemTB() {
        double dtb = 0;
        int count = 0;
        for (String x : score.keys()) {
            dtb += score.get(x);
            count++;
        }
        return this.average = dtb/count;
    }

    public simpleTable<String, Double> bangDiem() { return score; }

    public String bangD() {
        String bangDiem = "";
        for (String x : score.keys()) {
            bangDiem += x + ": " + score.get(x) + " , ";
        }
        return bangDiem;
    }

    @Override
    public String toString() {
        //return "Student [id=" + id + ", name=" + name + ", date=" + date + "          " + average ;
        return String.format("%-10d %-20s %02d/%02d/%-10d  %-10f",id,name,date.day(),date.month(),date.year(),average);
    }
}