package lk.ijse.cocothumb.model;

import lk.ijse.cocothumb.model.tModel.EmployeeTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public final class Employee extends EmployeeTm {
    private String e_Id;
    private String e_Name;
    private String e_Address;
    private String e_Contact;
    private double e_Salary;
    private String machine_id;



}
