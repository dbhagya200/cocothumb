package lk.ijse.cocothumb.controller;
import javafx.scene.input.KeyEvent;
import lk.ijse.cocothumb.controller.Util.Regex;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.tModel.CustomerTm;

import lk.ijse.cocothumb.repository.CustomerRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;


    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtId1;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtName;
    private List<Customer> customerList = new ArrayList<>();


    @FXML
    void btnClear(ActionEvent event) {
        txtId1.setText("");
        txtNIC.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        loadNextCustomerId();
    }

    @FXML
    void btnSave(ActionEvent event) {
        String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = NewLoginController.getUserId();


        if (cust_name.equals("")){
            txtName.setStyle("-fx-border-color: red");
        }

        if (isValid()){

            Customer customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);


            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                }
                initialize();
                btnClear(event);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

         }
        initialize();
        btnClear(event);
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = NewLoginController.getUserId();

        if (isValid()){
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

        initialize();
        btnClear(event);

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
        initialize();
        btnClear(event);
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
            String[] split = currentId.split("c");
            int id = Integer.parseInt(split[1],10);
            return "c" + String.format("%04d", ++id);
        }
        return "c0001";
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

    public void txtCustomerNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.NIC.NIC,txtNIC);
    }
    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.contact.contact,txtContact);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.NIC,txtNIC)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.contact,txtContact)) return false;
        return true;
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.name.name,txtName);
    }
    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.address.address,txtAddress);
    }
    public void actionsearch(ActionEvent actionEvent) {
        String cust_id = txtId1.getText();

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

    public void idSearch(ActionEvent actionEvent) {
    }
}



