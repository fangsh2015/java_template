package concurrent.lock;

/**
 * Created by Niki on 2018/6/30 10:27
 */
public class SynchronizeDemo {
    private String flag ;

    public void changeFlag(String newFlag) {
        synchronized (this) {
            flag = newFlag;
        }
    }

}
