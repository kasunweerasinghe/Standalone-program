package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeAddCustomerFormController {
    public JFXTextField txtCId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtNic;
    public JFXButton btnSaveCustomer;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveCustomer();
    }

    private void saveCustomer() {
        Customer c = new Customer(
                txtCId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                txtNic.getText()
        );
        try{
            if((CrudUtil.execute("INSERT INTO PetShop.Customer VALUES (?,?,?,?,?)",c.getCId(),c.getName(),c.getAddress(),c.getContact(),c.getNic())));
            new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearAllTexts();

    }

    public void textFields_key_Released(KeyEvent keyEvent) {
        checkPattern();
        ValidationUtil.validate(map,btnSaveCustomer);

        if(keyEvent.getCode()== KeyCode.ENTER){
            Object response =ValidationUtil.validate(map,btnSaveCustomer);

            if(response instanceof JFXTextField){
                JFXTextField jfxTextField = (JFXTextField)response;
                jfxTextField.requestFocus();
            }else if(response instanceof Boolean){
                saveCustomer();
            }
        }
    }

    public void checkPattern(){
        Pattern CIdPattern = Pattern.compile("^(CId00-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,35}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern contactPattern = Pattern.compile("^(077|071|078|075|031|076|011)-[0-9]{7}$");
        Pattern nicPattern = Pattern.compile("^([0-9]{12}|[0-9]{9}(V))$");

        map.put(txtCId,CIdPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
        map.put(txtNic,nicPattern);

    }

    public void clearAllTexts(){
        txtCId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtNic.clear();
    }
}
