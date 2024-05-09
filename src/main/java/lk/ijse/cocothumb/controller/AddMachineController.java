package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Machine;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.MachineRepo;

import java.sql.SQLException;

public class AddMachineController {


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNodeMachine;

    @FXML
    private TableView<?> tblMachine;

    @FXML
    private JFXTextField txtBrandName;

    @FXML
    private JFXTextField  txtId;

    private static String machine_id;

    public static String getMachineId() {
        return machine_id;
    }

    @FXML
    void btnSave(ActionEvent event) {
     machine_id = txtId.getText();
    String brand = txtBrandName.getText();

        Machine machine = new Machine(machine_id,brand);
        try {
            boolean isSaved = MachineRepo.save(machine);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdate(ActionEvent event) {
    String machine_id = txtId.getText();
    String brand = txtBrandName.getText();

        Machine machine = new Machine(machine_id,brand);
        try {
            boolean isUpdated = MachineRepo.update(machine);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "machine updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void idSearch(ActionEvent event) {
        String machine_id = txtId.getText();

        try {
            Machine machine = MachineRepo.searchById(machine_id);

            if (machine != null) {
                txtId.setText(machine.getMachine_id());
                txtBrandName.setText(machine.getBrand());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() {
        loadNextMachineId();
    }
    private void loadNextMachineId() {
        try {
            String currentId = MachineRepo.currentId();
            String nextId = nextId(currentId);

            txtId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("m00");
            int id = Integer.parseInt(split[1]);
            return "m00" + ++id;

        }
        return "m001";
    }
}
