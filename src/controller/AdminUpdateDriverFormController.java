package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Driver;
import util.CrudUtil;

import java.sql.*;

public class AdminUpdateDriverFormController {
    public JFXTextField txtDId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAge;
    public JFXTextField txtStatus;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        model.Driver d = new Driver(
                txtDId.getText(),
                txtName.getText(),
                txtContact.getText(),
                Integer.parseInt(txtAge.getText()),
                txtStatus.getText()
        );

        try{
            boolean isUpdated = CrudUtil.execute("UPDATE PetShop.Driver SET name=?,contact=?,age=?,status=? WHERE DId=? ",d.getName(),d.getContact(),d.getAge(),d.getStatus(),d.getDId());
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
