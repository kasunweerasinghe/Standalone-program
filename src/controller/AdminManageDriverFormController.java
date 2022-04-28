package controller;

import db.DBConnection;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Driver;
import model.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AdminManageDriverFormController {
    public AnchorPane AdminManageDriverFormContext;
    public TableView<Driver> tblDriver;
    public TableColumn colDId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAge;
    public TableColumn colStatus;
    public TextField txtSearch;
    public Label lblDate;
    public Label lblTime;


    public void initialize(){
        loadDateAndTime();

        colDId.setCellValueFactory(new PropertyValueFactory("DId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAge.setCellValueFactory(new PropertyValueFactory("age"));
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));



        try {
            loadAllDriver();
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

    private void loadAllDriver() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Driver ");

        ObservableList<model.Driver> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new model.Driver(
                            result.getString("DId"),
                            result.getString("name"),
                            result.getString("contact"),
                            result.getInt("age"),
                            result.getString("status")
                    )
            );
        }
        tblDriver.setItems(obList);

        FilteredList<Driver> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Driver ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Driver.getDId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(Driver.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(Driver.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Driver.getStatus().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Driver> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblDriver.comparatorProperty());
        tblDriver.setItems(sortedData);
    }


    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageDriverFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void addDriverOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminAddDriverForm");
    }

    public void deleteDriverOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminDeleteDriverForm");
    }

    public void updateDriverOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminUpdateDriverForm");
    }

    private void setUi(String URI) throws IOException {
       Parent parent = FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
       Stage stage = new Stage();
       stage.setScene(new Scene(parent));
       stage.setTitle(URI);
       stage.show();
    }
}
