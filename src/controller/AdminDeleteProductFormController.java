package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.PetProduct;
import util.CrudUtil;

import java.sql.*;

public class AdminDeleteProductFormController {
    public JFXTextField txtPPId;
    public JFXTextField txtName;
    public JFXTextField txtType;
    public JFXTextField txtPrice;
    public JFXTextField txtQTYOnHand;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try{
            if(CrudUtil.execute("DELETE FROM PetShop.PetProduct WHERE PPId=?",txtPPId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (SQLException | ClassNotFoundException e){

        }

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.PetProduct WHERE PPId=?",txtPPId.getText());
            if(result.next()){
                txtName.setText(result.getString(2));
                txtType.setText(result.getString(3));
                txtPrice.setText(String.valueOf(result.getInt(4)));
                txtQTYOnHand.setText(String.valueOf(result.getInt(5)));
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }




}
