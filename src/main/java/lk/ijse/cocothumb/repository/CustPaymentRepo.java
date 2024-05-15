package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.LoginFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustPaymentRepo {

    public static String currentId() throws SQLException {
        String sql = "SELECT pay_id FROM cust_payment ORDER BY CAST(SUBSTRING(pay_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, payment.getPay_id());
        pstm.setObject(2, payment.getCust_id());
        pstm.setObject(3, payment.getPay_method());
        pstm.setObject(4, payment.getT_price());
        pstm.setObject(5, payment.getDate());



        return pstm.executeUpdate() > 0;
    }
}
