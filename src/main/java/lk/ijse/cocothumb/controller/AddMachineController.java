package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.controller.Util.Regex;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Machine;
import lk.ijse.cocothumb.model.tModel.CustomerTm;
import lk.ijse.cocothumb.model.tModel.MachineTm;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.MachineRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.jfoenix.svg.SVGGlyphLoader.clear;

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
    private TableView<MachineTm> tblMachine;

    @FXML
    private JFXTextField txtBrandName;

    @FXML
    private JFXTextField  txtId;
    @FXML
    private JFXTextField  txtId1;

    private static String machine_id;

    public static String getMachineId() {
        return machine_id;
    }
    private List<Machine> machineList = new ArrayList<>();

    public void clearData(){
        txtId.setText("");
        txtBrandName.setText("");
        loadNextMachineId();
    }


    @FXML
    void btnSave(ActionEvent event) {
     machine_id = txtId.getText();
    String brand = txtBrandName.getText();

    if (isValid()) {
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

        initialize();
        clearData();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
    String machine_id = txtId.getText();
    String brand = txtBrandName.getText();

    if (isValid()) {
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

        initialize();
        clearData();
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
        this.machineList = getAllMachines();
        setMachineValue();
        loadMachineTable();
        loadNextMachineId();
    }

    private List<Machine> getAllMachines() {
        List<Machine> machineList = null;
        try {
            machineList = MachineRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return machineList;
    }

    private void setMachineValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("machine_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("brand"));
    }

    private void loadMachineTable() {
        ObservableList<MachineTm> tmList = FXCollections.observableArrayList();

        for (Machine machine : machineList) {
            MachineTm machineTm = new MachineTm(
                    machine.getMachine_id(),
                    machine.getBrand()

            );

            tmList.add(machineTm);
        }
        tblMachine.setItems(tmList);
        MachineTm selectedItem = tblMachine.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
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
            String[] split = currentId.split("#");
            int id = Integer.parseInt(split[1],10);
            return "#" + String.format("%04d", ++id);
        }
        return "#0001";
    }

    public void actionsearch(ActionEvent actionEvent) {
        String machine_id = txtId1.getText();

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

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name.name,txtBrandName);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name,txtBrandName)) return false;
        return true;
    }
}
