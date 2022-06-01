package com.example.webapp.export;

import com.example.webapp.entity.ParticipantEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ParticipantDataExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ParticipantEntity> participantEntities;

    public ParticipantDataExcelExporter(List<ParticipantEntity> participantEntities) {
        this.participantEntities = participantEntities;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Participants");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "NAME", style);
        createCell(row, 2, "B-YEAR", style);
        createCell(row, 3, "RATING", style);
        createCell(row, 4, "COUNTRY", style);
        createCell(row, 5, "NICKNAME", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else if (value instanceof Double){
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (ParticipantEntity participantEntity : participantEntities) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, participantEntity.getId(), style);
            createCell(row, columnCount++, participantEntity.getName(), style);
            createCell(row, columnCount++, participantEntity.getbYear(), style);
            createCell(row, columnCount++, participantEntity.getRating(), style);
            createCell(row, columnCount++, participantEntity.getCountry().getName(), style);
            createCell(row, columnCount++, participantEntity.getLichessName(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
