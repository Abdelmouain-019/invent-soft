package com.project.Enums;

public enum ProductsOrederType {
    CreationASC("Product.id ASC"),
    CreationDESC("Product.id DESC"),
    NameASC("Product.name ASC"),
    NameDESC("Product.name DESC"),
    QuantityASC("Product.quantity ASC"),
    QuantityDESC("Product.quantity DESC"),
    SellingPriceASC("Product.selling_price ASC"),
    SellingPriceDESC("Product.selling_price DESC"),
    BuingPriceASC("Product.buing_price ASC"),
    BuingPriceDESC("Product.buing_price DESC");

    private final String text;

    public String getSqlName() {
        return text;
    }

    ProductsOrederType(String text) {
        this.text = text;
    }
}
