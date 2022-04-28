package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.PetProduct;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminAddProductFormController {
    public JFXTextField txtPPId;
    public JFXTextField txtName;
    public JFXTextField txtType;
    public JFXTextField txtPrice;
    public JFXTextField txtQTYOnHand;
    public JFXButton btnSaveProduct;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveProduct();
    }

    private void saveProduct() {
        PetProduct pp = new PetProduct(
                txtPPId.getText(),
                txtName.getText(),
                txtType.getText(),
                Integer.parseInt(txtPrice.getText()),
                Integer.parseInt(txtQTYOnHand.getText())
        );

        try { ;
            if(CrudUtil.execute("INSERT INTO PetShop.PetProduct VALUES (?,?,?,?,?)",pp.getPPId(),pp.getName(),pp.getType(),pp.getPrice(),pp.getQTYOnHand())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show(); new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearAllTexts();
    }

    public void textFields_key_Released(KeyEvent keyEvent) {
        checkPattern();
        ValidationUtil.validate(map,btnSaveProduct);

        if(keyEvent.getCode()== KeyCode.ENTER){
            Object response =ValidationUtil.validate(map,btnSaveProduct);

            if(response instanceof JFXTextField){
                JFXTextField jfxTextField = (JFXTextField)response;
                jfxTextField.requestFocus();
            }else if(response instanceof Boolean){
                saveProduct();
            }
        }
    }

    public void checkPattern(){
        Pattern PPIdPattern = Pattern.compile("^(PPId00-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,35}[1-9][0-9]*(.[0-9]{2})?(Kg|g)$");
        Pattern typePattern = Pattern.compile("^(Dog Food|Cat Food|Dog Toy|Cat Toy)$");
        Pattern pricePattern = Pattern.compile("^[0-9]{4,6}$");
        Pattern QTYOnHandPattern = Pattern.compile("^[0-9]{1,3}$");

        map.put(txtPPId,PPIdPattern);
        map.put(txtName,namePattern);
        map.put(txtType,typePattern);
        map.put(txtPrice,pricePattern);
        map.put(txtQTYOnHand,QTYOnHandPattern);

    }

    public void clearAllTexts(){
        txtPPId.clear();
        txtName.clear();
        txtType.clear();
        txtPrice.clear();
        txtQTYOnHand.clear();
    }



}
