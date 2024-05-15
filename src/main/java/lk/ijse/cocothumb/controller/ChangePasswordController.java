package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.User;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.UserRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordController {

    @FXML
    private TextField txtUserName;
    @FXML
    private AnchorPane rootNodePassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtOldPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    void btnChangePassword(ActionEvent event) throws SQLException {

        String newPassword = txtNewPassword.getText();
        String conformPassword = txtConfirmPassword.getText();

        if (newPassword.equals(conformPassword)) {
            User user = new User();
            user.setU_password(newPassword);
            user.setUser_id(txtUserId.getText());
            UserRepo.update(user);
            System.out.println("user = " + user);
            new Alert(Alert.AlertType.CONFIRMATION, "Password changed!").show();

      }


    }

    public void initialize() throws SQLException, IOException {
        idSearch();
    }
    public void idSearch() {

       String userId = NewLoginController.getUserId();
        System.out.println("userId = " + userId);
       // txtUserId.setText(userId);


        try {
            User user= UserRepo.searchById(userId);
            System.out.println("userId = " + userId);
            if (user != null) {
                txtUserId.setText(NewLoginController.getUserId());
                txtUserName.setText(user.getU_name());
                txtOldPassword.setText(user.getU_password());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
