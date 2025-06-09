package com.project.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.ViewFactory;
import com.project.Database.HistoryDatabase;
import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Enums.HistoryOrederType;
import com.project.Model.History;
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

public class HistoryController implements Initializable {
    @FXML
    private ListView<History> history_listview;
    @FXML
    private ComboBox<HistoryOrederType> oreder_combo;
    @FXML
    private Button pdf_btn;
    @FXML
    private Button excel_btn;

    private ObservableList<History> list = FXCollections.observableArrayList();

    public static HistoryController instance;

    public static void Fetch() {
        if (UsersDatabse.currentUser.isAdmin()) {
            if (HistoryDatabase.getAll(instance.list, instance.oreder_combo.getValue()) != Errors.SUCCESS)
                System.err.println("ERROR");
        } else {
            if (HistoryDatabase.getAllUserHistory(instance.list, instance.oreder_combo.getValue()) != Errors.SUCCESS)
                System.err.println("ERROR");

        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        oreder_combo.getItems().addAll(HistoryOrederType.values());
        oreder_combo.setValue(HistoryOrederType.CreationASC);

        pdf_btn.setOnAction(e -> {
            Platform.runLater(() -> {
                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "History", false);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (UsersDatabse.currentUser.isAdmin() && HistoryDatabase.getAllExport(products,
                            HistoryOrederType.CreationASC) == Errors.SUCCESS) {
                        History.CreatePDFFile(products, file);
                    } else if (HistoryDatabase.getAllUserHistoryExport(products,
                            HistoryOrederType.CreationASC) == Errors.SUCCESS) {
                        History.CreatePDFFile(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });

        excel_btn.setOnAction(e -> {
            Platform.runLater(() -> {

                File file = PopUpWindows.ShowPathSelector(ViewFactory.getStage(), "History", true);
                if (file != null) {
                    List<ExportEntity> products = new ArrayList<>();
                    if (UsersDatabse.currentUser.isAdmin() && HistoryDatabase.getAllExport(products,
                            HistoryOrederType.CreationASC) == Errors.SUCCESS) {
                        History.CreateExcelSheet(products, file);
                    } else if (HistoryDatabase.getAllUserHistoryExport(products,
                            HistoryOrederType.CreationASC) == Errors.SUCCESS) {
                        History.CreateExcelSheet(products, file);
                    } else {
                        PopUpWindows.ShowResult(Errors.GLOBAL_ERROR);
                    }
                }
            });
        });

        Fetch();
        history_listview.setPlaceholder(new Label("No History found"));
        history_listview.setCellFactory(listview -> new HistoryCellController());
        history_listview.setItems(list);
        history_listview.setFocusTraversable(false);

        oreder_combo.setOnAction(event -> {
            Fetch();
        });

    }

}
