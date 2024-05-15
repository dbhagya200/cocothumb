package lk.ijse.cocothumb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {
    private String item_code;
    private String order_id;
    private int qty;
    private String description;
    private double unit_price;
    private double amount;
    private String pay_method;
    private String email;
}
