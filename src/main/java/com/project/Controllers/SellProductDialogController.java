package com.project.Controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.project.Database.HistoryDatabase;
import com.project.Database.ProductsDatabase;
import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Model.History;
import com.project.Model.Product;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * CreateAccountDialogController
 */
public class SellProductDialogController implements Initializable {
    @FXML
    private TextField quantity_field;
    @FXML
    private Button add_btn;
    @FXML
    private Label product_name;
    @FXML
    private ImageView image;
    @FXML
    private Label error_text;

    private final String errorClassName = "error_textfield";

    private static Product Selected = null;

    public static void setSelected(Product selected) {
        Selected = selected;
    }

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
        if (Selected == null) {
            Stage stage = (Stage) quantity_field.getScene().getWindow();
            stage.close();
            PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
        }

        error_text.setText("");
        product_name.setText(Selected.getName());
        if (Selected.getImage_data() != null) {
            image.setImage(new Image(new ByteArrayInputStream(Selected.getImage_data())));
        } else
            image.setImage(new Image("file:images/placeholder.png"));

        FoucsEvent(quantity_field);

        OnlyNumbers(quantity_field);

        add_btn.setOnAction(e -> {
            error_text.setText("");
            int q = ToNumber(quantity_field.getText());

            if (Selected.getQuantity() < q) {
                PopUpWindows.ShowResult(Errors.NOT_ENOUGH);
                return;
            }

            Selected.setQuantity(Selected.getQuantity() - q);

            if (PopUpWindows.ShowResult(ProductsDatabase.setProduct(Selected))) {
                HistoryDatabase.addHistory(new History(
                        1,
                        q,
                        Selected.getSelling_price(),
                        UsersDatabse.currentUser.getId(),
                        Selected.getId()));
                ProductsController.Fetch();
                Stage stage = (Stage) quantity_field.getScene().getWindow();
                stage.close();
            }
        });
    }

}
