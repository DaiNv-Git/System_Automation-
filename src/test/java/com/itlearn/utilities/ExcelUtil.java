package com.itlearn.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class ExcelUtil {

    public static void writeResultToExcel(String testName, String status, String filePath) {
        try {
            File file = new File(filePath);
            Workbook workbook;
            Sheet sheet;

            // Nếu file chưa tồn tại, tạo mới
            if (!file.exists()) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("TestResults");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Test Case");
                header.createCell(1).setCellValue("Status");
            } else {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet("TestResults");
                fis.close();
            }

            // Ghi thêm dòng kết quả
            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(testName);
            row.createCell(1).setCellValue(status);

            // Lưu file
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
