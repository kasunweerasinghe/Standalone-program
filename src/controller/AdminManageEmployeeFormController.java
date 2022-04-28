package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminManageEmployeeFormController {
    public AnchorPane AdminManageEmployeeFormContext;
    public TableView<Employee> tblEmployees;
    public TableColumn colEId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TextField txtSearchEmployee;
    public Label lblDate;
    public Label lblTime;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();


    public void initialize(){
        loadDateAndTime();

        colEId.setCellValueFactory(new PropertyValueFactory("EId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colUserName.setCellValueFactory(new PropertyValueFactory("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));


        try {
            loadAllEmployee();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+":"+
                    currentTime.getMinute()+":"+
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void loadAllEmployee() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Employee");
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Employee(
                            result.getString("EId"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("contact"),
                            result.getString("userName"),
                            result.getString("password")

                    )
            );
        }
        tblEmployees.setItems(obList);

        FilteredList<Employee> filteredData = new FilteredList<>(obList, b -> true);
        txtSearchEmployee.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Employee ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Employee.getEId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if(Employee.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Employee.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if (Employee.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getUserName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getPassword().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                return false;
                }
            });
        } );

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEmployees.comparatorProperty());
        tblEmployees.setItems(sortedData);
    }


    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageEmployeeFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void addEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminAddEmployeeForm");
    }

    public void deleteEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminDeleteEmployeeForm");
    }

    public void updateEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminUpdateEmployeeForm");
    }

    private void setUi(String URI) throws IOException {
       Parent parent = FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
       Stage stage =new Stage();
       stage.setScene(new Scene(parent));
       stage.setTitle(URI);
       stage.show();
    }
}
