package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.*;

public class AdminDeleteDriverFormController {
    public JFXTextField txtDId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAge;
    public JFXTextField txtStatus;


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try{
            if(CrudUtil.execute("DELETE FROM PetShop.Driver WHERE DId=?",txtDId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (SQLException | ClassNotFoundException e){

        }

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Driver WHERE DId=?",txtDId.getText());
            if(result.next()){
                txtName.setText(result.getString(2));
                txtContact.setText(result.getString(3));
                txtAge.setText(String.valueOf(result.getInt(4)));
                txtStatus.setText(result.getString(5));
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }




}
