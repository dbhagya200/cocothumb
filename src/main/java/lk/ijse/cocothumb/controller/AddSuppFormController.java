package lk.ijse.cocothumb.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Supplier;
import lk.ijse.cocothumb.model.tModel.CustomerTm;
import lk.ijse.cocothumb.model.tModel.SupplierTm;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddSuppFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNode8;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private List<Supplier> supplierList = new ArrayList<>();

    @FXML
    void btnClear(ActionEvent event) {
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");


    }

    @FXML
    void btnExit(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode8.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }

    @FXML
    void btnHome(ActionEvent event) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode8.getScene().getWindow();

        stage.setScene(new Scene(rootNode1));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSave(ActionEvent event) {
        String supp_id = txtId.getText();
        String supp_name = txtName.getText();
        String supp_address = txtAddress.getText();
        String supp_contact = txtContact.getText();


        Supplier supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);
        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                initialize();
                btnClear(event);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdate(ActionEvent event) {

        String supp_id = txtId.getText();
        String supp_name = txtName.getText();
        String supp_address = txtAddress.getText();
        String supp_contact = txtContact.getText();

        Supplier supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
            }
            initialize();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnDelete(ActionEvent event) {
        String supp_id = txtId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(supp_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
            }
            initialize();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void idSearch(ActionEvent event) {

        String supp_id = txtId.getText();

        try {
            Supplier supplier = SupplierRepo.searchById(supp_id);

            if (supplier != null) {
                txtId.setText(supplier.getSupp_id());
                txtName.setText(supplier.getSupp_name());
                txtAddress.setText(supplier.getSupp_address());
                txtContact.setText(supplier.getSupp_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() {
        this.supplierList = getAllSuppliers();
        loadNextSupplierId();
        setSupplierValue();
        loadSupplierTable();

    }



    private void loadNextSupplierId() {
        try {
            String currentId = SupplierRepo.currentId();
            String nextId = nextId(currentId);

            txtId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("s00");
            int id = Integer.parseInt(split[1]);
            return "s00" + ++id;

        }
        return "s001";
    }

    private void setSupplierValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("supp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supp_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supp_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supp_contact"));
    }
    private void loadSupplierTable() {
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        for (Supplier supplier : supplierList) {
            SupplierTm supplierTm = new SupplierTm(
                    supplier.getSupp_id(),
                    supplier.getSupp_name(),
                    supplier.getSupp_address(),
                    supplier.getSupp_contact()

            );

            tmList.add(supplierTm);
        }
        tblSupplier.setItems(tmList);
        SupplierTm selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Supplier> getAllSuppliers() {
        List<Supplier> supplierList = null;
        try {
            supplierList = SupplierRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return supplierList;
    }

}
