package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import lk.ijse.cocothumb.controller.Util.Regex;
import lk.ijse.cocothumb.model.User;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.UserRepo;

import java.sql.SQLException;

public class UserFormController {

    @FXML
    private  JFXComboBox<JobRole> cmbJobrole;

    @FXML
    private AnchorPane rootNodeUserForm;

    @FXML
    private TextField txtConfirm;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;



    @FXML
    void btnConfirm(ActionEvent event) {
        String user_id = txtUserId.getText();
        String u_name = txtUserName.getText();
        String u_password=  txtPassword.getText();
        String confirm = txtConfirm.getText();
        String u_email = txtEmail.getText();
        String u_role = String.valueOf(cmbJobrole.getValue());
        String e_id = txtEmpId.getText();

        if (isValid()) {
            if (u_password.equals(confirm)) {
                User user = new User();

                user.setU_password(u_password);
                System.out.println("user = " + user);


            }else {
                new Alert(Alert.AlertType.ERROR, "Password not matched!").show();
            }

            User user = new User(user_id, u_name, u_password, u_email, u_role, e_id);

            System.out.println(user);

            try {
                boolean isSaved = UserRepo.save(user);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

            }
        }


    }


    @FXML
    void cmbJobAction(ActionEvent event) {
       /* ComboBox<JobRole> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(JobRole.values());

        comboBox.setOnAction(Event -> {
            JobRole selected = comboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
    });*/

    }
    public void comboJob(){
        cmbJobrole.getItems().addAll(JobRole.values());

        cmbJobrole.setOnAction(event -> {
            JobRole selected = cmbJobrole.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });

    }




    public void initialize() {
       //loadNextEmployeeId();
       loadNextUserId();
       comboJob();

    }

    private void loadNextUserId() {
        try {
            String currentId = UserRepo.currentUserId();
            String nextUId = nextUId(currentId);

            txtUserId.setText(nextUId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextUId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("u");
            int id = Integer.parseInt(split[1],10);
            return "u" + String.format("%04d", ++id);
        }
        return "u0001";
    }


    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.email.email, (JFXTextField) txtEmail);
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name.name, (JFXTextField) txtUserName);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name, (JFXTextField) txtUserName)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.email, (JFXTextField) txtEmail)) return false;
        return true;
    }
}
