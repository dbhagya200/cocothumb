package lk.ijse.cocothumb.repository;

import lk.ijse.cocothumb.database.dbConnection;
import lk.ijse.cocothumb.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder PO) throws SQLException {
        Connection connection = dbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(PO.getOrders());
            System.out.println("1");
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailsRepo.save(PO.getOdList());
                System.out.println("2");
                if (isOrderDetailSaved) {
                    boolean isItemQtyUpdate = ItemRepo.updateQty(PO.getOdList());
                    System.out.println("3");
                    if (isItemQtyUpdate) {
                       // boolean isPaymentSaved = CustPaymentRepo.save(PO.getPayment());
                        System.out.println("4");
                        if (isItemQtyUpdate) {
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }
}
