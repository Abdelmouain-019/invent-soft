package com.project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * CreateAccountDialogController
 */
public class CreateAccountDialogController implements Initializable {
    @FXML
    private TextField first_name_field;
    @FXML
    private TextField last_name_field;
    @FXML
    private TextField user_name_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button add_btn;
    @FXML
    private Label error_text;

    private final String errorClassName = "error_textfield";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        error_text.setText("");
        first_name_field.focusedProperty()
                .addListener((a, b, c) -> first_name_field.getStyleClass().remove(errorClassName));
        last_name_field.focusedProperty()
                .addListener((a, b, c) -> last_name_field.getStyleClass().remove(errorClassName));
        user_name_field.focusedProperty()
                .addListener((a, b, c) -> user_name_field.getStyleClass().remove(errorClassName));
        password_field.focusedProperty()
                .addListener((a, b, c) -> password_field.getStyleClass().remove(errorClassName));

        add_btn.setOnAction(e -> {
            error_text.setText("");
            String Fname = first_name_field.getText().trim();
            if (Fname.length() < 3) {
                first_name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.NAME_SHORT.getMsg());
                return;
            }
            String Lname = last_name_field.getText().trim();
            if (Lname.length() < 3) {
                last_name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.NAME_SHORT.getMsg());
                return;
            }
            String Uname = user_name_field.getText().trim();
            if (Uname.length() < 3) {
                user_name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.NAME_SHORT.getMsg());
                return;
            }
            String password = password_field.getText();
            if (password.isBlank()) {
                if (PopUpWindows.ShowResult(UsersDatabse.add_user(Uname, Fname, Lname))) {
                    Stage s = (Stage) first_name_field.getScene().getWindow();
                    s.close();
                }
            }
            if (password.length() < 8) {
                password_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.PASSWORD_SHORT.getMsg());
                return;
            }
            if (PopUpWindows.ShowResult(UsersDatabse.add_user(Uname, Fname, Lname, password))) {
                Stage s = (Stage) first_name_field.getScene().getWindow();
                s.close();
            }
        });
    }

}
