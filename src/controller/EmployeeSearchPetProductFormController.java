package controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PetProduct;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class EmployeeSearchPetProductFormController {
    public TextField txtSearch;
    public TableView tblPetProduct;
    public TableColumn colPPId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colPrice;
    public TableColumn colQtyOnHand;
    public AnchorPane EmployeeSearchPetProductFormContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();

        colPPId.setCellValueFactory(new PropertyValueFactory("PPId"));
        colName.setCellValueFactory(new PropertyValueFactory("Name"));
        colType.setCellValueFactory(new PropertyValueFactory("Type"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("QTYOnHand"));
        try {
            loadAllPetProduct();
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

    private void loadAllPetProduct() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.PetProduct ");

        ObservableList<PetProduct> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new PetProduct(
                            result.getString("PPId"),
                            result.getString("Name"),
                            result.getString("Type"),
                            result.getInt("price"),
                            result.getInt("QTYOnHand")
                    )
            );

        }
        tblPetProduct.setItems(obList);
        FilteredList<PetProduct> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(PetProduct -> {
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (PetProduct.getPPId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(PetProduct.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(PetProduct.getType().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }

            });
        });

        SortedList<PetProduct> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblPetProduct.comparatorProperty());
        tblPetProduct.setItems(sortedData);
    }

    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) EmployeeSearchPetProductFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManageCustomerForm.fxml"))));
        stage.centerOnScreen();
    }
}
