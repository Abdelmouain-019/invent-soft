package com.project.Enums;

public enum AccountsOrederType {
    CreationASC("id ASC"),
    CreationDESC("id DESC"),
    UserNameASC("user_name ASC"),
    UserNameDESC("user_name DESC"),
    FirstNameASC("first_name ASC"),
    FirstNameDESC("first_name DESC"),
    LastNameASC("last_name ASC"),
    LastNameDESC("last_name DESC");

    private final String text;

    public String getSqlName() {
        return text;
    }

    AccountsOrederType(String text) {
        this.text = text;
    }
}
