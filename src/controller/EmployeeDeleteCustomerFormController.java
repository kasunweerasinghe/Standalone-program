package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDeleteCustomerFormController {
    public JFXTextField txtCId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtNic;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try{
            if(CrudUtil.execute("DELETE FROM PetShop.Customer WHERE CId=?",txtCId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try{
            ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Customer WHERE CId=?",txtCId.getText());
            if(result.next()){
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtContact.setText(result.getString(4));
                txtNic.setText(result.getString(5));
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
