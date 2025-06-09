package com.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.Database.CategoryDatabase;
import com.project.Database.ProductsDatabase;
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Model.Product;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * CreateAccountDialogController
 */
public class ModifyProductDialogController implements Initializable {
    @FXML
    private TextField id_field;
    @FXML
    private TextField name_field;
    @FXML
    private TextField quantity_field;
    @FXML
    private TextField selling_price_field;
    @FXML
    private TextField buing_price_field;
    @FXML
    private ComboBox<Category> category_combobox;
    // @FXML
    // private Button image_selector;
    @FXML
    private Button add_btn;
    @FXML
    private Label error_text;
    @FXML
    private Label image_label;

    private static Product Selected;

    public static void setSelected(Product selected) {
        Selected = selected;
    }

    private final String errorClassName = "error_textfield";

    // private static byte[] image;

    private void OnlyFloats(TextField field) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) { // "\\d*" means only digits
                return change;
            }
            return null; // reject the change
        }));
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

    private float ToFloat(String str) {
        if (str.isBlank())
            return 0;
        else
            return Float.parseFloat(str);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        error_text.setText("");
        FoucsEvent(id_field);
        FoucsEvent(name_field);
        FoucsEvent(quantity_field);
        FoucsEvent(selling_price_field);
        FoucsEvent(buing_price_field);

        OnlyNumbers(id_field);
        OnlyNumbers(quantity_field);
        OnlyFloats(selling_price_field);
        OnlyFloats(buing_price_field);

        id_field.setText(Selected.getId());
        name_field.setText(Selected.getName());
        quantity_field.setText(String.valueOf(Selected.getQuantity()));
        selling_price_field.setText(String.valueOf(Selected.getSelling_price()));
        buing_price_field.setText(String.valueOf(Selected.getBuing_price()));

        List<Category> l = new ArrayList<>();

        if (CategoryDatabase.getAll(l) != Errors.SUCCESS) {
            Stage stage = (Stage) name_field.getScene().getWindow();
            stage.close();
            PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
        }

        Category c = new Category("None", 0, 0);
        l.add(c);

        category_combobox.getItems().addAll(l);
        for (Category cat : l) {
            if (cat.getId() == Selected.getCat_id()) {
                category_combobox.setValue(cat);
                break;
            }
        }

        add_btn.setOnAction(e -> {
            error_text.setText("");
            Product product = new Product();
            product.setId(id_field.getText().trim());
            if (product.getId().length() < 7) {
                name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.INVALID_PRODUCT_ID.getMsg());
                return;
            }

            product.setName(name_field.getText().trim());
            if (product.getName().length() < 3) {
                name_field.getStyleClass().add(errorClassName);
                error_text.setText(Errors.NAME_SHORT.getMsg());
                return;
            }

            product.setQuantity(ToNumber(quantity_field.getText()));
            product.setSelling_price(ToFloat(selling_price_field.getText()));
            product.setBuing_price(ToFloat(buing_price_field.getText()));
            if (product.getBuing_price() == 0) {
                buing_price_field.getStyleClass().add(errorClassName);
                error_text.setText("The Buing Price Can't be zero");
                return;
            }

            product.setCat_id(category_combobox.getValue().getId());
            System.out.println("Category : " + category_combobox.getValue().getName());
            System.out.println("Category ID : " + category_combobox.getValue().getId());

            // product.setImage_data(image);

            if (PopUpWindows.ShowResult(ProductsDatabase.setProduct(product))) {
                ProductsController.Fetch();
                Stage stage = (Stage) name_field.getScene().getWindow();
                stage.close();
                stage = null;
            }
        });
    }

}
