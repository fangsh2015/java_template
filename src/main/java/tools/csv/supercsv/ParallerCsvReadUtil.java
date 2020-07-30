package tools.csv.supercsv;

import com.google.common.collect.Lists;
import org.supercsv.io.ICsvBeanReader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 并行CSV读取工具类
 * 采用线程池的方式批量处理csv中的文件
 * Created by Niki on 2020/1/3 10:04
 */
public class ParallerCsvReadUtil extends CSVReadUtil {
    /**
     * 默认使用五个线程的线程池处理并发请求
     */
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 并行处理csv文件，没有返回值
     *
     * @param filePath
     * @param executorService
     * @param handler
     */
    public static <T> void parallerHandlerBean(String filePath, Class<T> clazz, ExecutorService executorService, CSVHandler handler, String... headers) {
        final ICsvBeanReader reader = getCsvBeanReader(filePath);
        if (executorService == null) {
            executorService = ParallerCsvReadUtil.executorService;
        }
        readHeader(reader);

        // 处理所有的csv行
        while( dealCsvBean(executorService, clazz, reader, handler, headers)){}

    }


    private static <T> boolean dealCsvBean(ExecutorService executorService, Class<T> clazz, ICsvBeanReader reader, CSVHandler handler, String[] header) {
        int index = 0;
        boolean status = false;
        T csvBean;
        while ((csvBean = read2Bean(reader, clazz, header)) != null) {// 这里从第一行开始取数据
            index++;
            T finalCsvBean = csvBean;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler.handler(finalCsvBean);
                }
            });
            //每次读取的行数，每个线程处理的记录数,根据实际情况修改
            if (index == 10) {
                status = true;
                break;
            }
        }
        return status;
    }

}
