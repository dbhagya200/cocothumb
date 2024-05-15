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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumb.model.*;
import lk.ijse.cocothumb.model.tModel.CartTms;
import lk.ijse.cocothumb.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuppPlaceOrderFormController {



    @FXML
    private JFXButton AddToCart;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private ComboBox<String> cmbSupplierId;

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
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNode6;

    @FXML
    private TableView<CartTms> tblSuppOrderCart;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtNetTotal;

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

    private ObservableList<CartTms> cartList = FXCollections.observableArrayList();

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
        String item_code = (String) cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String description = txtItemType.getText();
        double unitPriceForCompany = Double.parseDouble(txtUnitPrice.getText());
        double amount = qty * unitPriceForCompany;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblSuppOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblSuppOrderCart.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblSuppOrderCart.getItems().size(); i++) {
            if (item_code.equals(colItemCode.getCellData(i))) {
                qty += cartList.get(i).getQty();
                amount = unitPriceForCompany * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(amount);

                tblSuppOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        CartTms cartTms = new CartTms(item_code,  qty,description, unitPriceForCompany, amount, btnRemove);

        cartList.add(cartTms);

        tblSuppOrderCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double netTotal = 0;
        for (int i = 0; i < tblSuppOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        txtNetTotal.setText(String.valueOf(netTotal));
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
        String order_id = txtOrderId.getText();
        String supp_id = cmbSupplierId.getValue();
        String user_id = LoginFormController.getUserId();
        Date date = Date.valueOf(LocalDate.now());

        var suppOrder = new SuppOrder(order_id, supp_id, user_id, date);

        List<SuppDetails> sodList = new ArrayList<>();
        for (int i=0;i<tblSuppOrderCart.getItems().size();i++) {
            CartTms tms = cartList.get(i);

            SuppDetails SD = new SuppDetails(
                    tms.getItem_code(),
                    supp_id,
                    order_id,
                    tms.getQty(),
                    tms.getDescription(),
                    tms.getUnit_price_forCompany(),
                    tms.getAmount()
            );
            sodList.add(SD);
        }
        SPlaceOrder SPO = new SPlaceOrder(suppOrder, sodList);

        try {
            boolean isPlaced = SPlaceOrderRepo.splaceOrder(SPO);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier order placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "supplier order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

    public void initialize() {
        setCellValueFactory();
        loadNextOrderId();
        getSupplierId();
        getItemCodes();
    }

    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = ItemRepo.getCodes();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbItemCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSupplierId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getIds();

            obList.addAll(idList);
            cmbSupplierId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextOrderId() {
        try {
            String currentId = SuppOrderRepo.currentId();
            String nextId = nextId(currentId);

            txtOrderId.setText(nextId);
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

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price_forCompany"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }


    public void btnPrintBill(ActionEvent actionEvent) {
    }
}
