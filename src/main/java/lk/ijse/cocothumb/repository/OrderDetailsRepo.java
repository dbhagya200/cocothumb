package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.OrderDetails;

import java.sql.*;
import java.util.List;

public class OrderDetailsRepo {

    public static boolean save(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails OD : odList) {
            if(!save(OD)) {
                return false;
            }
        }
        return true;
    }
    private static boolean save(OrderDetails OD) throws SQLException {
        String sql = "INSERT INTO order_details VALUES(?, ?, ?, ?,?,?,?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, OD.getItem_code());
        pstm.setString(2, OD.getOrder_id());
        pstm.setInt(3, OD.getQty());
        pstm.setString(4, OD.getDescription());
        pstm.setDouble(5, OD.getUnit_price());
        pstm.setDouble(6, OD.getAmount());
        pstm.setString(7, OD.getPay_method());
        pstm.setString(8, OD.getEmail());

        return pstm.executeUpdate() > 0;
    }

}
