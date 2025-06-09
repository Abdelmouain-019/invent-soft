package com.project.Model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.project.Utils.ExportEntity;

public class Product extends ExportEntity {

    private int critical, cat_id;

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    private int quantity;
    private String id, name, cat_name;
    private float selling_price, buying_price;
    private byte[] image_data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public float getBuing_price() {
        return buying_price;
    }

    public void setBuing_price(float buying_price) {
        this.buying_price = buying_price;
    }

    public Product(String id, String name, int quantity, int critical, int cat_id, String cat_name, float selling_price,
            float buying_price, byte[] inputStream) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.critical = critical;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.selling_price = selling_price;
        this.buying_price = buying_price;
        this.image_data = inputStream;
    }

    public Product() {
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }

    @Override
    protected void CreateExcelRow(Row row) {

        row.createCell(0).setCellValue(getId());
        row.createCell(1).setCellValue(getName());
        row.createCell(2).setCellValue(getCat_name());
        row.createCell(3).setCellValue(getQuantity());
        row.createCell(4).setCellValue(getBuing_price());
        row.createCell(5).setCellValue(getSelling_price());
        row.createCell(6).setCellValue(getCat_id());
    }

    @Override
    protected void CreateExcelHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 9 * 256);
        sheet.setColumnWidth(4, 13 * 256);
        sheet.setColumnWidth(5, 13 * 256);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Category");
        header.createCell(3).setCellValue("Quantity");
        header.createCell(4).setCellValue("Buying Price");
        header.createCell(5).setCellValue("Selling Price");
    }

    @Override
    protected Table CreatePDFHeader() {
        float[] width = { 100F, 100F, 100F, 50F, 50F, 50F };
        Table t = new Table(width);
        t.addCell(CreateCell("Id"));
        t.addCell(CreateCell("Name"));
        t.addCell(CreateCell("Category"));
        t.addCell(CreateCell("Quantity"));
        t.addCell(CreateCell("Buying Price"));
        t.addCell(CreateCell("Selling Price"));
        return t;
    }

    @Override
    protected void CreatePDFRow(Table tab) {
        tab.addCell(CreateCell(id));
        tab.addCell(CreateCell(name));
        tab.addCell(CreateCell(cat_name));
        tab.addCell(CreateCell(quantity));
        tab.addCell(CreateCell(buying_price));
        tab.addCell(CreateCell(selling_price));
    }

}
