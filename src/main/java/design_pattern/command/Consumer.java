package design_pattern.command;

/**
 * 餐厅的顾客，发起点餐命令
 * Created by Niki on 2018/4/5 15:25
 */
public class Consumer implements Invoker {
    private Command command ;
    public Consumer(Command command){
        this.command = command;
    }

    @Override
    public void call() {
        System.out.println("我要一份水果沙拉。");
        command.exec();
    }
}
