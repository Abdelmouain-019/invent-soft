package com.project.Model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.itextpdf.layout.element.Table;
import com.project.Utils.ExportEntity;

public class Category extends ExportEntity {
    private String name;
    private int id;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category(String name, int id, int quantity) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected Table CreatePDFHeader() {
        float[] width = { 100F, 100F, 150F };
        Table t = new Table(width);
        t.addCell(CreateCell("Id"));
        t.addCell(CreateCell("Name"));
        t.addCell(CreateCell("Critical Quantity"));
        return t;
    }

    @Override
    protected void CreateExcelHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Critical Quantity");
    }

    @Override
    protected void CreatePDFRow(Table tab) {
        tab.addCell(CreateCell(id));
        tab.addCell(CreateCell(name));
        tab.addCell(CreateCell(quantity));
    }

    @Override
    protected void CreateExcelRow(Row row) {
        row.createCell(0).setCellValue(getId());
        row.createCell(1).setCellValue(getName());
        row.createCell(2).setCellValue(getQuantity());
    }

}
