package com.project.Enums;

public enum Errors {
    SUCCESS("Success"),
    GLOBAL_ERROR("Something Went Wrong"),
    USER_NOT_FOUND("User Not Found"),
    PASSWORD_WRONG("The Password is worng"),
    PASSWORD_EMPTY("Password can't be Empty"),
    PASSWORD_SHORT("Password shold be at least 8 characters long"),
    NAME_SHORT("Your name is too short"),
    FETCH_ERROR("Couldn't Fetch Data"),
    CAN_NOT_DELETE_ADMIN("Can not Delete Admin Account"),
    UNAUTHERAIZE("Unautheraized"),
    INVALID_PRODUCT_ID("Invalid product error"),
    NOT_ENOUGH("You Don't Have Enough"),
    PERMISSION_DENIED("Permission denied"),
    PASSWORD_DIFFRENCE("The Passwords should be the same");

    private final String ErrorMessage;

    Errors(String s) {
        ErrorMessage = s;
    }

    public String getMsg() {
        return ErrorMessage;
    }
}
