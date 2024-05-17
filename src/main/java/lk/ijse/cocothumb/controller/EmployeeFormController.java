package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumb.controller.Util.EmailSender;
import lk.ijse.cocothumb.controller.Util.Regex;
import lk.ijse.cocothumb.controller.Util.TextField;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Employee;
import lk.ijse.cocothumb.model.Job;
import lk.ijse.cocothumb.model.User;
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


    public JFXTextField txtEmail;
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
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtId1;


    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

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
        txtId1.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtSalary.setText("");
        txtEmail.setText("");
        loadNextEmployeeId();

    }

    @FXML
    void btnAddNewMachine(ActionEvent event) throws IOException {
        AnchorPane rootNodeMachine = FXMLLoader.load(getClass().getResource("/view/add_machine.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add Machine Form");
        popupStage.setScene(new Scene(rootNodeMachine));
        popupStage.showAndWait();
    }

    @FXML
    void btnSave(ActionEvent event) throws IOException {
        e_id = txtId.getText();
        String e_name = txtName.getText();
        String e_jobrole = cmbjobrole.getValue().toString();
        String e_address = txtAddress.getText();
        String e_contact = txtContact.getText();
        double e_salary = Double.parseDouble(txtSalary.getText());
        String e_email = txtEmail.getText();
        String machine_id = AddMachineController.getMachineId();

         if (isValid()) {


        Employee employee = new Employee(e_id, e_name, e_jobrole, e_address, e_contact, e_salary,e_email, machine_id);
       // User user = new User(e_id, e_email);
        System.out.println(employee);
        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                initialize();
                btnClear(event);

                String body = "<html>"
                        + "<body>"
                        + "<p>Dear " + e_name + ",</p>"
                        + "<p>We are pleased to inform you that your account for accessing our system has been successfully created. Below are your login credentials:</p>"
                        + "<p>Username: <span style='color:blue; font-size:14px;'>" + e_email + "</span></p>"
                        + "<p>Password: <span style='color:green; font-size:14px;'>" + e_contact + "</span></p>"
                        + "<p>For security reasons, we recommend that you change your password upon your first login.</p>"
                        + "<p>If you have any questions or need assistance, please don't hesitate to contact our support team at support@example.com.</p>"
                        + "<p>Best regards,<br>CocoThumb</p>"
                        + "</body>"
                        + "</html>";
                EmailSender.sendEmail(e_email, "CocoThumb System User Login Access", body);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
    }
            initialize();
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
        String e_email = txtEmail.getText();
        String machine_id = AddMachineController.getMachineId();
    if (isValid()) {
    Employee employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact,e_salary,e_email,machine_id);

    boolean isUpdated = EmployeeRepo.update(employee);
    if (isUpdated) {
        new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
         }
    }
        initialize();
        btnClear(event);
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
            String[] split = currentId.split("e");
            int id = Integer.parseInt(split[1],10);
            return "e" + String.format("%04d", ++id);
        }
        return "e0001";
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
                    employee.getE_Salary(),
                    employee.getE_email()
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
        initialize();
        btnClear(actionEvent);
    }


    public void actionsearch(ActionEvent actionEvent) throws SQLException {
        String id = txtId1.getText();

        Employee employee = EmployeeRepo.searchById(id);

        if (employee != null) {
            txtId.setText(employee.getE_Id());
            txtName.setText(employee.getE_Name());
            txtAddress.setText(employee.getE_Address());
            txtContact.setText(employee.getE_Contact());
            txtSalary.setText(String.valueOf(employee.getE_Salary()));
            txtEmail.setText(employee.getE_email());
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name.name,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.address.address,txtAddress);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.contact.contact,txtContact);
    }
    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.Double.Double,txtSalary);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name,txtName)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.contact,txtContact)) return false;
        else if (!Regex.setTextColor(TextField.address,txtAddress)) return false;
        else if (!Regex.setTextColor(TextField.Double,txtSalary)) return false;
        else if (!Regex.setTextColor(TextField.email,txtEmail)) return false;
        return true;
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.email.email,txtEmail);

    }
}
