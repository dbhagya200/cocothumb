package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.controller.Util.Mail;
import lk.ijse.cocothumb.model.User;
import lk.ijse.cocothumb.repository.UserRepo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordController {


    public AnchorPane rootNodeForgot;
    public JFXTextField txtEmail;
    public JFXTextField txtOTP;
    public JFXPasswordField txtNewPassword;
    public JFXPasswordField txtConfirm;
    public JFXTextField txtUserName;

    public void searchUName() {
        try {
            User user = UserRepo.searchByName(txtUserName.getText());
            if (user != null) {
                txtUserName.setText(user.getU_name());
                txtEmail.setText(user.getU_email());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnConfirm(ActionEvent actionEvent) {

    }

    public void btnCheck(ActionEvent actionEvent) {
    }

    public void sendOtpOnAction(ActionEvent actionEvent) {
        int otp = new Random().nextInt(9999);
        User user = new User();
        try {
            Mail.setOtpMail(user.getU_email(), String.valueOf(otp));
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() throws SQLException, IOException {
        searchUName();
    }
}