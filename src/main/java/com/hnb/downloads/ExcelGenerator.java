package com.hnb.downloads;

import com.hnb.app.models.Tecajevi;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGenerator {

    public static ByteArrayInputStream tecajeviToExcel(List<Tecajevi> tecajevi) throws IOException {

        String[] COLUMNs = {"broj_tecajnice", "datum_primjene", "drzava", "drzava_iso", "sifra_valute", "valuta", "jedinica", "kupovni_tecaj", "srednji_tecaj", "prodajni_tecaj"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Tecajevi");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();

            // Row for header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();

            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;
            for (Tecajevi tecaj : tecajevi) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(tecaj.getBroj_tecajnice());
                row.createCell(1).setCellValue(tecaj.getDatum_primjene().toString());
                row.createCell(2).setCellValue(tecaj.getDrzava());
                row.createCell(3).setCellValue(tecaj.getDrzava_iso());
                row.createCell(4).setCellValue(tecaj.getSifra_valute());
                row.createCell(5).setCellValue(tecaj.getValuta());
                row.createCell(6).setCellValue(tecaj.getJedinica());
                row.createCell(7).setCellValue(tecaj.getKupovni_tecaj());
                row.createCell(8).setCellValue(tecaj.getSrednji_tecaj());
                row.createCell(9).setCellValue(tecaj.getProdajni_tecaj());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
