package watch_service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

/**
 * Created by Niki on 2018/5/4 15:46
 */
public class PropertiesWatchServiceDemo {
    //配置文件的路径，可通过上下文ClassPathResource获取
    private static String filepath = "E://application.properties";
    private static String filename = "application.properties";
    private static WatchService watchService = null;
    //该配置文件对象是全局的，应该设置为单利的bean，这里应该注入进来
    private static Properties properties;

    private static void watch() {

        try {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(filepath).register(watchService, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //开启线程，一直监控文件的变化，文件改变了，则重新加载全局的属性对象
        Thread watchThread = new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take();
                    for (WatchEvent event : key.pollEvents()) {
                        if (event.context().equals(filename)) {
                            properties.load(new FileInputStream(filepath));
                            break;
                        }
                    }
                    key.reset();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        //将线程设为守护线程
        watchThread.setDaemon(true);
        //开启文件监控
        watchThread.start();

        //设置回调钩子，JVM停止的时候关闭线程
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                watchService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
