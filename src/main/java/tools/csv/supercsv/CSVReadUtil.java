package tools.csv.supercsv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * csv的读取工具类
 * Created by Niki on 2019/12/30 10:09
 */
@Slf4j
public class CSVReadUtil {

    /**
     * 获得数据行转化为bean的csv阅读器
     *
     * @param filePath 文件绝对路径
     * @return ICsvBeanReader
     */
    public static ICsvBeanReader getCsvBeanReader(String filePath) {
        return new CsvBeanReader(getFileReader(filePath), CsvPreference.STANDARD_PREFERENCE);
    }

    /**
     * 获得数据行转化为Map的csv阅读器
     *
     * @param filePath 文件绝对路径
     * @return ICsvMapReader
     */
    public static ICsvMapReader getCsvMapReader(String filePath) {
        return new CsvMapReader(getFileReader(filePath), CsvPreference.STANDARD_PREFERENCE);
    }


    /**
     * 读取csv文件的头信息
     *
     * @param reader
     * @return
     */
    public static String[] readHeader(ICsvReader reader) {
        if (reader != null) {
            try {
                reader.getHeader(true);
            } catch (IOException e) {
                log.error("读取csv文件头异常", e);
            }
        }
        return null;
    }

    /**
     * 读取文件
     *
     * @param reader  csv读取器
     * @param headers 每一列对应的key
     * @return Map<String                               ,                                                               String> / null csv文件读取完毕
     */
    public static Map<String, String> read2Map(ICsvMapReader reader, String... headers) {
        try {
            return reader.read(headers);
        } catch (IOException e) {
            throw new RuntimeException("读取csv文件异常");
        }
    }

    public static <T> T read2Bean(ICsvBeanReader reader, Class<T> clazz, String... headers) {
        try {
            reader.getHeader(true);
            return reader.read(clazz, headers);
        } catch (IOException e) {
            throw new RuntimeException("读取csv文件异常");
        }
    }

    /**
     * 关闭csv文件阅读器
     *
     * @param reader
     */
    public void close(ICsvReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("关闭文件阅读器异常！", e);
            }
        }
    }

    /**
     * 获取文件输出reader
     *
     * @param filePath 文件绝对路径
     * @return FileReader
     */
    private static FileReader getFileReader(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("文件路径不允许为空");
        }
        try {
            FileReader fileReader = new FileReader(filePath);
            return fileReader;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("需读取的csv文件不存在");
        }
    }
}
