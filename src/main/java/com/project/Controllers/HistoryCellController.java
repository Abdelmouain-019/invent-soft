package com.project.Controllers;

import java.io.IOException;

import com.project.Model.History;
import com.project.Utils.FileHandler;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HistoryCellController extends ListCell<History> {
    @FXML
    private Label date_label;
    @FXML
    private Label user_label;
    @FXML
    private Label product_label;
    @FXML
    private Label amount_label;
    @FXML
    private Label price_label;
    @FXML
    private VBox sell;
    @FXML
    private VBox buy;

    private HBox content;

    public HistoryCellController() {
        try {
            FXMLLoader fxml = FileHandler.loadFXML("HistoryCell");
            fxml.setController(this);
            content = fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Disable(VBox b) {
        b.setVisible(false);
        b.setMaxWidth(0);
        b.setMinWidth(0);
    }

    private void Enable(VBox b) {
        b.setVisible(true);
        b.setMaxWidth(40);
        b.setMinWidth(40);
    }

    @Override
    protected void updateItem(History history, boolean empty) {
        super.updateItem(history, empty);
        if (history == null || empty) {
            setGraphic(null);
        } else {
            date_label.setText(history.getOccur_date().toString());
            user_label.setText(history.getUser_name());
            product_label.setText(history.getProduct_name());
            amount_label.setText(String.valueOf(history.getQuantity()));
            price_label.setText(history.getPrice() + " Dz");

            Boolean is_buy = history.getAction() == 0;

            price_label.setTextFill(is_buy ? Color.RED : Color.GREEN);

            Disable(is_buy ? sell : buy);
            Enable(is_buy ? buy : sell);

            setGraphic(content);
        }
    }

}
