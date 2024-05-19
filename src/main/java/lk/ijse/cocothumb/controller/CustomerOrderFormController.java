package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumb.controller.Util.Regex;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.*;
import lk.ijse.cocothumb.model.tModel.CartTm;
import lk.ijse.cocothumb.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerOrderFormController {


    public AnchorPane rootNode4;
    public TableColumn colMethod;
    public JFXComboBox cmbMethod;
    public TextField txtEmail;
    public JFXButton bttnPlaseOrder;
    @FXML
    private  TextField txtCustId;
    public Label lblNetTotal;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private  TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStockQty;

    @FXML
    private TextField txtUnitPrice;
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private  ComboBox<String> cmbCustomerNIC;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private  TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private DatePicker lblOrderDate;
    @FXML
    private AnchorPane rootNodePayment;

    @FXML
    private  TableView<CartTm> tblOrderCart;

    public AnchorPane customRoot;
    private static ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    private  double netTotal = 0;
    private static String cust_id;

    public static String getCust_id() {
        return cust_id;
    }

    @FXML
    void btnShowTable(ActionEvent event) throws IOException {

    }



    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String item_code = cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String description = txtItemType.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double amount = qty * unitPrice;
        String pay_method = String.valueOf(cmbMethod.getValue());
        String email = txtEmail.getText();
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });


            if (txtQty.getText().equals(txtStockQty.getText())|| Integer.parseInt(txtQty.getText()) < Integer.parseInt(txtStockQty.getText())) {

                for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                    if (item_code.equals(colItemCode.getCellData(i))) {
                        qty += cartList.get(i).getQty();

                        if (qty > Integer.parseInt(txtStockQty.getText())) {
                            new Alert(Alert.AlertType.ERROR, "Item is out of stock").show();
                            return;
                        }

                        amount = unitPrice * qty;

                        cartList.get(i).setQty(qty);
                        cartList.get(i).setAmount(amount);


                        initialize();

                        tblOrderCart.refresh();
                        calculateNetTotal();
                        txtQty.setText("");
                        txtItemType.setText("");
                        cmbItemCode.setValue("");
                        return;
                    }
                }

                    CartTm cartTm = new CartTm(item_code,  qty,description, unitPrice, amount,pay_method,email, btnRemove);

                    cartList.add(cartTm);
                    tblOrderCart.setItems(cartList);



            }else {
                new Alert(Alert.AlertType.ERROR, "Item is out of stock").show();
                return;
            }

        txtQty.setText("");
        txtItemType.setText("");
        cmbItemCode.setValue("");
        calculateNetTotal();
    }



    @FXML
    void btnPlaceOrder(ActionEvent event) throws IOException {
        String order_id = txtOrderId.getText();
        String cust_NIC = cmbCustomerNIC.getValue();
         cust_id = txtCustId.getText();
        String user_id = NewLoginController.getUserId();
        Date date = Date.valueOf(LocalDate.now());


        var orders = new Orders(order_id, cust_NIC,cust_id, user_id, date);


        List<OrderDetails> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = cartList.get(i);

            OrderDetails OD = new OrderDetails(

                    tm.getItem_code(),
                    order_id,
                    tm.getQty(),
                    tm.getDescription(),
                    tm.getUnit_price(),
                    tm.getAmount(),
                    tm.getPay_method(),
                    tm.getEmail()
            );
            odList.add(OD);
        }

        PlaceOrder PO = new PlaceOrder(orders, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(PO);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();


            } else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        txtNetTotal.setText(String.valueOf(netTotal));
        System.out.println("net total = " + netTotal);
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("pay_method"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }
    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String cust_NIC = (String) cmbCustomerNIC.getValue();

        try {
            Customer customer = CustomerRepo.searchByNIC(cust_NIC);

            txtCustName.setText(customer.getCust_name());
            txtCustId.setText(customer.getCust_id());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
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

    public void comboMethod() {
        cmbMethod.getItems().addAll(PayMethod.values());

        cmbMethod.setOnAction(Event -> {
            PayMethod selected = (PayMethod) cmbMethod.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
    public void initialize() {
        loadNextOrderId();
        setCellValueFactory();
        getCustomerNIC();
        getItemCodes();
        comboMethod();
    }

    private  void loadNextOrderId() {

        try {
            String currentId = OrderRepo.currentId();
            String nextId = nextId(currentId);

            txtOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1],10);
            return "O" + String.format("%04d", ++id);
        }
        return "O0001";
    }



    private void getCustomerNIC() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> nicList = CustomerRepo.getNIC();

            obList.addAll(nicList);
            cmbCustomerNIC.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


    public void btnaddtocart(MouseEvent mouseEvent) {
    }


    @FXML
    void btnPrintBill(ActionEvent event) throws SQLException, JRException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/coco_bill.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);


        Map<String, Object> data = new HashMap<>();
        data.put("OrderID",txtOrderId.getText());
        data.put("NetTotal",txtNetTotal.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        dbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
        txtCustName.setText("");
        txtCustId.setText("");
        txtEmail.setText("");
        txtNetTotal.setText("");
        txtItemType.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        cmbItemCode.setValue("");
        cmbCustomerNIC.setValue("");
        cmbMethod.setValue("");
        txtStockQty.setText("");
        tblOrderCart.getItems().clear();
        loadNextOrderId();

        /*JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/coco_bill.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);


        Map<String, Object> data = new HashMap<>();
        data.put("Order ID",txtOrderId.getText());
        data.put("Net Total",txtNetTotal.getText());
        data.put("custId",txtCustId.getText());
        data.put("description",txtItemType.getText());
        data.put("unitPrice",txtUnitPrice.getText());
        data.put("qty",txtQty.getText());
        data.put("amount",txtNetTotal.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        dbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);*/

    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.INT.INT, (JFXTextField) txtQty);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.email.email, (JFXTextField) txtEmail);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.INT, (JFXTextField) txtQty)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumb.controller.Util.TextField.email, (JFXTextField) txtEmail)) return false;
        return true;
    }
    public void txtQtyOnKeyType(KeyEvent keyEvent) {
        /*if (txtQty.getText().equals(Integer.parseInt(txtStockQty.getText()))) {
            txtQty.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
        }
        else {
            btnAddToCart.setDisable(true);
            txtQty.setStyle("-fx-border-color: red");
        }*/
    }
    public void txtEmailOnKeyType(KeyEvent keyEvent) {
       /* if (isValid()) {
            txtEmail.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
            bttnPlaseOrder.setDisable(false);
        }
        else {
            btnAddToCart.setDisable(true);
            bttnPlaseOrder.setDisable(true);
            txtEmail.setStyle("-fx-border-color: red");
        }*/
    }
}
