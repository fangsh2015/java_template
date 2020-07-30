package watch_service;

import java.io.IOException;
import java.nio.file.*;

/**
 * java文件监听demo
 * Created by Niki on 2018/5/4 8:57
 */
public class WatchServiceDemo {
    public static void main(String[] args) {
        try {
            //获取文件系统WatchService对象
            WatchService watchService = FileSystems.getDefault().newWatchService();
            //为指定目录路径注册监听
            Paths.get("E:\\test").register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                //获取下一个文件变化时间
                try {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        System.out.println(event.context() + "文件发生了" + event.kind() + "事件");
                    }
                    if (!key.reset()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
