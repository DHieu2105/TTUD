import java.util.Date;

public class Student implements Comparable<Student> {
    private String name;
    private String middleName;
    private Date birthDate;
    private double averageScore;

    public Student(String middleName, String Name, Date birthDate, double averageScore) {
        this.name = Name;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.averageScore = averageScore;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(averageScore, o.averageScore);
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return this.middleName + " " + this.name + ", " + this.birthDate + ", " + this.averageScore;
    }
}