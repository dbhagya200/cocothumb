package lk.ijse.cocothumb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuppDetails {

    private String item_code;
    private String supp_id;
    private String order_id;
    private int qty;
    private String description;
    private double unit_price_forCompany;
    private double amount;
}
