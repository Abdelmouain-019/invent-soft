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
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Utils.FileHandler;
import com.project.Utils.PopUpWindows;

public class CategoryCellController extends ListCell<Category> {
    @FXML
    private Label name;
    @FXML
    private Label quantity;
    @FXML
    private Button change_btn;
    @FXML
    private Button delete_btn;

    private HBox content;

    public CategoryCellController() {
        try {
            FXMLLoader fxml = FileHandler.loadFXML("CategoryCell");
            fxml.setController(this);
            content = fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (category == null || empty) {
            setGraphic(null);
        } else {
            name.setText(category.getName());
            quantity.setText(category.getQuantity() + "");
            change_btn.setOnAction(event -> {
                Optional<String> result = PopUpWindows.DialogTextField("Change Category Name", "Enter Category Name",
                        "Name ");
                result.ifPresent(name -> {
                    name = name.trim();
                    if (name.length() < 3) {
                        PopUpWindows.ShowMsg(Errors.NAME_SHORT.getMsg(), AlertType.ERROR);
                        return;
                    }
                    Errors err = CategoryDatabase.changeName(category.getId(), name);
                    if (err == Errors.SUCCESS) {
                        CategoryController.Fetch();
                        PopUpWindows.ShowMsg(err.getMsg(), AlertType.INFORMATION);
                    } else {

                        PopUpWindows.ShowMsg(err.getMsg(), AlertType.ERROR);
                    }
                });
            });
            delete_btn.setOnAction(event -> {
                if (PopUpWindows.DialogConfirm("Delete Category", "Are you Sure?", "Select")) {
                    if (CategoryDatabase.deleteCategory((category.getId())) == Errors.SUCCESS) {
                        CategoryController.Fetch();
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
