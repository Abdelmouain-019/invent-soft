package com.project.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import com.project.Enums.Errors;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindows {
    private static int width = 400;
    private static int height = 250;

    public static Optional<String> DialogTextField(String title, String HeaderText, String ContentText) {
        TextInputDialog tiDialog = new TextInputDialog();
        tiDialog.setTitle(title);
        tiDialog.setHeaderText(HeaderText);
        tiDialog.setContentText(ContentText);
        tiDialog.setWidth(width);
        tiDialog.setHeight(height);
        return tiDialog.showAndWait();
    }

    public static boolean DialogConfirm(String title, String HeaderText, String ContentText) {
        Alert tiDialog = new Alert(AlertType.CONFIRMATION);
        tiDialog.setTitle(title);
        tiDialog.setHeaderText(HeaderText);
        tiDialog.setContentText(ContentText);
        ButtonType result = tiDialog.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    public static void ShowMsg(String msg, AlertType a) {
        Alert dialog = new Alert(a);
        dialog.setTitle("Alert");
        dialog.setContentText(msg);
        dialog.show();
    }

    public static boolean ShowResult(Errors err) {
        if (err == Errors.SUCCESS) {
            ShowMsg(err.getMsg(), AlertType.INFORMATION);
            return true;
        } else {
            ShowMsg(err.getMsg(), AlertType.ERROR);
            return false;
        }
    }

    public static boolean ShowResult(Errors err, String Succses) {
        if (err == Errors.SUCCESS) {
            ShowMsg(Succses, AlertType.INFORMATION);
            return true;
        } else {
            ShowMsg(err.getMsg(), AlertType.ERROR);
            return false;
        }
    }

    public static boolean ShowResult(Errors err, String Succses, String Error) {
        if (err == Errors.SUCCESS) {
            ShowMsg(Succses, AlertType.INFORMATION);
            return true;
        } else {
            ShowMsg(Error, AlertType.ERROR);
            return false;
        }
    }

    public static void ShowCustomDialog(String name) {
        Parent root;
        try {
            root = FileHandler.loadFXML("PopUpWindow/" + name).load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            ShowResult(Errors.GLOBAL_ERROR);
            e.printStackTrace();
        }
    }

    public static File ShowPathSelector(Stage s, String title, boolean isExcel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Path to save");
        if (isExcel) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            fileChooser.setInitialFileName(title + ".xlsx");
        } else {

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName(title + ".pdf");
        }

        return fileChooser.showSaveDialog(s);
    }

    public static File ShowImageSelector(Stage s, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        return fileChooser.showOpenDialog(s);

    }
}
