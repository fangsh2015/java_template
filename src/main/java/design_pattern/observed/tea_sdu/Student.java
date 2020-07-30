package design_pattern.observed.tea_sdu;

import java.util.Observable;
import java.util.Observer;

/**
 * 学生对象，作为观察者，观察老师布置作业
 * Created by Niki on 2020/5/12 19:52
 */
public class Student implements Observer {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    /**
     * 学生观察到（被通知）老师布置作业后的动作
     *
     * @param observable
     * @param homework
     */
    @Override
    public void update(Observable observable, Object homework) {
        Teacher teacher = (Teacher) observable;
        String h = String.format("%s收到了老师：%s布置的作业《%s》", this.name, teacher.getName(), homework);
        System.out.println(h);
    }

}
