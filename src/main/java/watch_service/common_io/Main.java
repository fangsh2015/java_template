package watch_service.common_io;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created by Niki on 2018/5/4 11:23
 */
public class Main {
    public static void main(String[] args) {
        //构造观察类，提供需要观察的文件或目录，以及filter
        FileAlterationObserver observer = new FileAlterationObserver(new File("E://test"), new FileFilterImpl());
        //监听类，监听文件的变化
        FileLitenerAdaptor listener = new FileLitenerAdaptor();
        //观察者添加监听器
        observer.addListener(listener);
        //配置Monitor，第一个参数单位为毫秒，为监听间隔；第二个参数就是绑定我们之前的观察对象
        FileAlterationMonitor monitor = new FileAlterationMonitor(5000, new FileAlterationObserver[]{observer});
        //启动监听
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
