package com.marsa.backendstageapplication.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsa.backendstageapplication.response.Answer;
import com.marsa.backendstageapplication.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static org.apache.poi.ss.util.CellUtil.createCell;

import org.json.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FormResponseExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Form form;

    public FormResponseExcelExporter(Form form) {
        this.form = form;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() throws JsonProcessingException {
        sheet = workbook.createSheet("Reponses");

        Row row = sheet.createRow(0);

        System.out.println(this.form.getResponses().get(0).getResponseString());
        ObjectMapper mapper = new ObjectMapper();
            Answer[] answers = mapper.readValue(this.form.getResponses().get(0).getResponseString(), Answer[].class);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User Id", style);
        createCell(row, 1, "Nom d'Utilisateur", style);
        createCell(row, 2, "Nom", style);
        createCell(row, 3, "Prénom", style);
        createCell(row, 4, "Date de réponse", style);
        int col = 5;
        for (Answer var: answers) {
            createCell(row, col, var.getLabel() , style);
            col++;
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        }else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() throws JsonProcessingException {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Response var : this.form.getResponses()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, var.getUser().getId(), style);
            createCell(row, columnCount++, var.getUser().getUsername(), style);
            createCell(row, columnCount++, var.getUser().getFirstName(), style);
            createCell(row, columnCount++, var.getUser().getLastName(), style);
            createCell(row, columnCount++, var.getDate().toString(), style);

            ObjectMapper mapper = new ObjectMapper();
            Answer[] answers = mapper.readValue(var.getResponseString(), Answer[].class);
            for (Answer answer: answers) {
                createCell(row, columnCount++, answer.getAnswer(), style);
            }
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
