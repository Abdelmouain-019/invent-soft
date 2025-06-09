package com.project.Controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.project.Database.UsersDatabse;
import com.project.Enums.Errors;
import com.project.Events.NameChangedPublisher;
import com.project.Utils.PopUpWindows;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ProfileController implements Initializable {
    @FXML
    private Text username;
    @FXML
    private Text full_name;
    @FXML
    private Button changeName;
    @FXML
    private Button changePassword;
    @FXML
    private Button changeFirstname;
    @FXML
    private Button changeLastname;
    @FXML
    private Button logout;

    private void getUsername() {
        username.setText(UsersDatabse.currentUser.getUserName());
    }

    private void ChangeNameDialog(String t) {
        Optional<String> result = PopUpWindows.DialogTextField("Change " + t, "Enter your " + t, t + " ");
        result.ifPresent(name -> {
            name = name.trim();
            if (name.length() < 3) {
                PopUpWindows.ShowMsg(Errors.NAME_SHORT.getMsg(), AlertType.ERROR);
                return;
            }
            switch (t) {
                case "Username":
                    PopUpWindows.ShowResult(
                            UsersDatabse.changeUsername(name),
                            "Your Username has changed to " + name);
                    NameChangedPublisher.instance.fireEvent();
                    break;
                case "First Name":
                    PopUpWindows.ShowResult(
                            UsersDatabse.changeFirstname(name),
                            "Your First Name has changed to " + name);
                    full_name.setText(name + " " + full_name.getText().split(" ")[1]);
                    break;
                case "Last Name":
                    PopUpWindows.ShowResult(
                            UsersDatabse.changeLastname(name),
                            "Your Last Name has changed to " + name);
                    full_name.setText(full_name.getText().split(" ")[0] + " " + name);
                    break;
                default:
                    System.err.println("Not Implemented");
                    break;
            }
        });
    }

    private void ChangePasswordDialog() {
        Optional<String> passOp = PopUpWindows.DialogTextField("Change Password", "Enter your password", "Password ");
        if (!passOp.isPresent()) {
            PopUpWindows.ShowMsg(Errors.PASSWORD_EMPTY.getMsg(), AlertType.ERROR);
            return;
        }
        String pass = passOp.get();
        if (pass.length() < 8) {
            PopUpWindows.ShowMsg(Errors.PASSWORD_SHORT.getMsg(), AlertType.ERROR);
            return;
        }
        passOp = PopUpWindows.DialogTextField("Confirm Password", "Renter your password", "Password ");
        if (!passOp.isPresent()) {
            PopUpWindows.ShowMsg(Errors.PASSWORD_EMPTY.getMsg(), AlertType.ERROR);
            return;
        }
        String pass2 = passOp.get();
        if (pass.equals(pass2)) {
            PopUpWindows.ShowResult(
                    UsersDatabse.changePassword(pass),
                    "Password Changed Succsefully");
        } else {
            PopUpWindows.ShowMsg(Errors.PASSWORD_DIFFRENCE.getMsg(), AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        getUsername();
        full_name.setText(UsersDatabse.currentUser.getFirstName() + " " + UsersDatabse.currentUser.getLastName());
        NameChangedPublisher.instance.addListener(() -> getUsername());
        logout.setOnAction(e -> UsersDatabse.logout());
        changeName.setOnAction(e -> ChangeNameDialog("Username"));
        changeFirstname.setOnAction(e -> ChangeNameDialog("First Name"));
        changeLastname.setOnAction(e -> ChangeNameDialog("Last Name"));
        changePassword.setOnAction(e -> ChangePasswordDialog());
    }

}
