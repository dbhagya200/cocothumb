package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.SPlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class SPlaceOrderRepo {

    public static boolean splaceOrder(SPlaceOrder spo) throws SQLException {
        Connection connection = dbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            boolean isOrderSaved = SuppOrderRepo .save(spo.getSuppOrder());
            System.out.println("1");
            if (isOrderSaved){
                boolean isSOrderDetailSaved = SuppDetailsRepo.save(spo.getSodList());
                System.out.println("2");
                if (isSOrderDetailSaved){
                    boolean isItemQtyUpdate = ItemRepo.updateSQty(spo.getSodList());
                    System.out.println("3");
                    if (isItemQtyUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}

