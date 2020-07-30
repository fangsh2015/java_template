package design_pattern.observed.tea_sdu;

import javafx.beans.InvalidationListener;

import java.util.Observable;

/**
 * 教师，被观察者，布置作业（通知学生）
 * Created by Niki on 2020/5/12 19:56
 */
public class Teacher extends Observable {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    /**
     * 布置作业
     *
     * @param homeWork
     */
    public void layoutHomework(String homeWork) {
        // 必须要这一行，否则无法通知成功
        setChanged();
        notifyObservers(homeWork);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
