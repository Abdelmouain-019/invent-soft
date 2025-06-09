package com.project.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.project.ViewFactory;
import com.project.Enums.Pages;
import com.project.Utils.FileHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class UserController implements Initializable {
    @FXML
    private BorderPane mainPane;

    private static Pages page;

    private Parent PageViews[];

    private static UserController instance;

    public static void setPage(Pages p) {
        page = p;
        instance.refresh();
    }

    private void refresh() {
        System.out.println("Changed To : " + page.getFileName());
        try {

            if (PageViews[page.getIndex()] == null) {
                PageViews[page.getIndex()] = FileHandler.loadFXML(page.getFileName()).load();
            }
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(PageViews[page.getIndex()]);
            mainPane.setCenter(
                    stackPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instance = this;
        PageViews = new Parent[Pages.values().length];
        page = Pages.DASHBOARD;
        mainPane.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.F11) {
                ViewFactory.toggleFullscreen();
            }
        });
        refresh();
    }

}
