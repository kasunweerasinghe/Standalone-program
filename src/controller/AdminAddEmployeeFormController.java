package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xpath.internal.operations.Bool;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminAddEmployeeFormController {

    public JFXTextField txtEId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtUserName;
    public JFXTextField pwdPassword;
    public JFXButton btnSaveEmployee;
    LinkedHashMap<JFXTextField, Pattern > map = new LinkedHashMap<>();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveEmployee();
    }

    private void saveEmployee() {
        Employee E = new Employee(
                txtEId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(),
                txtUserName.getText(), pwdPassword.getText()
        );

        try {
            if(CrudUtil.execute("INSERT INTO PetShop.Employee VALUES (?,?,?,?,?,?)",E.getEId(),E.getName(),E.getAddress(),E.getContact(),E.getUserName(),E.getPassword())){
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
        ValidationUtil.validate(map,btnSaveEmployee);

        if(keyEvent.getCode()== KeyCode.ENTER){
            Object response =ValidationUtil.validate(map,btnSaveEmployee);

            if(response instanceof JFXTextField){
                JFXTextField jfxTextField = (JFXTextField)response;
                jfxTextField.requestFocus();
            }else if(response instanceof Boolean){
                saveEmployee();
            }
        }
    }
    public void checkPattern(){
        Pattern EIdPattern = Pattern.compile("^(EId00-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern contactPattern = Pattern.compile("^(077|071|078|075|031|076)-[0-9]{7}$");
        Pattern usernamePattern = Pattern.compile("^[A-z ]{3,8}$");
        Pattern passwordPattern = Pattern.compile("^[A-z0-9]{3,8}$");

        map.put(txtEId,EIdPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
        map.put(txtUserName,usernamePattern);
        map.put(pwdPassword,passwordPattern);
    }
    public void clearAllTexts(){
        txtEId.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
        txtUserName.clear();
        pwdPassword.clear();
        txtEId.requestFocus();

    }

}