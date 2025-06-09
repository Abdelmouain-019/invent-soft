package com.project.Enums;

public enum Pages {
    LOADING("Loading", 0),
    DASHBOARD("DashBoard", 1),
    PRODUCTS("Products", 2),
    CATEGORY("Category", 3),
    PROFILE("Profile", 4),
    ACCOUNTS("Accounts", 5),
    HISTORY("History", 6);

    private final String FileName;
    private final int Index;

    Pages(String s, int i) {
        FileName = s;
        Index = i;
    }

    public String getFileName() {
        return FileName;
    }

    public int getIndex() {
        return Index;
    }
}
