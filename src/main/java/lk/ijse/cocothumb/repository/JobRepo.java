package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Job;
import lk.ijse.cocothumb.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRepo {

    public static Job searchByJobRole(String jobRole) throws SQLException {
        String sql = "SELECT * FROM job_role WHERE job_role = ?";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, jobRole);
        ResultSet resultSet = pstm.executeQuery();

        Job job = null;

        if (resultSet.next()) {
            String job_role = resultSet.getString(1);

            job = new Job(job_role);
        }
        return job;
    }


    }

