package watch_service.common_io;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Niki on 2018/5/4 11:23
 */
public class FileFilterImpl implements FileFilter {
    /**
     * 返回false则文件不监听
     * @param pathname
     * @return
     */
    @Override
    public boolean accept(File pathname) {
//        System.out.println("file_filter: "+pathname);
        return true;
    }
}
