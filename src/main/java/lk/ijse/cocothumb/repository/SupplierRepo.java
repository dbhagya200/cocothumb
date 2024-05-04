package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, supplier.getSupp_id());
        pstm.setObject(2, supplier.getSupp_name());
        pstm.setObject(3, supplier.getSupp_address());
        pstm.setObject(4, supplier.getSupp_contact());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supp_name=?, supp_address=?, supp_contact=? WHERE supp_id=?";

            PreparedStatement pstm = dbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, supplier.getSupp_name());
            pstm.setObject(2, supplier.getSupp_address());
            pstm.setObject(3, supplier.getSupp_contact());
            pstm.setObject(4, supplier.getSupp_id());

            return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String suppId) throws SQLException {

        String sql = "DELETE FROM supplier WHERE supp_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, suppId);

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String suppId) throws SQLException {
        String sql = "SELECT * FROM supplier  WHERE supp_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, suppId);
        ResultSet resultSet = pstm.executeQuery();

        Supplier supplier = null;

        if (resultSet.next()) {
            String supp_id = resultSet.getString(1);
            String supp_name = resultSet.getString(2);
            String supp_address = resultSet.getString(3);
            String supp_contact = resultSet.getString(4);

            supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);
        }
        return supplier;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT supp_id FROM supplier ORDER BY supp_id desc LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> suppliersList = new ArrayList<>();
        while (resultSet.next()) {
            String supp_id = resultSet.getString(1);
            String supp_name = resultSet.getString(2);
            String supp_address = resultSet.getString(3);
            String supp_contact = resultSet.getString(4);

           Supplier supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);
            suppliersList.add(supplier);
        }
        return suppliersList;
    }
}

