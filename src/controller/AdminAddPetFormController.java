package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Pet;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminAddPetFormController {
    public JFXTextField txtPId;
    public JFXTextField txtType;
    public JFXTextField txtBreeds;
    public JFXTextField txtPrice;
    public JFXTextField txtGender;
    public JFXTextField txtAge;
    public JFXTextField txtQTYOnHand;
    public JFXButton btnSavePet;
    LinkedHashMap<JFXTextField,Pattern> map = new LinkedHashMap<>();

    public void btnSaveOnAction(ActionEvent actionEvent) {
        savePet();
    }

    private void savePet() {

        Pet p = new Pet(
                txtPId.getText(),
                txtType.getText(),
                txtBreeds.getText(),
                Integer.parseInt(txtPrice.getText()),
                txtGender.getText(),
                txtAge.getText(),
                Integer.parseInt(txtQTYOnHand.getText())
        );

        try {
            if(CrudUtil.execute("INSERT INTO PetShop.Pet VALUES (?,?,?,?,?,?,?)",p.getPId(),p.getType(),p.getBreeds(),p.getPrice(),p.getGender(),p.getAge(),p.getQTYOnHand())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearAllTexts();
    }
    public void checkPattern(){
        Pattern PIdPattern = Pattern.compile("^(PId00-)[0-9]{3,5}$");
        Pattern typePattern = Pattern.compile("^(Cat|Dog)$");
        Pattern breedsPattern = Pattern.compile("^[A-z ]{3,12}$");
        Pattern pricePattern = Pattern.compile("^[0-9]{4,6}$");
        Pattern genderPattern = Pattern.compile("^(Male|Female)$");
        Pattern agePattern = Pattern.compile("^[1-9](M|Y)$");
        Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,3}$");

        map.put(txtPId,PIdPattern);
        map.put(txtType,typePattern);
        map.put(txtBreeds,breedsPattern);
        map.put(txtPrice,pricePattern);
        map.put(txtGender,genderPattern);
        map.put(txtAge,agePattern);
        map.put(txtQTYOnHand,qtyOnHandPattern);

    }

    public void textFields_key_Released(KeyEvent keyEvent) {
        checkPattern();
        ValidationUtil.validate(map,btnSavePet);

        if(keyEvent.getCode()== KeyCode.ENTER){
            Object response = ValidationUtil.validate(map,btnSavePet);

            if(response instanceof JFXTextField){
                JFXTextField jfxTextField = (JFXTextField)response;
                jfxTextField.requestFocus();
            }else if(response instanceof Boolean){
                savePet();
            }
        }
    }



    public void clearAllTexts(){
        txtPId.clear();
        txtType.clear();
        txtBreeds.clear();
        txtPrice.clear();
        txtGender.clear();
        txtAge.clear();
        txtQTYOnHand.requestFocus();

    }



}
