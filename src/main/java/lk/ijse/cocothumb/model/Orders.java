package lk.ijse.cocothumb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {

    private String order_id;
    private String cust_NIC;
    private String cust_id;
    private String user_id;
    private Date order_date;


}
