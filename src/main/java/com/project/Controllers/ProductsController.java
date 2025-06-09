package com.project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.ViewFactory;
import com.project.Database.CategoryDatabase;
import com.project.Database.ProductsDatabase;
import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Enums.ProductsOrederType;
import com.project.Model.Category;
import com.project.Model.Product;
import com.project.Utils.ExportEntity;
import com.project.Utils.PopUpWindows;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProductsController implements Initializable {
    @FXML
    private ListView<Product> products_listview;
    @FXML
    private ComboBox<ProductsOrederType> oreder_combo;
    @FXML
    private ComboBox<Category> category_combobox;
    @FXML
    private Button add_btn;
    @FXML
    private Button excel_btn;
    @FXML
    private Button pdf_btn;
    @FXML
    private TextField serach_field;

    private ObservableList<Product> list = FXCollections.observableArrayList();

    public static ProductsController instance;

    public static void Fetch() {
        Errors result;
        if (instance.serach_field.getText().isEmpty() && instance.category_combobox.getValue().getId() == 0) {
            result = ProductsDatabase.getAll(
                    instance.list,
                    instance.oreder_combo.getValue());
        } else if (!instance.serach_field.getText().isEmpty() && instance.category_combobox.getValue().getId() != 0) {
            result = ProductsDatabase.getAll(
                    instance.list,
                    instance.oreder_combo.getValue(),
                    instance.serach_field.getText(),
                    instance.category_combobox.getValue().getName());
        } else if (!instance.serach_field.getText().isEmpty()) {
            result = ProductsDatabase.getAll(
                    instance.list,
                    instance.oreder_combo.getValue(),
                    instance.serach_field.getText());
        } else {
            result = ProductsDatabase.getAllCategory(
                    instance.list,
                    instance.oreder_combo.getValue(),
                    instance.category_combobox.getValue().getName());
        }
        if (result != Errors.SUCCESS)
            System.err.println("ERROR");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        oreder_combo.getItems().addAll(ProductsOrederType.values());
        oreder_combo.setValue(ProductsOrederType.CreationASC);

        List<Category> l = new ArrayList<>();

        if (CategoryDatabase.getAll(l) != Errors.SUCCESS) {
            PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
        }

        Category c = new Category("None", 0, 0);
        l.add(c);

        category_combobox.getItems().addAll(l);
        category_combobox.setValue(c);
        Fetch();
        products_listview.setPlaceholder(new Label("No Product found"));
        products_listview
                .setCellFactory(listview -> {
                    if (UsersDatabse.currentUser.isAdmin()) {
                        return new ProductCellAdminController();
                    } else {
                        return new ProductCellController();
                    }
                });
        products_listview.setItems(list);
        products_listview.setFocusTraversable(false);
        // add_btn.setOnAction(e -> AddCategoryDialog());
        add_btn.setOnAction(e -> {
            if (!UsersDatabse.currentUser.isAdmin()) {
                PopUpWindows.ShowResult(Errors.PERMISSION_DENIED);
                return;
            }
            PopUpWindows.ShowCustomDialog("CreateProductDialog");
        });

        pdf_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Products", false);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (ProductsDatabase.getAll(products, ProductsOrederType.CreationASC) == Errors.SUCCESS) {
                        Product.CreatePDFFile(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });

        excel_btn.setOnAction(e -> {
            Platform.runLater(() -> {

                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Products", true);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (ProductsDatabase.getAll(products, ProductsOrederType.CreationASC) == Errors.SUCCESS) {
                        Product.CreateExcelSheet(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });

        serach_field.setOnAction(e -> {
            Fetch();
        });

        oreder_combo.setOnAction(event -> {
            Fetch();
        });
        category_combobox.setOnAction(event -> {
            Fetch();
        });

        add_btn.setVisible(UsersDatabse.currentUser.isAdmin());
    }

}
