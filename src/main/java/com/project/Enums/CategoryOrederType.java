package com.project.Enums;

public enum CategoryOrederType {
    CreationASC("id ASC"),
    CreationDESC("id DESC"),
    NameASC("name ASC"),
    NameDESC("name DESC"),
    QuantityASC("critical_quantity ASC"),
    QuantityDESC("critical_quantity DESC");

    private final String text;

    public String getSqlName() {
        return text;
    }

    CategoryOrederType(String text) {
        this.text = text;
    }
}
