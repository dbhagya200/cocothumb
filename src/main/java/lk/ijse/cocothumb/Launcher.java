package lk.ijse.cocothumb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage
                .setScene(new Scene(FXMLLoader
                        .load(this.getClass()
                                .getResource("/view/new_login.fxml"))));

        stage.setTitle("Login Form");
        stage.centerOnScreen();
        stage.show();
    }
}
