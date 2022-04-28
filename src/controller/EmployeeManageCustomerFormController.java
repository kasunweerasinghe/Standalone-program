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
import model.Customer;
import model.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class EmployeeManageCustomerFormController {
    public TableView<Customer> tblCustomer;
    public TableColumn colCId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNIc;
    public TextField txtSearch;
    public AnchorPane EmployeeManageCustomerContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();

        colCId.setCellValueFactory(new PropertyValueFactory("CId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colNIc.setCellValueFactory(new PropertyValueFactory("nic"));

        try {
            loadAllCustomer();
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

    private void loadAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Customer");

        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Customer(
                            result.getString("CId"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("contact"),
                            result.getString("nic")

                    )
            );
        }
        tblCustomer.setItems(obList);

        FilteredList<Customer> filteredData = new FilteredList<>(obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Customer ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Customer.getCId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }else if(Customer.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Customer.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Customer.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Customer.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedData);
    }

    private void setUi(String URI) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.show();
    }

    public void btnAddCustomer(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeAddCustomerForm");
    }

    public void btnDeleteCustomer(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeDeleteCustomerForm");
    }

    public void btnUpdateCustomer(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeUpdateCustomerForm");
    }



    private void setUI(String location) throws IOException {
        Stage stage =(Stage) EmployeeManageCustomerContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
        stage.show();

    }

    public void btnGoPetFormOnAction(ActionEvent actionEvent) throws IOException {
        setUI("EmployeeSearchPetForm");
    }

    public void btnGoPetProductOnAction(ActionEvent actionEvent) throws IOException {
        setUI("EmployeeSearchPetProductForm");
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MainLoginForm");

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmployeePlaceOrderForm");
    }
}
