package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewLoginController {

    @FXML
    private AnchorPane rootNodeLogin;

    @FXML
    private ImageView rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public TextField txtUserId;
    private static String userId;

    @FXML
    void btnlogin(ActionEvent event) throws IOException {

        userId = txtUserId.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }

    }
    @FXML
    void actionFpassword(ActionEvent event) {

    }



    public static String getUserId() {
        return userId;
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT user_id, u_password FROM user WHERE user_id = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user id not found!").show();
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(this.getClass().getResource("/view/new_dashboard.fxml"));

        Scene scene = new Scene(rootNode1);

        Stage stage = (Stage) this.rootNodeLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }



}


