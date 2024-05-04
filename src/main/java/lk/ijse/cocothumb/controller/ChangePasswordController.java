package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.User;
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
    void btnChangePassword(ActionEvent event) {

        //String userId = LoginFormController.getUserId();


    }
    private void checkUser(String userId) throws SQLException, IOException {
        String sql = "SELECT user_id, u_password FROM user WHERE user_id = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        if (txtUserId.getText().equals(userId)) {
            idSearch();


        }
    }

    public void initialize() throws SQLException, IOException {
        checkUser(LoginFormController.getUserId());

    }
    public void idSearch() {

        String userId = txtUserId.getText();

        try {
            User user= UserRepo.searchById(userId);

            if (user != null) {
                txtUserId.setText(user.getUser_id());
                txtUserName.setText(user.getU_name());
                txtOldPassword.setText(user.getU_password());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



}
