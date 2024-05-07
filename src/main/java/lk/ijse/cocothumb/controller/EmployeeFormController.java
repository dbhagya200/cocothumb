package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Employee;
import lk.ijse.cocothumb.model.Job;
import lk.ijse.cocothumb.model.tModel.CartTm;
import lk.ijse.cocothumb.model.tModel.CustomerTm;
import lk.ijse.cocothumb.model.tModel.EmployeeTm;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.JobRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeFormController {



    @FXML
    private TableColumn<?, ?> colSalary;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;
    @FXML
    private AnchorPane rootNode2;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private JFXComboBox<JobRole> cmbjobrole;
    private List<Employee> employeeList = new ArrayList<>();
    private static String e_id;

    public static String getE_id() {
        return e_id;
    }

    @FXML
    void cmbjobroleOnAction(ActionEvent event) throws SQLException {


    }

    @FXML
    void btnClear(ActionEvent event) {
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtSalary.setText("");

    }

    @FXML
    void btnAddNewMachine(ActionEvent event) throws IOException {
        AnchorPane rootNodeMachine = FXMLLoader.load(getClass().getResource("/view/add_machine.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");
        popupStage.setScene(new Scene(rootNodeMachine));
        popupStage.showAndWait();
    }

    @FXML
    void btnSave(ActionEvent event) throws IOException {
         e_id = txtId.getText();
        String e_name = txtName.getText();
        String e_jobrole= cmbjobrole.getValue().toString();
        String e_address = txtAddress.getText();
        String e_contact = txtContact.getText();
        double e_salary = Double.parseDouble(txtSalary.getText());
        String machine_id = AddMachineController.getMachineId();

        Employee employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact,e_salary,machine_id);
        System.out.println(employee);
        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "machine saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
        btnClear(event);
    }

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException {
        String e_id = txtId.getText();
        String e_name = txtName.getText();
        String e_jobrole= cmbjobrole.getValue().toString();
        String e_address = txtAddress.getText();
        String e_contact = txtContact.getText();
        double e_salary = Double.parseDouble(txtSalary.getText());
        String machine_id = AddMachineController.getMachineId();

        Employee employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact,e_salary,machine_id);

        boolean isUpdated = EmployeeRepo.update(employee);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
        }
    }

    @FXML
    void idSearch(ActionEvent event) throws SQLException {
        String id = txtId.getText();

        Employee employee = EmployeeRepo.searchById(id);

        if (employee != null) {
            txtId.setText(employee.getE_Id());
            txtName.setText(employee.getE_Name());
            txtAddress.setText(employee.getE_Address());
            txtContact.setText(employee.getE_Contact());
            txtSalary.setText(String.valueOf(employee.getE_Salary()));
        }
    }

    public void initialize() {
        this.employeeList = getAllEmployees();
        setEmployeeValue();
        loadEmployeeTable();
        loadNextEmployeeId();
        comboJob();

    }



    private void setEmployeeValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("e_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("e_contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("e_salary"));

        System.out.println("value set");
    }

    private void loadNextEmployeeId() {
        try {
            String currentId = EmployeeRepo.currentId();
            String nextId = nextId(currentId);

            txtId.setText(nextId);
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

    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

       for(Employee employee : employeeList){
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getE_Id(),
                    employee.getE_Name(),
                    employee.getE_jobrole(),
                    employee.getE_Address(),
                    employee.getE_Contact(),
                    employee.getE_Salary()
            );
            System.out.println("employeeTm load");

            tmList.add(employeeTm);
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);

    }

    public void comboJob(){
        cmbjobrole.getItems().addAll(JobRole.values());

        cmbjobrole.setOnAction(event -> {
            JobRole selected = cmbjobrole.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });

    }


    private List<Employee> getAllEmployees() {

        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }



    public void btnDelete(ActionEvent actionEvent) {
        String e_id = txtId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(e_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
