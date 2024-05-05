package lk.ijse.cocothumb.model.tModel;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTms {
    private String item_code;
    private int qty;
    private String description;
    private double unit_price_forCompany;
    private double amount;
    private JFXButton btnRemove;

    public void setTotal(double amount) {

    }
}
