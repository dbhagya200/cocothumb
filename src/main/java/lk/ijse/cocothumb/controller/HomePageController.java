package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private AnchorPane rootNodeHome;

    public void btnSignup(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNodeUserForm = FXMLLoader.load(getClass().getResource("/view/user_form.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");
        popupStage.setScene(new Scene(rootNodeUserForm));
        popupStage.showAndWait();
    }

    public void btnLogin(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNodeLogin = FXMLLoader.load(this.getClass().getResource("/view/new_login.fxml"));

        Scene scene = new Scene(rootNodeLogin);

        Stage stage = (Stage) this.rootNodeHome.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }
}
