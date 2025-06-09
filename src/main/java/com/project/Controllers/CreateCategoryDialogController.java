package com.project.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.Database.CategoryDatabase;
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * CreateAccountDialogController
 */
public class CreateCategoryDialogController implements Initializable {
    @FXML
    private TextField name_field;
    @FXML
    private TextField critical_field;
    @FXML
    private Button add_btn;
    @FXML
    private Label error_text;

    private final String errorClassName = "error_textfield";

    private void OnlyNumbers(TextField field) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // "\\d*" means only digits
                return change;
            }
            return null; // reject the change
        }));
    }

    private void FoucsEvent(TextField field) {
        field.focusedProperty()
                .addListener((a, b, c) -> field.getStyleClass().remove(errorClassName));
    }

    private int ToNumber(String str) {
        if (str.isBlank())
            return 0;
        else
            return Integer.parseInt(str);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        error_text.setText("");
        FoucsEvent(name_field);
        FoucsEvent(critical_field);

        OnlyNumbers(critical_field);

        add_btn.setOnAction(e -> {
            error_text.setText("");
            Category category = new Category(name_field.getText(), 0, ToNumber(critical_field.getText()));

            if (category.getName().length() < 3) {
                name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.NAME_SHORT.getMsg());
                return;
            }

            if (category.getQuantity() == 0) {
                critical_field.getStyleClass().add(errorClassName);
                error_text.setText("The Critical Quantity Can t be zero");
                return;
            }

            if (PopUpWindows.ShowResult(CategoryDatabase.addCategory(category))) {
                Stage stage = (Stage) name_field.getScene().getWindow();
                stage.close();
                stage = null;
            }
        });
    }

}
