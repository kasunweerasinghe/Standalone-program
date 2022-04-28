package controller;

import com.sun.webkit.dom.CSSRuleImpl;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainLoginFormController {
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public AnchorPane MainLogInFormContext;

    public void SignInOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        ResultSet QueryResult = CrudUtil.execute("SELECT * FROM PetShop.Employee WHERE userName=? AND password=?",txtUserName.getText(),pwdPassword.getText());
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Admin WHERE userName=? AND password=?",txtUserName.getText(),pwdPassword.getText());

        if(QueryResult.next()){
            Stage stage=(Stage) MainLogInFormContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManageCustomerForm.fxml"))));
            stage.show();
        }else if(result.next()) {
                Stage stage =(Stage) MainLogInFormContext.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
                stage.show();

        }else {
            new Alert(Alert.AlertType.WARNING, "Incorrect! Try Again").show();
        }
    }

}



    //validation Employeee Other way
   /* private void validationForEmployee() {
        String verifyLogin = "SELECT count(1) FROM PetShop.Employee WHERE userName = '"+ txtUserName.getText()+ "' " +
                "AND password ='" +pwdPassword.getText()+ "'";

        try {
            ResultSet QueryResult = CrudUtil.execute(verifyLogin);

            while (QueryResult.next()){
                if(QueryResult.getInt(1) == 1){
                    //new Alert(Alert.AlertType.CONFIRMATION, "Welcome").show();
                    Stage stage=(Stage) MainLogInFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManageCustomerForm.fxml"))));
                    stage.show();
                    return;
                }else{
                    new Alert(Alert.AlertType.ERROR, "Error").show();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }*/
    //validation Admin Other way
   /* public void SignInOnAction(ActionEvent actionEvent){
        String verifyLogin= "SELECT count(1) FROM PetShop.Admin WHERE userName= '"+ txtUserName.getText()+ "' AND password= '" + pwdPassword.getText()+ "'";

        try {
            ResultSet QueryResult = CrudUtil.execute(verifyLogin);
            while (QueryResult.next()){
                if(QueryResult.getInt(1) == 1){
                    Stage stage =(Stage) AdminLogInFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
                    stage.show();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }*/


