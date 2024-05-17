package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.AddMachineController;
import lk.ijse.cocothumb.controller.UserFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public static boolean save(Employee employee) throws SQLException {

        String sql = "INSERT INTO employee VALUES (?, ?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, employee.getE_Id());
        pstm.setObject(2, employee.getE_Name());
        pstm.setObject(3, employee.getE_jobrole());
        pstm.setObject(4, employee.getE_Address());
        pstm.setObject(5, employee.getE_Contact());
        pstm.setObject(6, employee.getE_Salary());
        pstm.setObject(7, employee.getE_email());
        pstm.setObject(8, AddMachineController.getMachineId());


        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String e_jobrole = resultSet.getString(3);
            String e_address = resultSet.getString(4);
            String e_contact = resultSet.getString(5);
            double e_salary = Double.parseDouble(resultSet.getString(6));
            String e_email = resultSet.getString(7);
            String machine_id = resultSet.getString(8);

            System.out.println("bla bla bla bla bla bla bla ");
            Employee employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact,e_salary,e_email,machine_id);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE e_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String e_jobrole = resultSet.getString(3);
            String e_address = resultSet.getString(4);
            String e_contact = resultSet.getString(5);
            double e_salary = Double.parseDouble(resultSet.getString(6));
            String e_email = resultSet.getString(7);
            String machine_id = resultSet.getString(8);


            employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact, e_salary,e_email,machine_id);
        }
        return employee;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET e_name = ?,e_jobrole = ?, e_address = ?, e_contact = ?,e_salary = ?,,e_email = ?,machine_id = ? WHERE e_id = ?";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getE_Name());
        pstm.setObject(2, employee.getE_jobrole());
        pstm.setObject(3, employee.getE_Address());
        pstm.setObject(4, employee.getE_Contact());
        pstm.setObject(5, employee.getE_salary());
        pstm.setObject(6, employee.getE_Id());
        pstm.setObject(7, employee.getE_email());
        pstm.setObject(8, employee.getMachine_id());


        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT e_id FROM employee ORDER BY CAST(SUBSTRING(e_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean delete(String e_id) throws SQLException {
        String sql = "DELETE FROM employee WHERE e_id = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, e_id);

        return pstm.executeUpdate() > 0;
    }
}
