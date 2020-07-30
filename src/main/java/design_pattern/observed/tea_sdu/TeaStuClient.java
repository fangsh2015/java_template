package design_pattern.observed.tea_sdu;

/**
 * Created by Niki on 2020/5/12 20:01
 */
public class TeaStuClient {
    public static void main(String[] args) {
        Student s1 = new Student("张梁宇");
        Student s2 = new Student("松溪");

        Teacher teacher = new Teacher("老紫");
        teacher.addObserver(s1);
        teacher.addObserver(s2);

        teacher.layoutHomework("背诵我的父亲");
    }
}
