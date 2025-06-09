package com.project;

import java.io.IOException;

import com.project.Utils.FileHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    private Parent LoginView;
    private Parent UserView;
    private Parent AdminView;

    public static ViewFactory instance;

    public ViewFactory(Stage s) {
        stage = s;
        instance = this;

    }

    private void SetUpLoginPage() {
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setHeight(440);
        stage.setWidth(640);
    }

    private void SetUpNonLoginPage() {
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setMinHeight(640);
        stage.setMinWidth(1000);
    }

    public void showLoginView() {
        try {
            LoginView = FileHandler.loadFXML("Login").load();
            setScene(LoginView);
            SetUpLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserView() {
        try {
            UserView = FileHandler.loadFXML("User").load();
            setScene(UserView);
            SetUpNonLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdminView() {
        try {
            AdminView = FileHandler.loadFXML("Admin").load();
            setScene(AdminView);
            SetUpNonLoginPage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Invintory Management System");
        stage.show();
    }

    public static void toggleFullscreen() {
        stage.setFullScreen(!stage.isFullScreen());
    }

}
