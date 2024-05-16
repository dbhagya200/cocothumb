package lk.ijse.cocothumb.controller.Util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isValidTextField(TextField textField, String text){
        String field = "";
        switch (textField){
            case NIC :
                field = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
            break;

            case contact :
                field = "([0-9]{10})$";
            break;
            case qty :
                field = "^([0-9]{1,3})$";
            break;
            case salary :
                field = "^([0-9]{1,}[.]([0-9]){1,})$";
            break;
            case name :
                field = "^[A-z|\\s]{3,}$";
            break;
            case email:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
            break;
            case address :
                field = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
            break;
            case Double:
                field = "^([0-9]){1,}[.]([0-9]){1,}$";
            break;
            case invoice:
                field = "^([0-9]){1,}$";
            break;
            case NONE_CHARACTER:
                field = "^[\\W]{1,}$";
            break;
            case INT:
                field = "^\\d+$";
            break;

        }
        Pattern pattern = Pattern.compile(field);

        if (text!=null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }else {
            return false;
        }

    }
    public static boolean setTextColor(TextField location, JFXTextField field){
        if (Regex.isValidTextField(location,field.getText())){
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
}
