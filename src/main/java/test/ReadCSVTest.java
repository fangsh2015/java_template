package test;

import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Niki on 2018/4/12 17:17
 */
public class ReadCSVTest {
    private static String path = "D:\\peopleDemo.csv";

    public static void main(String[] args) {
        CsvReader reader = null;
        try {
            reader = new CsvReader(path,',', Charset.forName("GBK"));
            reader.readHeaders();
            while (reader.readRecord()) {
                System.out.println(reader.get(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            reader.close();
        }

//        testLineNumberReader("C:\\Users\\nikifang\\Desktop\\工作笔记\\老商户数据导入\\数据\\updateAgentScene.csv");
    }

    public static void testLineNumberReader(String filepath) {
        try {
            File file = new File(filepath);
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            for (int i = 0; i < 4; i++) {

                String line = lineNumberReader.readLine();
                System.out.println(line);
            }
            lineNumberReader.setLineNumber(2);
            String line = lineNumberReader.readLine();
            System.out.println(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
