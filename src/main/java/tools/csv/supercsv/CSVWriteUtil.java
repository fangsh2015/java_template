package tools.csv.supercsv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * CSV写工具
 * Created by Niki on 2020/1/2 20:52
 */
@Slf4j
public class CSVWriteUtil {
    public static ICsvBeanWriter getCsvBeanWriter(String filePath) {
        try {
            return new CsvBeanWriter(getFileWriter(filePath), CsvPreference.STANDARD_PREFERENCE);
        } catch (Exception e) {
            log.error("无法打开写入csv文件");
        }
        return null;
    }

    public static ICsvMapWriter getCsvMapWriter(String filePath) {
        try {
            return new CsvMapWriter(getFileWriter(filePath), CsvPreference.STANDARD_PREFERENCE);
        } catch (Exception e) {
            log.error("无法打开写入csv文件");
        }
        return null;
    }

    public static <T> void writeBean(ICsvBeanWriter writer, T t, String... headers) {
        if (writer == null) {
            log.error("文件写入流为空");
            return;
        }
        try {
            writer.write(t, headers);
        } catch (IOException e) {
            log.error("写入失败");
            throw new RuntimeException("数据写入csv文件失败");
        }
    }

    public static void writeMap(ICsvMapWriter writer, Map<String,  Object> params, String... headers) {
        if (writer == null) {
            log.error("文件写入流为空");
            return;
        }
        try {
            writer.write(params, headers);
        } catch (IOException e) {
            log.error("写入失败");
            throw new RuntimeException("数据写入csv文件失败");
        }
    }

    public void close(ICsvWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("关闭文件写入流异常", e);
            }
        }
    }

    /**
     * 获取文件输出reader
     *
     * @param filePath
     * @return
     */
    private static FileWriter getFileWriter(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("文件路径不允许为空");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            // 文件不存在，创建
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("无法创建写入的csv文件");
            }
        }
        try {
            FileWriter fileReader = new FileWriter(file);
            return fileReader;
        } catch (IOException e) {
            throw new RuntimeException("无法写入csv文件");
        }
    }
}
