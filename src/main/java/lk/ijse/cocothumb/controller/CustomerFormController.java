package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumb.controller.Util.Regex;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.tModel.CartTm;
import lk.ijse.cocothumb.model.tModel.CustomerTm;

import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.cocothumb.controller.Util.Regex.isValidTextField;

public class CustomerFormController {


    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNode3;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;
    private List<Customer> customerList = new ArrayList<>();


    @FXML
    void btnClear(ActionEvent event) {
        txtNIC.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");


    }

    @FXML
    void btnSave(ActionEvent event) {
        String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = LoginFormController.getUserId();



        Customer customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);




           /* try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                }
                initialize();
                btnClear(event);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }*/

        boolean isValidNIC = Regex.isValidTextField(txtNIC, cust_NIC);

        if (isValidNIC) {
            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error saving customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC format").show();
        }



    }

    @FXML
    void btnUpdate(ActionEvent event) {
        String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = LoginFormController.getUserId();

        Customer customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
            initialize();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void NICSearch(ActionEvent event) {
        String cust_NIC = txtNIC.getText();

        try {
            Customer customer = CustomerRepo.searchByNIC(cust_NIC);

            if (customer != null) {
                txtId.setText(customer.getCust_id());
                txtNIC.setText(customer.getCust_NIC());
                txtName.setText(customer.getCust_name());
                txtAddress.setText(customer.getCust_address());
                txtContact.setText(customer.getCust_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    public void idSearch(ActionEvent actionEvent) {

        String cust_id = txtId.getText();

        try {
            Customer customer = CustomerRepo.searchById(cust_id);

            if (customer != null) {
                txtId.setText(customer.getCust_id());
                txtNIC.setText(customer.getCust_NIC());
                txtName.setText(customer.getCust_name());
                txtAddress.setText(customer.getCust_address());
                txtContact.setText(customer.getCust_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDelete(ActionEvent event) {

        String cust_id = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(cust_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
            initialize();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void initialize() {
        this.customerList = getAllCustomers();
        setCustomerValue();
        loadCustomerTable();
        loadNextCustomerId();
    }

    private void loadNextCustomerId() {
        try {
            String currentId = CustomerRepo.currentId();
            String nextId = nextId(currentId);

            txtId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("c00");
            int id = Integer.parseInt(split[1]);
            return "c00" + ++id;

        }
        return "c001";
    }


    private void setCustomerValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cust_NIC"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cust_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cust_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("cust_contact"));
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            CustomerTm customerTm = new CustomerTm(
                    customer.getCust_id(),
                    customer.getCust_NIC(),
                    customer.getCust_name(),
                    customer.getCust_address(),
                    customer.getCust_contact()

            );

            tmList.add(customerTm);
        }
        tblCustomer.setItems(tmList);
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }



}



