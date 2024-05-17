package lk.ijse.cocothumb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.cocothumb.database.dbConnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
public class NewDashboardController {

    public AnchorPane customRoot;
    @FXML
    private AnchorPane tabPane1;
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

    private int ordersCount;
    @FXML
    private AnchorPane tabPane;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblOrdersCount;

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
        /*AnchorPane rootNodeLogin = FXMLLoader.load(getClass().getResource("/view/new_login.fxml"));
        Stage stage = (Stage) rootNode1.getScene().getWindow();

        stage.setScene(new Scene(rootNodeLogin));
        stage.setTitle("Login Form");
        stage.centerOnScreen();*/
        System.exit(0);

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
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime currentTime = LocalTime.now();
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy, MMM dd");
                    String formattedDate = date.format(dateFormatter);
                    String formattedTime = currentTime.format(formatter);
                    lblTime.setText(formattedTime);
                    lbldate1.setText(formattedDate);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        try {
            customerCount = getCustomerCount();
            employeeCount = getEmployeeCount();
            itemCount = getItemCount();
            ordersCount = getOrdersCount();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCustomerCount(customerCount);
        setEmployeeCount(employeeCount);
        setItemCount(itemCount);
        setOrdersCount(ordersCount);



    }

    private void setOrdersCount(int ordersCount) {
        lblOrdersCount.setText(String.valueOf(ordersCount));
    }

    private int getOrdersCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS orders_count FROM order_details";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int ordersCount = 0;
        if(resultSet.next()) {
            ordersCount = resultSet.getInt("orders_count");
        }
        return ordersCount;
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

    public void btnlogout(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/login_form_old.fxml"));
        Stage stage = (Stage) rootNode1.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    public void actionchange(MouseEvent mouseEvent) throws IOException {
        AnchorPane rootNodePassword = FXMLLoader.load(this.getClass().getResource("/view/change_password.fxml"));
        Stage stage = (Stage) customRoot.getScene().getWindow();
        customRoot.getChildren().clear();
        customRoot.getChildren().add(rootNodePassword);
        stage.setTitle("Change Password Form");
        stage.centerOnScreen();
    }

    public void actionsearch(ActionEvent actionEvent) {
    }


    @FXML
    void onsetting(MouseEvent event) {

    }
    @FXML
    void onlogout(MouseEvent event) throws IOException {
        AnchorPane rootNodeLogin = FXMLLoader.load(getClass().getResource("/view/new_login.fxml"));
        Stage stage = (Stage) rootNode1.getScene().getWindow();

        stage.setScene(new Scene(rootNodeLogin));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNodeLogin = FXMLLoader.load(getClass().getResource("/view/new_login.fxml"));
        Stage stage = (Stage) rootNode1.getScene().getWindow();

        stage.setScene(new Scene(rootNodeLogin));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }
    @FXML
    void change(ActionEvent event) throws IOException {
        AnchorPane rootNodeChange = FXMLLoader.load(getClass().getResource("/view/change_password.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Change Password ");
        popupStage.setScene(new Scene(rootNodeChange));
        popupStage.showAndWait();
    }
}
