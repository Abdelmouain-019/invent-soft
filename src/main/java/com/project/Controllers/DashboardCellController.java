package com.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import com.project.Controllers.DashboardController.ProductInfo;
import com.project.Database.ProductsDatabase;
import com.project.Model.Product;
import com.project.Utils.FileHandler;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class DashboardCellController extends ListCell<Product>{
    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label quantity_label;

    @FXML
    private Circle red_dot;

    private HBox content;

    public DashboardCellController() {
        try {
            FXMLLoader fxml = FileHandler.loadFXML("DashboardCell");
            fxml.setController(this);
            content = fxml.load();
            String userDirectory = System.getProperty("user.dir") + "/images";
            File directory = new File(userDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (product == null || empty) {
            setGraphic(null);
        } else {
            red_dot.setVisible(product.getQuantity() < product.getCritical());
            if (product.getImage_data() != null) {
                try {
                    image.setImage(new Image(new ByteArrayInputStream(product.getImage_data())));
                } catch (Exception e) {
                    //System.err.println("**********Error************\n".repeat(5));
                    e.printStackTrace();
                    //System.err.println("**********Error************\n".repeat(5));
                }
            } else
                image.setImage(new Image("file:images/placeholder.png"));
            name.setText(product.getName());
            quantity_label.setText(product.getQuantity() + "");
            setGraphic(content);
        }
    }
}
