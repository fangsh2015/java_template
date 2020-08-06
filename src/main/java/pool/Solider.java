package pool;

import lombok.Data;

/**
 * 士兵
 * 要池化的对象，
 * Created by Niki on 2020/8/6 13:00
 */
@Data
public class Solider {
    private boolean service;

    public void enlist() {
        this.service = true;
    }

    /**
     * 退伍方法
     */
    public void retired() {
        this.service = false;
        System.out.println("我退伍了");
    }
}
