package lk.ijse.cocothumb.model.tModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
    String item_code;
    String item_type;
    double unit_price;
    String stock_qty;
}
