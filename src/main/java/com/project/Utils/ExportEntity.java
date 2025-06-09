package com.project.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.project.Enums.Errors;

public abstract class ExportEntity {

    protected static Cell CreateCell(String s) {
        if (s == null)
            return new Cell().add(new Paragraph("Null"));
        return new Cell().add(new Paragraph(s));
    }

    protected static Cell CreateCell(Integer s) {
        if (s == null)
            return new Cell().add(new Paragraph("Null"));
        return new Cell().add(new Paragraph(String.valueOf(s)));
    }

    protected static Cell CreateCell(Float s) {
        if (s == null)
            return new Cell().add(new Paragraph("Null"));
        return new Cell().add(new Paragraph(String.valueOf(s)));
    }

    public static void CreatePDFFile(List<ExportEntity> list, File file) {
        if (list.isEmpty()) {
            PopUpWindows.ShowResult(Errors.SUCCESS);
            return;
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document doc = new Document(pdfDocument);
            doc.setBackgroundColor(ColorConstants.WHITE);

            Table t = list.get(0).CreatePDFHeader();
            for (ExportEntity e : list)
                e.CreatePDFRow(t);

            doc.add(t);
            doc.close();

            PopUpWindows.ShowResult(Errors.SUCCESS);
        } catch (Exception err) {
            err.printStackTrace();
            PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
        }
    }

    public static void CreateExcelSheet(List<ExportEntity> list, File file) {
        if (list.isEmpty()) {
            PopUpWindows.ShowResult(Errors.SUCCESS);
            return;
        }
        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DataSheet");
            list.get(0).CreateExcelHeader(sheet);
            int i = 1;
            for (ExportEntity p : list) {
                Row r = sheet.createRow(i);
                p.CreateExcelRow(r);
                i++;
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                PopUpWindows.ShowResult(Errors.SUCCESS);
                out.close();
                workbook.close();
            }

        } catch (Exception err) {
            PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
        }
    }

    protected abstract Table CreatePDFHeader();

    protected abstract void CreateExcelHeader(Sheet sheet);

    protected abstract void CreatePDFRow(Table tab);

    protected abstract void CreateExcelRow(Row row);

}
