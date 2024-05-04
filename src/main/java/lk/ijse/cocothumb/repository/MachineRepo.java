package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.controller.LoginFormController;
import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Customer;
import lk.ijse.cocothumb.model.Machine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
