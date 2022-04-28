package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Driver;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminAddDriverFormController {
    public JFXTextField txtDId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAge;
    public JFXTextField txtStatus;
    public JFXButton btnSaveDriver;
    LinkedHashMap<JFXTextField, Pattern > map = new LinkedHashMap<>();


    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveDriver();
    }

    private void saveDriver() {
        Driver d = new Driver(
                txtDId.getText(),
                txtName.getText(),
                txtContact.getText(),
                Integer.parseInt(txtAge.getText()),
                txtStatus.getText()
        );


        try {
            if(CrudUtil.execute("INSERT INTO PetShop.Driver VALUES(?,?,?,?,?)",d.getDId(),d.getName(),d.getContact(),d.getAge(),d.getStatus())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearAllTexts();
    }

    public void textFields_key_Released(KeyEvent keyEvent) {
        checkPattern();
        ValidationUtil.validate(map,btnSaveDriver);

        if(keyEvent.getCode()== KeyCode.ENTER){
            Object response =ValidationUtil.validate(map,btnSaveDriver);

            if(response instanceof JFXTextField){
                JFXTextField jfxTextField = (JFXTextField)response;
                jfxTextField.requestFocus();
            }else if(response instanceof Boolean){
                saveDriver();
            }
        }
    }

    public void checkPattern(){
        Pattern DIdPattern = Pattern.compile("^(DId00-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,35}$");
        Pattern contactPattern = Pattern.compile("^(077|071|078|075|031|076)-[0-9]{7}$");
        Pattern agePattern = Pattern.compile("^[1-9]{2}$");
        Pattern statusPattern = Pattern.compile("^(Available)$");

        map.put(txtDId,DIdPattern);
        map.put(txtName,namePattern);
        map.put(txtContact,contactPattern);
        map.put(txtAge,agePattern);
        map.put(txtStatus,statusPattern);

    }
    public void clearAllTexts(){
        txtDId.clear();
        txtName.clear();
        txtContact.clear();
        txtAge.clear();
        txtStatus.clear();
    }
}



