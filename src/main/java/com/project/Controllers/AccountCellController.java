package com.project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

import com.project.Database.CategoryDatabase;
import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Model.User;
import com.project.Utils.FileHandler;
import com.project.Utils.PopUpWindows;

public class AccountCellController extends ListCell<User> {
    @FXML
    private Label user_name;
    @FXML
    private Label full_name;
    @FXML
    private Button delete_btn;

    private HBox content;

    public AccountCellController() {
        try {
            FXMLLoader fxml = FileHandler.loadFXML("AccountCell");
            fxml.setController(this);
            content = fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (user == null || empty) {
            setGraphic(null);
        } else {
            user_name.setText(user.getUserName());
            full_name.setText(user.getFirstName() + " " + user.getLastName());
            delete_btn.setOnAction(event -> {
                if (PopUpWindows.DialogConfirm("Delete User", "Are you Sure?",
                        "All of their history is gonna be deleted!!!")) {
                    if (UsersDatabse.delete_user((user.getId())) == Errors.SUCCESS) {
                        AccountsController.Fetch();
                        PopUpWindows.ShowMsg(Errors.SUCCESS.getMsg(), AlertType.INFORMATION);
                    } else {
                        PopUpWindows.ShowMsg(Errors.GLOBAL_ERROR.getMsg(), AlertType.ERROR);
                    }
                }
            });
            setGraphic(content);
        }
    }

}
