package com.project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.project.ViewFactory;
import com.project.Database.CategoryDatabase;
import com.project.Enums.CategoryOrederType;
import com.project.Enums.Errors;
import com.project.Model.Category;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class CategoryController implements Initializable {
    @FXML
    private Button add_btn;
    @FXML
    private Button pdf_btn;
    @FXML
    private Button excel_btn;
    @FXML
    private ListView<Category> category_list;
    @FXML
    private Text error_text;
    @FXML
    private ComboBox<CategoryOrederType> oreder_combo;

    private ObservableList<Category> list = FXCollections.observableArrayList();

    private static CategoryController instance;

    public static void Fetch() {
        instance.error_text.setText("");
        if (CategoryDatabase.getAll(instance.list, instance.oreder_combo.getValue()) != Errors.SUCCESS)
            instance.error_text.setText(Errors.FETCH_ERROR.getMsg());
    }

    // private void AddCategoryDialog() {
    // Optional<String> NameOp = PopUpWindows.DialogTextField("Adding new Category",
    // "Enter the name of this Category",
    // "Name ");
    // if (NameOp.isPresent()) {
    // String name = NameOp.get().trim();
    // if (name.length() < 3) {
    // PopUpWindows.ShowMsg(Errors.NAME_SHORT.getMsg(), AlertType.ERROR);
    // return;
    // }
    // if (CategoryDatabase.addCategory(name) == Errors.SUCCESS) {
    // Fetch();
    // PopUpWindows.ShowMsg("Category Added", AlertType.INFORMATION);
    // return;
    // }
    //
    // }
    // PopUpWindows.ShowMsg(Errors.GLOBAL_ERROR.getMsg(), AlertType.ERROR);
    // }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        oreder_combo.getItems().addAll(CategoryOrederType.values());
        oreder_combo.setValue(CategoryOrederType.CreationASC);

        Fetch();
        category_list.setPlaceholder(new Label("No Category found"));
        category_list.setCellFactory(listview -> new CategoryCellController());
        category_list.setItems(list);
        // category_list.setMouseTransparent(true);
        category_list.setFocusTraversable(false);
        add_btn.setOnAction(e -> {
            PopUpWindows.ShowCustomDialog("CreateCategoryDialog");
            Fetch();
        });

        excel_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Category", true);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (CategoryDatabase.getAllExport(products) == Errors.SUCCESS) {
                        Category.CreateExcelSheet(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });
        pdf_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Category", false);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (CategoryDatabase.getAllExport(products) == Errors.SUCCESS) {
                        Category.CreatePDFFile(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });

        oreder_combo.setOnAction(event -> {
            Fetch();
        });
    }

}
