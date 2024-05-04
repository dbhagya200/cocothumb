package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumb.model.Item;
import lk.ijse.cocothumb.model.Supplier;
import lk.ijse.cocothumb.model.tModel.CartTm;
import lk.ijse.cocothumb.repository.ItemRepo;
import lk.ijse.cocothumb.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SuppPlaceOrderFormController {


    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStockQty;

    @FXML
    private TextField txtSuppName;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane rootNode6;

    @FXML
    private TableView<?> tblOrderCart;



    private ObservableList<CartTm> CartList = FXCollections.observableArrayList();

    @FXML
    void btnAddNewSupplier(ActionEvent event) throws IOException {
        AnchorPane rootNode8 = FXMLLoader.load(getClass().getResource("/view/add_supp_form.fxml"));
        Stage stage = (Stage) rootNode6.getScene().getWindow();
        rootNode6.getChildren().clear();
        rootNode6.getChildren().add(rootNode8);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnAddToCart(ActionEvent event) {

    }

    private void calculateNetTotal() {

    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode6.getScene().getWindow();

        stage.setScene(new Scene(rootNode1));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnPlaceOrder(ActionEvent event) {

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws IOException {
        String code = (String) cmbItemCode.getValue();

        try {
            Item item = ItemRepo.searchByCode(code);
            txtItemType.setText(item.getItem_type());
            txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
            txtStockQty.setText(item.getStock_qty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtQty.requestFocus();
    }

    @FXML
    void cmbSupplier(ActionEvent event) {
        String supp_id = (String) cmbSupplierId.getValue();

        try {
            Supplier supplier = SupplierRepo.searchById(supp_id);

            txtSuppName.setText(supplier.getSupp_name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddNewItem(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode7 = FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));
        Stage stage = (Stage) rootNode6.getScene().getWindow();
        rootNode6.getChildren().clear();
        rootNode6.getChildren().add(rootNode7);
        stage.setTitle("Add Item Form");
        stage.centerOnScreen();
    }

    public void btnShowTable(ActionEvent actionEvent) {

    }
}
