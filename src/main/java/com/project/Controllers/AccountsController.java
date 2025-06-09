package com.project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.ViewFactory;
import com.project.Database.ProductsDatabase;
import com.project.Database.UsersDatabse;
import com.project.Enums.AccountsOrederType;
import com.project.Enums.CategoryOrederType;
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Model.Product;
import com.project.Model.User;
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

public class AccountsController implements Initializable {
    @FXML
    private Button add_btn;
    @FXML
    private Button pdf_btn;
    @FXML
    private Button excel_btn;
    @FXML
    private ListView<User> accounts_list;
    /*
     * @FXML
     * private Text error_text;
     */
    @FXML
    private ComboBox<AccountsOrederType> oreder_combo;

    private ObservableList<User> list = FXCollections.observableArrayList();

    private static AccountsController instance;

    public static void Fetch() {
        UsersDatabse.getAll(instance.list, instance.oreder_combo.getValue());
        for (User u : instance.list) {
            System.out.println("********************************");
            System.out.println(u.getUserName());
            System.out.println("********************************");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        oreder_combo.getItems().addAll(AccountsOrederType.values());
        oreder_combo.setValue(AccountsOrederType.CreationASC);

        Fetch();
        accounts_list.setPlaceholder(new Label("No Category found"));
        accounts_list.setCellFactory(listview -> new AccountCellController());
        accounts_list.setItems(list);
        // accounts_list.setMouseTransparent(true);
        accounts_list.setFocusTraversable(false);
        add_btn.setOnAction(e -> PopUpWindows.ShowCustomDialog("CreateAccountDialog"));

        excel_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Accounts", true);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (UsersDatabse.getAll(products, AccountsOrederType.CreationASC) == Errors.SUCCESS) {
                        Category.CreateExcelSheet(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });
        pdf_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "Accounts", false);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (UsersDatabse.getAll(products, AccountsOrederType.CreationASC) == Errors.SUCCESS) {
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
