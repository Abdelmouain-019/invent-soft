package com.project.Enums;

public enum HistoryOrederType {
    CreationASC("History.occur_date ASC"),
    CreationDESC("History.occur_date DESC"),
    UsernameASC("User.name ASC"),
    UsernameDESC("User.name DESC"),
    QuantityASC("History.quantity ASC"),
    QuantityDESC("History.quantity DESC"),
    PriceASC("History.selling_price ASC"),
    PriceDESC("History.selling_price DESC"),
    ProductNameASC("Product.name ASC"),
    ProductNameDESC("Product.name DESC");

    private final String text;

    public String getSqlName() {
        return text;
    }

    HistoryOrederType(String text) {
        this.text = text;
    }
}
