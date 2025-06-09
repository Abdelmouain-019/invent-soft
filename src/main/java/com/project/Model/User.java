package com.project.Model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.itextpdf.layout.element.Table;
import com.project.Utils.ExportEntity;

public class User extends ExportEntity {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;

    public User(int id, String userName, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return this.id == 1;
    }

    @Override
    protected Table CreatePDFHeader() {
        float[] width = { 50F, 100F, 100F, 100F };
        Table t = new Table(width);
        t.addCell(CreateCell("Id"));
        t.addCell(CreateCell("First Name"));
        t.addCell(CreateCell("Last Name"));
        t.addCell(CreateCell("Username"));
        return t;
    }

    @Override
    protected void CreateExcelHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Username");
    }

    @Override
    protected void CreatePDFRow(Table tab) {
        tab.addCell(CreateCell(id));
        tab.addCell(CreateCell(firstName));
        tab.addCell(CreateCell(lastName));
        tab.addCell(CreateCell(userName));
    }

    @Override
    protected void CreateExcelRow(Row row) {
        row.createCell(0).setCellValue(getId());
        row.createCell(1).setCellValue(getFirstName());
        row.createCell(2).setCellValue(getLastName());
        row.createCell(3).setCellValue(getUserName());
    }
}
