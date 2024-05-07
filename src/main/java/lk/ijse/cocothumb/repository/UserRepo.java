package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.EmployeeFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    public static boolean save(User user) throws SQLException {
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, user.getUser_id());
        pstm.setObject(2, user.getU_name());
        pstm.setObject(3, user.getU_password());
        pstm.setObject(4, user.getU_role());
        pstm.setObject(5, user.getU_email());
        pstm.setObject(6, EmployeeFormController.getE_id());


        return pstm.executeUpdate() > 0;
    }

    public static String currentUserId() throws SQLException {
        String sql = "SELECT user_id FROM user ORDER BY user_id desc LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM user";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<User> usersList = new ArrayList<>();
        while (resultSet.next()) {
            String user_id = resultSet.getString(1);
            String u_name = resultSet.getString(2);
            String u_passsword = resultSet.getString(3);
            String u_email = resultSet.getString(4);
            String u_role = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            User user = new User(user_id, u_name, u_passsword,u_email, u_role, e_id);
            usersList.add(user);
        }
        return usersList;
    }

    public static String currentEmpId() throws SQLException {
        String sql = "SELECT e_id FROM employee ORDER BY e_id desc LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static User searchById(String userId) throws SQLException {

        String sql = "SELECT * FROM user WHERE user_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, userId);
        ResultSet resultSet = pstm.executeQuery();

        User user = null;

        if (resultSet.next()) {
            String user_id = resultSet.getString(1);
            String u_name = resultSet.getString(2);
            String u_password = resultSet.getString(3);
            String u_email = resultSet.getString(4);
            String u_role = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            user = new User(user_id, u_name, u_password, u_email, u_role, e_id);
        }
        return user;
    }


}
