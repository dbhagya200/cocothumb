package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.NewLoginController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Orders;

import java.sql.*;

public class OrderRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }


    public static boolean save(Orders orders) throws SQLException {
        System.out.println(orders);
        String sql = "INSERT INTO orders VALUES(?, ?, ?,?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, orders.getOrder_id());
        pstm.setString(2, orders.getCust_NIC());
        pstm.setString(3, orders.getCust_id());
        pstm.setString(4, NewLoginController.getUserId());
        pstm.setDate(5, (Date) orders.getOrder_date());

        return pstm.executeUpdate() > 0;
    }


}

