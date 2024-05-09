package lk.ijse.cocothumb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrder {
    private Orders orders;
    private List<OrderDetails> odList;
   // private Payment payment;

}
