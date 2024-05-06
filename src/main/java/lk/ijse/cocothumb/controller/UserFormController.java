package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.UserRepo;

import java.sql.SQLException;

public class UserFormController {

    @FXML
    private JFXComboBox<?> cmbJobrole;

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

    }

    @FXML
    void cmbJobAction(ActionEvent event) {

    }
    public void initialize() {
       loadNextEmployeeId();
       loadNextUserId();
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

    private String nextUId(String currentUserId) {
        if (currentUserId != null) {
            String[] split = currentUserId.split("u00");
            int id = Integer.parseInt(split[1]);
            return "u00" + ++id;

        }
        return "u001";
    }


    private void loadNextEmployeeId() {
        try {
            String currentId = EmployeeRepo.currentId();
            String nextId = nextId(currentId);

            txtEmpId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("e00");
            int id = Integer.parseInt(split[1]);
            return "e00" + ++id;

        }
        return "e001";
    }

}
