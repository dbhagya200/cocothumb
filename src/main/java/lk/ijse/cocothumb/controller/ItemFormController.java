package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.cocothumb.model.Item;
import lk.ijse.cocothumb.model.tModel.CustomerTm;
import lk.ijse.cocothumb.model.tModel.ItemTm;
import lk.ijse.cocothumb.repository.CustomerRepo;
import lk.ijse.cocothumb.repository.EmployeeRepo;
import lk.ijse.cocothumb.repository.ItemRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtCode1;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colitemType;

    @FXML
    private AnchorPane rootNode7;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private JFXTextField txtItemType;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtQtyOnHandCompany;

    @FXML
    private JFXTextField txtUnitPrice;
    private List<Item> itemList = new ArrayList<>();



    @FXML
    void btnClear(ActionEvent event) {
        txtCode1.setText("");
        txtCode.setText("");
        txtItemType.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHandCompany.setText("");
        txtQtyOnHand.setText("");
        loadNextItemId();

    }


    @FXML
    void btnSave(ActionEvent event) {
        String item_code = txtCode.getText();
        String item_type = txtItemType.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());
        double unit_price_forCompany = Double.parseDouble(txtQtyOnHandCompany.getText());
        String stock_qty = txtQtyOnHand.getText();
        String user_id = LoginFormController.getUserId();

        Item item = new Item(item_code, item_type, unit_price,unit_price_forCompany, stock_qty, user_id);
        try {
            boolean isSaved = ItemRepo.save(item);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "items saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        btnClear(event);

    }

    @FXML
    void btnUpdate(ActionEvent event) {
        String item_code = txtCode.getText();
        String item_type = txtItemType.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());
        double unit_price_forCompany = Double.parseDouble(txtQtyOnHandCompany.getText());
        String stock_qty = txtQtyOnHand.getText();
        String user_id = LoginFormController.getUserId();

        Item item = new Item(item_code,item_type, unit_price,unit_price_forCompany, stock_qty, user_id);

        try {
            boolean isUpdated = ItemRepo.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        btnClear(event);
    }



    public void SearchCode(ActionEvent actionEvent) {
        String item_code = txtCode.getText();

        try {
            Item item = ItemRepo.searchByCode(item_code);

            if (item != null) {
                txtCode.setText(item.getItem_code());
                txtItemType.setText(item.getItem_type());
                txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtQtyOnHand.setText(item.getStock_qty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void initialize() {
        this.itemList = getAllItems();
        setItemValue();
        loadItemTable();
        loadNextItemId();

    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        for (Item item : itemList) {
            ItemTm itemTm = new ItemTm(
                    item.getItem_code(),
                    item.getItem_type(),
                    item.getUnit_price(),
                    item.getUnit_price_forCompany(),
                    item.getStock_qty()
            );

            tmList.add(itemTm);
        }
        tblItem.setItems(tmList);
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setItemValue() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colitemType.setCellValueFactory(new PropertyValueFactory<>("item_type"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("stock_qty"));

    }

    private void loadNextItemId() {
        try {
            String currentId = ItemRepo.currentId();
            String nextId = nextId(currentId);

            txtCode.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("i");
            int id = Integer.parseInt(split[1],10);
            return "i" + String.format("%04d", ++id);
        }
        return "i0001";
    }

    private List<Item> getAllItems() {
        List<Item> itemList = null;
        try {
            itemList = ItemRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    public void btnDelete(ActionEvent actionEvent) {
        String item_code = txtCode.getText();

        try {
            boolean isDeleted = ItemRepo.delete(item_code);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        btnClear(actionEvent);
    }

    public void actionsearch(ActionEvent actionEvent) {
        String item_code = txtCode1.getText();

        try {
            Item item = ItemRepo.searchByCode(item_code);

            if (item != null) {
                txtCode.setText(item.getItem_code());
                txtItemType.setText(item.getItem_type());
                txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtQtyOnHandCompany.setText(String.valueOf(item.getUnit_price_forCompany()));
                txtQtyOnHand.setText(item.getStock_qty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}

