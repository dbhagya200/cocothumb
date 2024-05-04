package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumb.database.dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DashboardFormController {

    public AnchorPane customRoot;
    public TabPane tabPane1;
    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lbldate1;

    @FXML
    private AnchorPane rootNode1;
    private int customerCount;
    private int employeeCount;
    private int itemCount;
    @FXML
    private AnchorPane tabPane;

    @FXML
    void btncustomer(ActionEvent event) throws IOException {
        AnchorPane rootNode3 = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode3);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();

    }

    @FXML
    void btncustomerorders(ActionEvent event) throws IOException {

        AnchorPane rootNode4 = FXMLLoader.load(getClass().getResource("/view/customer_order_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode4);
        stage.setTitle("Customer Order Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnemployee(ActionEvent event) throws IOException {

        AnchorPane rootNode2 = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode2);
        stage.setTitle("Employee Form");
        stage.centerOnScreen();

    }

    @FXML
        void btnexit(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode1.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Login Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnsupplierorders(ActionEvent event) throws IOException {
        AnchorPane rootNode6 = FXMLLoader.load(getClass().getResource("/view/supp_placeorder_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode6);
        stage.setTitle("Supplier Order Form");
        stage.centerOnScreen();

    }

        public void initialize(){
       setdate();
            try {
                customerCount = getCustomerCount();
                employeeCount = getEmployeeCount();
                itemCount = getItemCount();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            setCustomerCount(customerCount);
            setEmployeeCount(employeeCount);
            setItemCount(itemCount);



    }

    private void setItemCount(int itemCount) {
        lblItemCount.setText(String.valueOf(itemCount));
    }

    private int getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS item_count FROM item";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM employee";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int employeeCount = 0;
        if(resultSet.next()) {
            employeeCount = resultSet.getInt("employee_count");
        }
        return employeeCount;
    }

    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM customer";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int customerCount = 0;
        if(resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;
    }

    private void setdate() {
        LocalDate localDate = LocalDate.now();
        lbldate1.setText(localDate.toString());
    }


    public void btnaddsupplier(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode8 = FXMLLoader.load(getClass().getResource("/view/add_supp_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode8);
        stage.setTitle("Add Supplier Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnaddItems(ActionEvent event) throws IOException {
        AnchorPane rootNode7 = FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNode7);
        stage.setTitle("Add Item Form");
        stage.centerOnScreen();
    }

    public void btnHome(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(tabPane);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

    public void btnchangePassword(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNodePassword = FXMLLoader.load(this.getClass().getResource("/view/change_password.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNodePassword);
        stage.setTitle("Change Password Form");
        stage.centerOnScreen();

    }
}
