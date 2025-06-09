package com.project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.Database.UsersDatabse;
import com.project.Enums.Pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SideMenuAdminController implements Initializable {
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button productsBtn;
    @FXML
    private Button categoryBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button accountBtn;
    @FXML
    private Button historyBtn;
    @FXML
    private Button logoutBtn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        logoutBtn.setOnAction(e -> UsersDatabse.logout());
        dashboardBtn.setOnAction(e -> UserController.setPage(Pages.DASHBOARD));
        productsBtn.setOnAction(e -> UserController.setPage(Pages.PRODUCTS));
        categoryBtn.setOnAction(e -> UserController.setPage(Pages.CATEGORY));
        profileBtn.setOnAction(e -> UserController.setPage(Pages.PROFILE));
        accountBtn.setOnAction(e -> UserController.setPage(Pages.ACCOUNTS));
        historyBtn.setOnAction(e -> UserController.setPage(Pages.HISTORY));
    }

}
