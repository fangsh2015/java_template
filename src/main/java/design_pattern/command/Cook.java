package design_pattern.command;

/**
 * 厨师类
 * Created by Niki on 2018/4/5 15:28
 */
public class Cook implements Receiver{
    @Override
    public void action() {
        System.out.println("厨师：马上做菜！");
    }
}
