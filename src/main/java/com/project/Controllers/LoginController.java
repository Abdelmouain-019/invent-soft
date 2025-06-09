package com.project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameFiled;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Text errorMsg;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        errorMsg.setText("");
        loginBtn.setOnAction(event -> {
            String userName = usernameFiled.getText();
            String password = passwordField.getText();
            Errors err = UsersDatabse.login(userName, password);
            if (err != Errors.SUCCESS) {
                errorMsg.setText(err.getMsg());
            }
        });
    }

}
