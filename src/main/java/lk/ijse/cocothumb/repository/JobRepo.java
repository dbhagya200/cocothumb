package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.Employee;
import lk.ijse.cocothumb.model.Job;
import lk.ijse.cocothumb.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRepo {
    public static List<Job> getAll() throws SQLException {
        String sql = "SELECT * FROM job_role";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Job> jobList = new ArrayList<>();
        while (resultSet.next()) {
            String job_role = resultSet.getString(1);

            System.out.println("bla bla bla bla bla bla bla ");
            Job job = new Job(job_role);
            jobList.add(job);
        }
        return jobList;

    }
}

