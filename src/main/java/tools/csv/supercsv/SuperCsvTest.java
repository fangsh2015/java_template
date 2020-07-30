package tools.csv.supercsv;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Niki on 2019/12/27 16:47
 */
public class SuperCsvTest {
    private static String filePath = "datas/csv/agent.csv";

    private static String fileWritePath = "data/csv/testWrite.csv";


    public void readBean() {
        final String path = this.getClass().getClassLoader().getResource(filePath).getPath();
        try {
            ICsvBeanReader reader = new CsvBeanReader(new FileReader(path), CsvPreference.STANDARD_PREFERENCE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readMap() {
        final String path = this.getClass().getClassLoader().getResource(filePath).getPath();
        try {
            ICsvMapReader reader = new CsvMapReader(new FileReader(path), CsvPreference.STANDARD_PREFERENCE);
            reader.length();
            reader.getHeader(false);
            final Map<String, String> read = reader.read("1", "2");
            System.out.println(read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testWrite() {
        final String path = this.getClass().getClassLoader().getResource(fileWritePath).getPath();

    }

    public void testList() {
        final String path = this.getClass().getClassLoader().getResource(filePath).getPath();
        try {
            ICsvListReader reader = new CsvListReader(new FileReader(path), CsvPreference.STANDARD_PREFERENCE);
            final List<String> read = reader.read();
            System.out.println(read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SuperCsvTest().readMap();


    }
}
