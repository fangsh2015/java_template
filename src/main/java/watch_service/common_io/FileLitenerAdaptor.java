package watch_service.common_io;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created by Niki on 2018/5/4 11:20
 */
public class FileLitenerAdaptor extends FileAlterationListenerAdaptor {
    @Override
    public void onStart(FileAlterationObserver observer) {
//        System.out.println("********** begin listener");
        super.onStart(observer);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("********** directory"+directory.getAbsolutePath()+" is create");
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("********** directory"+directory.getAbsolutePath()+" is change");
        super.onDirectoryChange(directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("********** directory"+directory.getAbsolutePath()+" is delete");
        super.onDirectoryDelete(directory);
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("********** file"+file.getAbsolutePath()+" is create");
        super.onFileCreate(file);
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("********** file"+file.getAbsolutePath()+" is change");
        super.onFileChange(file);
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("********** file"+file.getAbsolutePath()+" is delete");
        super.onFileDelete(file);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
//        System.out.println("********** end listener");
        super.onStop(observer);
    }
}
