package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Employee;
import util.CrudUtil;

import java.sql.*;

public class AdminUpdateEmployeeFormController {
    public JFXTextField txtEId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtUserName;
    public JFXTextField pwdPassword;


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Employee E = new Employee(
                txtEId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(),
                txtUserName.getText(), pwdPassword.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE PetShop.Employee SET name=?,address=?,contact=?,userName=?,password=? WHERE EId=?",E.getName(),E.getAddress(),E.getContact(),E.getUserName(),E.getPassword(),E.getEId());
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
