package com.project.Model;

import java.sql.Timestamp;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.itextpdf.layout.element.Table;
import com.project.Utils.ExportEntity;

public class History extends ExportEntity {
    Timestamp occur_date;
    int action;
    int quantity;
    float price;
    String user_name, product_name;
    int user_id, id;

    public History(int id, int user_id, String product_id, String user_name, String product_name, int action,
            int quantity, float price, Timestamp occur_date) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.user_name = user_name;
        this.product_name = product_name;
        this.action = action;
        this.quantity = quantity;
        this.price = price;
        this.occur_date = occur_date;
    }

    public History(int action, int quantity, float price, int user_id, String product_id) {
        this.action = action;
        this.quantity = quantity;
        this.price = price;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public Timestamp getOccur_date() {
        return occur_date;
    }

    public void setOccur_date(Timestamp occur_date) {
        this.occur_date = occur_date;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    String product_id;

    @Override
    protected Table CreatePDFHeader() {
        float[] width = { 100F, 100F, 100F, 30F, 40F, 80F };
        Table t = new Table(width);
        t.addCell(CreateCell("Username"));
        t.addCell(CreateCell("Date"));
        t.addCell(CreateCell("Product"));
        t.addCell(CreateCell("Action"));
        t.addCell(CreateCell("Amount"));
        t.addCell(CreateCell("Price"));
        return t;
    }

    @Override
    protected void CreateExcelHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 7 * 256);
        sheet.setColumnWidth(4, 7 * 256);
        sheet.setColumnWidth(5, 13 * 256);
        header.createCell(0).setCellValue("User Name");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Product");
        header.createCell(3).setCellValue("Action");
        header.createCell(4).setCellValue("Amount");
        header.createCell(5).setCellValue("Price");
    }

    @Override
    protected void CreatePDFRow(Table tab) {
        tab.addCell(CreateCell(user_name));
        tab.addCell(CreateCell(occur_date.toString()));
        tab.addCell(CreateCell(product_name));
        tab.addCell(CreateCell(action == 0 ? "Buy" : "Sell"));
        tab.addCell(CreateCell(quantity));
        tab.addCell(CreateCell(price));
    }

    @Override
    protected void CreateExcelRow(Row row) {
        row.createCell(0).setCellValue(getUser_name());
        row.createCell(1).setCellValue(getOccur_date().toString());
        row.createCell(2).setCellValue(getProduct_name());
        row.createCell(3).setCellValue(action == 0 ? "Buy" : "Sell");
        row.createCell(4).setCellValue(getQuantity());
        row.createCell(5).setCellValue(getPrice());
    }
}
