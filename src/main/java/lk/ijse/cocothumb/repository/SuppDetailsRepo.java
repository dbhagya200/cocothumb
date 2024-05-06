package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.SuppDetails;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SuppDetailsRepo {

    public static boolean save(List<SuppDetails> sodList) throws SQLException {
        for (SuppDetails sd : sodList) {
            if(!save(sd)) {
                return false;
            }
        }
        return true;
    }
    private static boolean save(SuppDetails sd) throws SQLException {
        System.out.println(sd);
        String sql = "INSERT INTO supp_details VALUES(?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, sd.getItem_code());
        pstm.setString(2, sd.getSupp_id());
        pstm.setString(3, sd.getOrder_id());
        pstm.setInt(4, sd.getQty());
        pstm.setString(5, sd.getDescription());
        pstm.setDouble(6, sd.getUnit_price_forCompany());
        pstm.setDouble(7, sd.getAmount());

        return pstm.executeUpdate() > 0;
    }
}
