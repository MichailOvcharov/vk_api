package ru.omb.vk_api.services.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.omb.vk_api.entity.UserInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public void fillDataExcelFile(List<UserInfo> userInfoList)  {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Пользователи");

        int rowNum = 0;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);


        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("id Пользователя");
        row.createCell(1).setCellValue("Имя");
        row.createCell(2).setCellValue("Фамилия");
        row.createCell(3).setCellValue("Дата рождения");
        row.createCell(4).setCellValue("Телефон");
        row.createCell(5).setCellValue("Город");

        // Устанавливаем жирный стиль заголовка таблицы
        for(int i = 0; i < row.getLastCellNum(); i++){
            row.getCell(i).setCellStyle(style);
        }

        for (UserInfo userInfo : userInfoList) {
            createSheetHeader(workbook, sheet, ++rowNum, userInfo);
        }
        // Устанавливаем размер колонок по ширине текста
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "out.xlsx";

        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException fnfe ) {
            fnfe.getMessage();
        } catch ( IOException eio) {
            eio.getMessage();
        }
    }

    private static void createSheetHeader(XSSFWorkbook workbook, XSSFSheet sheet, int rowNum, UserInfo userInfo) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(userInfo.getUserId());
        row.createCell(1).setCellValue(userInfo.getUserFirstName());
        row.createCell(2).setCellValue(userInfo.getUserLastName());
        // Устанавливаем стиль для ячейки с датой рождения
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yyyy"));
        Cell cell = row.createCell(3);
        cell.setCellValue(userInfo.getUserBirthdate());
        cell.setCellStyle(cellStyle);
        row.createCell(4).setCellValue(userInfo.getUserContacts());
        row.createCell(5).setCellValue(userInfo.getUserCity());
    }
}
