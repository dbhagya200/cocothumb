package lk.ijse.cocothumb.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumb.model.Payment;
import lk.ijse.cocothumb.repository.CustPaymentRepo;
import lk.ijse.cocothumb.repository.OrderRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CustPaymentController {

    @FXML
    private Label Date;

    @FXML
    private Label Time;

    @FXML
    private static Label lbNetTotal;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNodePayment;

    @FXML
    private static JFXTextField txtCustId;

    @FXML
    private JFXTextField  txtEmail;

    @FXML
    private JFXTextField  txtPayId;

    @FXML
    private JFXComboBox<PayMethod> cmbMethod;

    @FXML
    void btnClear(ActionEvent event) {
        cmbMethod.setValue(null);
        txtEmail.setText("");
    }

    @FXML
    public  void  btnSave() throws SQLException {
    String pay_id = txtPayId.getText();
    String cust_id = txtCustId.getText();
    String pay_method = cmbMethod.getValue().toString();
    double t_price = Double.parseDouble(lbNetTotal.getText());
    Date date = java.sql.Date.valueOf(LocalDate.now());


        Payment payment = new Payment(pay_id, cust_id, pay_method,t_price, date);
        try {
            boolean isSaved = CustPaymentRepo.save(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Your payment saved!").show();
            }
            //initialize();
            //btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


   public void comboMethod() {
        cmbMethod.getItems().addAll(PayMethod.values());

        cmbMethod.setOnAction(Event -> {
            PayMethod selected = cmbMethod.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });
    }
    private  void loadNextId() {

        try {
            String currentId = CustPaymentRepo.currentId();
            String nextId = nextId(currentId);

            txtPayId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("p");
            int id = Integer.parseInt(split[1],10);
            return "p" + String.format("%04d", ++id);
        }
        return "p0001";
    }


    public void initialize() {
        loadNextId();
        setdate();
        settime();
        comboMethod();
    }

    private void settime() {
        LocalTime localTime = LocalTime.now();
        Time.setText(localTime.toString());
    }

    private void setdate() {
        LocalDate localDate = LocalDate.now();
        Date.setText(localDate.toString());
    }


    public void btnSave(ActionEvent actionEvent) {
    }


    }

