package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.controller.Util.EmailSender;
import lk.ijse.cocothumb.controller.Util.Mail;
import lk.ijse.cocothumb.model.User;
import lk.ijse.cocothumb.repository.UserRepo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordController {

    static String checkOtp;

    public AnchorPane rootNodeForgot;
    public JFXTextField txtEmail;
    public JFXTextField txtOTP;
    public JFXPasswordField txtNewPassword;
    public JFXPasswordField txtConfirm;
    public JFXTextField txtUserName;
    public JFXTextField txtUseId;
    public JFXButton btnconfirm;


    public void btnConfirm(ActionEvent actionEvent) throws SQLException {
        String newPassword = txtNewPassword.getText();
        String conformPassword = txtConfirm.getText();

        if (newPassword.equals(conformPassword)) {
            User user = new User();
            user.setU_password(newPassword);
            user.setUser_id(txtUseId.getText());
            UserRepo.update(user);
            System.out.println("user = " + user);
            new Alert(Alert.AlertType.CONFIRMATION, "Password changed!").show();

        }
        txtUseId.setText("");
        txtNewPassword.setText("");
        txtConfirm.setText("");
        txtEmail.setText("");
        txtUserName.setText("");
    }

    public void btnCheck(ActionEvent actionEvent) {
        if (checkOtp.equalsIgnoreCase(txtOTP.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "OTP Verified").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP").show();
        }
    }

    public void sendOtpOnAction(ActionEvent actionEvent) {
        try {
            User user = UserRepo.searchByName(txtUserName.getText());
            System.out.println(user);

            if (user != null) {

                String subject = "OTP for Account Verification";
                String otp = generateOTP();
                String body = "<html>"
                        + "<body>"
                        + "<p>Dear " + user.getU_name() + ",</p>"
                        + "<p>Your One-Time Password (OTP) for account verification is: <span style='color:blue; font-size:16px;'>" + otp + "</span></p>"
                        + "<p>Please use this OTP to verify your account.</p>"
                        + "<p>If you didn't request this OTP, please ignore this email.</p>"
                        + "<p>Best regards,<br>CocoThumb</p>"
                        + "</body>"
                        + "</html>";
                EmailSender.sendEmail(user.getU_email(), subject, body);
                txtOTP.setText("");
            }else {
                new Alert(Alert.AlertType.ERROR, "User Not Found").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateOTP() {
        int otpLength = 6;
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            int index = (int)(numbers.length() * Math.random());
            sb.append(numbers.charAt(index));
        }
        checkOtp = sb.toString();
        return sb.toString();
    }



    public void searchUName(KeyEvent keyEvent) {
        try {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                User user = UserRepo.searchByName(txtUserName.getText());
                if (user != null) {
                   txtUseId.setText(user.getUser_id());
                    txtEmail.setText(user.getU_email());
                }else {
                    new Alert(Alert.AlertType.ERROR, "User not found!").show();
                }
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtOtpKeyOnAction(KeyEvent keyEvent) {
        if (txtOTP.getText().length()>0 && txtOTP.getText().length()<=6){
            txtOTP.setStyle("-fx-border-color: green");
            btnconfirm.setDisable(false);
        }
        else {
            txtOTP.setStyle("-fx-border-color: red");
            btnconfirm.setDisable(true);
        }
    }

    public void txtConfirmKeyType(KeyEvent keyEvent) {
        if (txtConfirm.getText().equals(txtNewPassword.getText())){
            txtConfirm.setStyle("-fx-border-color: green");
            btnconfirm.setDisable(false);
        }
        else {
            btnconfirm.setDisable(true);
            txtConfirm.setStyle("-fx-border-color: red");
        }
    }
}