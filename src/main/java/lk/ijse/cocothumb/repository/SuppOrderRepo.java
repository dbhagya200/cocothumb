package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.LoginFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.SuppOrder;

import java.sql.*;

public class SuppOrderRepo {

    public static boolean save(SuppOrder suppOrder) throws SQLException {
        String sql = "INSERT INTO supp_order VALUES(?, ?, ?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, suppOrder.getOrder_id());
        pstm.setString(2, suppOrder.getSupp_id());
        pstm.setString(3, LoginFormController.getUserId());
        pstm.setDate(4, (Date) suppOrder.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT order_id FROM supp_order ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
