package com.example.webapp.export;

import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.entity.TournamentEntity;
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

public class TournamentDataExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<TournamentEntity> tournamentEntities;

    public TournamentDataExcelExporter(List<TournamentEntity> tournamentEntities) {
        this.tournamentEntities = tournamentEntities;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Tournaments");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "YEAR", style);
        createCell(row, 2, "NUMBER_OF_PARTICIPANTS", style);
        createCell(row, 3, "PRIZE_POOL", style);
        createCell(row, 4, "SPONSORS", style);

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

        for (TournamentEntity tournamentEntity : tournamentEntities) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, tournamentEntity.getId(), style);
            createCell(row, columnCount++, tournamentEntity.getYear(), style);
            createCell(row, columnCount++, tournamentEntity.getNumbOfParticipants(), style);
            createCell(row, columnCount++, tournamentEntity.getPrizePool(), style);
            createCell(row, columnCount++, tournamentEntity.getSponsors(), style);
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
