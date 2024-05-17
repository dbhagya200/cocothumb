package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineRepo {

    public static boolean save(Machine machine) throws SQLException {
        String sql = "INSERT INTO machine VALUES (?, ?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, machine.getMachine_id());
        pstm.setObject(2, machine.getBrand());


        return pstm.executeUpdate() > 0;

    }

    public static Machine searchById(String machineId) throws SQLException {
        String sql = "SELECT * FROM machine WHERE machine_id = ?";
    PreparedStatement pstm = dbConnection.getInstance().getConnection()
            .prepareStatement(sql);

    pstm.setObject(1, machineId);
        ResultSet resultSet = pstm.executeQuery();

        Machine machine = null;
        if (resultSet.next()) {
            String machine_id = resultSet.getString(1);
            String brand = resultSet.getString(2);

             machine = new Machine(machine_id,brand);
        }
        return machine;
    }

    public static boolean update(Machine machine)throws SQLException {
        String sql = "UPDATE machine SET brand = ? WHERE machine_id = ?";
        try {
            PreparedStatement pstm = dbConnection.getInstance().getConnection()
                    .prepareStatement(sql);

            pstm.setObject(1, machine.getBrand());
            pstm.setObject(2, machine.getMachine_id());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT machine_id FROM machine ORDER BY CAST(SUBSTRING(machine_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<Machine> getAll() throws SQLException {
        String sql = "SELECT * FROM machine";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Machine> machineList = new ArrayList<>();
        while (resultSet.next()) {
            String machine_id = resultSet.getString(1);
            String brand = resultSet.getString(2);

            Machine machine = new Machine(machine_id,brand);
            machineList.add(machine);
        }
        return machineList;
    }
}
