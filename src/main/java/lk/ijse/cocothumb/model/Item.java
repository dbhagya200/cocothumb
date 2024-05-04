package lk.ijse.cocothumb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
String item_code;
String item_type;
double unit_price;
String stock_qty;
String user_id;



}
