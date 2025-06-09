package com.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import com.project.Model.Product;
import com.project.Utils.FileHandler;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class ProductCellController extends ListCell<Product> {
    @FXML
    private Label category_label;
    @FXML
    private Label name;
    @FXML
    private Label buing_price;
    @FXML
    private Label selling_price;
    @FXML
    private Label quantity_label;
    // @FXML
    // private Button delete_btn;
    @FXML
    private Button sell_btn;
    @FXML
    private Button buy_btn;
    @FXML
    private ImageView image;
    @FXML
    private Circle red_dot;

    private HBox content;

    public ProductCellController() {
        try {
            FXMLLoader fxml = FileHandler.loadFXML("ProductCell");
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
                    System.err.println("**********Error************\n".repeat(5));
                    e.printStackTrace();
                    System.err.println("**********Error************\n".repeat(5));
                }
            } else
                image.setImage(new Image("file:images/placeholder.png"));
            name.setText(product.getName());
            category_label.setText(product.getCat_name());
            buing_price.setText(product.getBuing_price() + " Dz");
            selling_price.setText(product.getSelling_price() + " Dz");
            quantity_label.setText(product.getQuantity() + "");
            buy_btn.setOnAction(e -> {
                BuyProductDialogController.setSelected(product);
                PopUpWindows.ShowCustomDialog("BuyProduct");
                red_dot.setVisible(product.getQuantity() < product.getCritical());
            });
            sell_btn.setOnAction(e -> {
                SellProductDialogController.setSelected(product);
                PopUpWindows.ShowCustomDialog("SellProduct");
                red_dot.setVisible(product.getQuantity() < product.getCritical());
            });

            setGraphic(content);
        }
    }

}
