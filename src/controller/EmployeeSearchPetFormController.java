package controller;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Pet;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class EmployeeSearchPetFormController {
    public TableView<Pet> tblPet;
    public TextField txtSearch;
    public TableColumn colPId;
    public TableColumn coltype;
    public TableColumn colBreeds;
    public TableColumn colPrice;
    public TableColumn colGender;
    public TableColumn colAge;
    public TableColumn colQtyOnHand;
    public AnchorPane EmployeeSearchPetFormContext;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbPet;

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    private void loadAllPet() throws SQLException, ClassNotFoundException {
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
        //create filtered list for search
        FilteredList<Pet> filteredData = new FilteredList<>(obList, b -> true);
        //set Listener to search bar
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Pet ->{//Pet(Model class)
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                //search using pet Id
                if(Pet.getPId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                    //search using pet type
                }else if(Pet.getType().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                    //search using pet breeds
                }else if(Pet.getBreeds().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                    //search using pet gender
                } else if(Pet.getGender().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                    //search using pet age
                }else if(Pet.getAge().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        //filtered data insert sorted list
        SortedList<Pet> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPet.comparatorProperty());
        tblPet.setItems(sortedData);
    }

    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) EmployeeSearchPetFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManageCustomerForm.fxml"))));
        stage.centerOnScreen();
    }
}
