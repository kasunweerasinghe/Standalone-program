package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Employee;
import util.CrudUtil;

import javax.xml.crypto.Data;
import java.sql.*;

public class AdminDeleteEmployeeFormController {

    public JFXTextField txtEId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtUserName;
    public JFXTextField pwdPassword;


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try{
            if(CrudUtil.execute("DELETE FROM PetShop.Employee WHERE EId=?",txtEId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (SQLException | ClassNotFoundException e){

        }
    }



    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Employee WHERE EId=?",txtEId.getText());
            if(result.next()){
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtContact.setText(result.getString(4));
                txtUserName.setText(result.getString(5));
                pwdPassword.setText(result.getString(6));
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }





}
