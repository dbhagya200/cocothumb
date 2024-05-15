package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.LoginFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
public static boolean save(Customer customer) throws SQLException {
    String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?,?)";
    PreparedStatement pstm = dbConnection.getInstance().getConnection()
            .prepareStatement(sql);
    pstm.setObject(1, customer.getCust_id());
    pstm.setObject(2, customer.getCust_NIC());
    pstm.setObject(3, customer.getCust_name());
    pstm.setObject(4, customer.getCust_address());
    pstm.setObject(5, customer.getCust_contact());
    System.out.println("yann hadnne");
    pstm.setObject(6, LoginFormController.getUserId());
    System.out.println("giya giya giyad blpan");


    return pstm.executeUpdate() > 0;

}


    public static boolean update(Customer customer) throws SQLException {
    String sql = "UPDATE customer SET  cust_NIC= ?, cust_name = ?, cust_address = ?, cust_contact = ? WHERE cust_id = ?";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, customer.getCust_NIC());
        pstm.setObject(2, customer.getCust_name());
        pstm.setObject(3, customer.getCust_address());
        pstm.setObject(4, customer.getCust_contact());
        pstm.setObject(5, customer.getCust_id());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchByNIC(String NIC) throws SQLException {
        String sql = "SELECT * FROM customer  WHERE cust_NIC = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, NIC);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);
        }
        return customer;
    }
    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM customer  WHERE cust_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);
        }
        return customer;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }


    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> customersList = new ArrayList<>();
        while (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            Customer customer = new Customer(cust_id, cust_NIC,cust_name, cust_address, cust_contact,user_id);
            customersList.add(customer);
        }
        return customersList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT cust_NIC,cust_id FROM customer";

        Connection connection = dbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static List<String> getNIC() throws SQLException {
        String sql = "SELECT cust_NIC FROM customer";

        Connection connection = dbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> nicList = new ArrayList<>();

        while (resultSet.next()) {
            nicList.add(resultSet.getString(1));
        }
        return nicList;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT cust_id FROM customer ORDER BY CAST(SUBSTRING(cust_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
    }

