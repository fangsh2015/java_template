package design_pattern.command;

/**
 * 做菜命令
 * Created by Niki on 2018/4/5 15:26
 */
public class Cooker implements Command {
    private Receiver receiver;

    public Cooker(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exec() {
        receiver.action();
    }
}
