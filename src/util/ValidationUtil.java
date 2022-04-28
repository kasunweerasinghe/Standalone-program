package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<JFXTextField,Pattern> map, JFXButton btn) {

        for (JFXTextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if(!pattern.matcher(key.getText()).matches()){
                //if the EId is not matching
                addError(key,btn);
                return key;
            }
            //if the EId is matching
            removeError(key,btn);
        }
        return true;
    }

    private static void removeError(JFXTextField jfxTextField,JFXButton btn) {
        jfxTextField.setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }

    private static void addError(JFXTextField jfxTextField,JFXButton btn) {
        if(jfxTextField.getText().length()>0){
            jfxTextField.setStyle("-fx-border-color: red");

        }
        btn.setDisable(true);
    }
}
