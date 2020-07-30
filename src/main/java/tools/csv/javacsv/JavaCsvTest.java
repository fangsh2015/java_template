package tools.csv.javacsv;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Niki on 2019/12/27 16:02
 */
public class JavaCsvTest {
    private static String filePath = "datas/csv/agent.csv";


    public  void read() {
        final String path = this.getClass().getClassLoader().getResource(filePath).getPath();
        CsvReader reader = null;
        try {
            reader = new CsvReader(path);
            final boolean b = reader.readHeaders();
            while (reader.readRecord()) {
                final String rawRecord = reader.getRawRecord();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JavaCsvTest javaCsvUtil = new JavaCsvTest();
        javaCsvUtil.read();
    }
}
