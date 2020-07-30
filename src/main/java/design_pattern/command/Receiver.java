package design_pattern.command;

/**
 * 命令的接收者
 * Created by Niki on 2018/4/5 15:23
 */
public interface Receiver {
    /**
     * 收到命令作出的行动
     */
    void action();
}
