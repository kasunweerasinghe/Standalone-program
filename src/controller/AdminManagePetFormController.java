package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import model.Employee;
import model.Pet;
import util.CrudUtil;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AdminManagePetFormController {
    public AnchorPane AdminManagePetFormContext;
    public TableView<Pet> tblPet;
    public TableColumn colPId;
    public TableColumn coltype;
    public TableColumn colBreeds;
    public TableColumn colPrice;
    public TableColumn colGender;
    public TableColumn colAge;
    public TableColumn colQtyOnHand;
    public TextField txtSearch;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();

        colPId.setCellValueFactory(new PropertyValueFactory("PId"));
        coltype.setCellValueFactory(new PropertyValueFactory("type"));
        colBreeds.setCellValueFactory(new PropertyValueFactory("breeds"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colGender.setCellValueFactory(new PropertyValueFactory("gender"));
        colAge.setCellValueFactory(new PropertyValueFactory("age"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("QTYOnHand"));


        try {
            loadAllPet();
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

    private void loadAllPet() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Pet");

        ObservableList<Pet> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Pet(
                            result.getString("PId"),
                            result.getString("type"),
                            result.getString("breeds"),
                            result.getInt("price"),
                            result.getString("gender"),
                            result.getString("age"),
                            result.getInt("QTYOnHand")
                    )
            );
        }
        tblPet.setItems(obList);

        FilteredList<Pet> filteredData = new FilteredList<>(obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Pet ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(Pet.getPId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Pet.getType().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Pet.getBreeds().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(Pet.getGender().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Pet.getAge().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Pet> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPet.comparatorProperty());
        tblPet.setItems(sortedData);


    }

    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManagePetFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void addPetOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminAddPetForm");
    }

    public void deletePetOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminDeletePetForm");
    }

    public void updatePetOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminUpdatePetForm");
    }

    private void setUi(String URI) throws IOException {
        Parent parent =FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.show();
    }
}
