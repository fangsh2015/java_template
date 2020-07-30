package tools;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 利用poi操作excel工具类
 * Created by Niki on 2018/11/21 11:00
 */
public class ExcelUtil {
    public void readFile(String filePath) {
        try {
            // 获取文件流
            InputStream is = new FileInputStream(filePath);

            // 获取Excel工作对象
            HSSFWorkbook workbook = new HSSFWorkbook(is);

            // 获取Excel工作表对象
            HSSFSheet sheet = workbook.getSheetAt(0);

            // 循环读取Excel表格数据
            for (Row row : sheet) {
                // 跳过表头
                if (row.getRowNum() == 0) {
                    continue;
                }
                // 读取单元格数据
                String valOne = row.getCell(0).getStringCellValue();
                System.out.println(String.format("第%d行第1个单元格数据为：%s",row.getRowNum(),valOne));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
