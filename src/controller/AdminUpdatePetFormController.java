package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Pet;
import util.CrudUtil;

import java.sql.*;

public class AdminUpdatePetFormController {
    public JFXTextField txtPId;
    public JFXTextField txtType;
    public JFXTextField txtBreeds;
    public JFXTextField txtPrice;
    public JFXTextField txtGender;
    public JFXTextField txtAge;
    public JFXTextField txtQTYOnHand;


    public void btnUpdateOnAction(ActionEvent actionEvent) {
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
            boolean isUpdated = CrudUtil.execute("UPDATE PetShop.Pet SET type=?,breeds=?,price=?,gender=?,age=?,QTYOnHand=? WHERE PId=?",p.getType(),p.getBreeds(),p.getPrice(),p.getGender(),p.getAge(),p.getQTYOnHand(),p.getPId());
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }


        }catch (SQLException | ClassNotFoundException e){

        }


    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Pet WHERE PId=?",txtPId.getText());
            if(result.next()){
                txtType.setText(result.getString(2));
                txtBreeds.setText(result.getString(3));
                txtPrice.setText(String.valueOf(result.getInt(4)));
                txtGender.setText(result.getString(5));
                txtAge.setText(result.getString(6));
                txtQTYOnHand.setText(String.valueOf(result.getInt(7)));

            }else{
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


}
