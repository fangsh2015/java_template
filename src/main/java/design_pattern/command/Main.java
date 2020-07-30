package design_pattern.command;

/**
 * Created by Niki on 2018/4/5 15:29
 */
public class Main {
    public static void main(String[] args) {
        //命令执行者——厨师
        Receiver cook = new Cook();

        //做菜命令
        Command cooker = new Cooker(cook);

        //顾客对象，用于发布做菜命令到厨师
        Invoker consumer = new Consumer(cooker);

        consumer.call();
    }
}
