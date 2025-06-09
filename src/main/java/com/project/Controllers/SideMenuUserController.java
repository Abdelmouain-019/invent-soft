package com.project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.Database.UsersDatabse;
import com.project.Enums.Pages;
import com.project.Events.NameChangedPublisher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SideMenuUserController implements Initializable {
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button productsBtn;
    @FXML
    private Button historyBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Text username;

    private void getUsername() {

        username.setText(UsersDatabse.currentUser.getUserName());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        getUsername();
        NameChangedPublisher.instance.addListener(() -> getUsername());
        logoutBtn.setOnAction(e -> UsersDatabse.logout());
        dashboardBtn.setOnAction(e -> UserController.setPage(Pages.DASHBOARD));
        productsBtn.setOnAction(e -> UserController.setPage(Pages.PRODUCTS));
        // categoryBtn.setOnAction(e -> UserController.setPage(Pages.CATEGORY));
        historyBtn.setOnAction(e -> UserController.setPage(Pages.HISTORY));
        profileBtn.setOnAction(e -> {
            UserController.setPage(Pages.PROFILE);
        });
    }

}
