package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.LoginFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Item;
import lk.ijse.cocothumb.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, item.getItem_code());
        pstm.setObject(2, item.getItem_type());
        pstm.setObject(3, item.getUnit_price());
        pstm.setObject(4, item.getStock_qty());
        pstm.setObject(5, LoginFormController.getUserId());

        return pstm.executeUpdate() > 0;

    }


    public static Item searchByCode(String item_code) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_code = ?";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, item_code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT item_code FROM item ORDER BY item_code desc LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String item_type = resultSet.getString(2);
            double unit_price = Double.parseDouble(resultSet.getString(3));
            String stock_qty = resultSet.getString(4);
            String user_id = resultSet.getString(5);


            Item item = new Item(item_code, item_type,unit_price, stock_qty,user_id);
            itemList.add(item);
        }
        return itemList;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET item_type = ?, unit_price = ?, stock_qty = ? WHERE item_code = ?";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1 ,item.getItem_type());
        pstm.setObject(2 ,item.getUnit_price());
        pstm.setObject(3 ,item.getStock_qty());
        pstm.setObject(4 ,item.getItem_code());


        return pstm.executeUpdate() > 0;

    }


    public static boolean delete(String item_code) throws SQLException {
        String sql = "DELETE FROM item WHERE item_code = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, item_code);

        return pstm.executeUpdate() > 0;

    }

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT item_code FROM item";

        Connection connection = dbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> codeList = new ArrayList<>();

        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static boolean updateQty(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }
    private static boolean updateQty(OrderDetails od) throws SQLException {
        String sql = "UPDATE item SET stock_qty = stock_qty - ? WHERE item_code = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);



        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getItem_code());

        return pstm.executeUpdate() > 0;
    }
}

