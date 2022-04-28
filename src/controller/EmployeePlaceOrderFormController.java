package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sun.util.locale.provider.JRELocaleConstants;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.PetCartTm;
import view.tm.ProductCartTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class EmployeePlaceOrderFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane PlaceOrderFormContext;

    public ComboBox<String> cmbCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;

    public ComboBox<String> cmbPetId;
    public TextField txtPetType;
    public TextField txtPetBreeds;
    public TextField txtPetQTYOnHand;
    public TextField txtPetPrice;
    public TextField txtPetQTY;

    public ComboBox<String> cmbProductId;
    public TextField txtProductType;
    public TextField txtProductName;
    public TextField txtProductQTYOnHand;
    public TextField txtProductPrice;
    public TextField txtProductQTY;
    public ComboBox<String> cmbDriverId;
    public Label lblTotalCost;
    
    public TableView<PetCartTm> tblPetCart;
    public TableColumn colPetCId;
    public TableColumn colPetId;
    public TableColumn colPetUnitPrice;
    public TableColumn colPetQty;
    public TableColumn colPetTotalCost;
    public TableColumn colPetDId;
    public TableColumn colPetButton;

    public TableView<ProductCartTm> tblProductCart;
    public TableColumn colProductCId;
    public TableColumn colProductId;
    public TableColumn colProductUnitPrice;
    public TableColumn colProductQty;
    public TableColumn colProductTotalCost;
    public TableColumn ProductProductDId;
    public TableColumn colProductButton;
    public TextField txtDriverName;


    public void initialize() throws SQLException, ClassNotFoundException {
        colPetCId.setCellValueFactory(new PropertyValueFactory("petCId"));
        colPetId.setCellValueFactory(new PropertyValueFactory("PId"));
        colPetUnitPrice.setCellValueFactory(new PropertyValueFactory("petUnitPrice"));
        colPetQty.setCellValueFactory(new PropertyValueFactory("petQty"));
        colPetTotalCost.setCellValueFactory(new PropertyValueFactory("petTotalCost"));
        colPetDId.setCellValueFactory(new PropertyValueFactory("petDId"));
        colPetButton.setCellValueFactory(new PropertyValueFactory("btn"));

        colProductCId.setCellValueFactory(new PropertyValueFactory("productCId"));
        colProductId.setCellValueFactory(new PropertyValueFactory("PPId"));
        colProductUnitPrice.setCellValueFactory(new PropertyValueFactory("productUnitPrice"));
        colProductQty.setCellValueFactory(new PropertyValueFactory("productQty"));
        colProductTotalCost.setCellValueFactory(new PropertyValueFactory("productTotalCost"));
        ProductProductDId.setCellValueFactory(new PropertyValueFactory("productDId"));
        colProductButton.setCellValueFactory(new PropertyValueFactory("btn"));
        
        loadDateAndTime();
        setCustomerIds();
        setPetIds();
        setProductIds();
        setDriverIds();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setCustomerDetails(newValue);
        });

        cmbPetId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setPetDetails(newValue);
        });

        cmbProductId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setProductDetails(newValue);
        });

        cmbDriverId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            setDriverDetails(newValue);
        });

    }

    public void setDriverIds() throws SQLException, ClassNotFoundException {
        try {
           /* ObservableList<String> obList = FXCollections.observableArrayList(DriverCrudController.getDriverIds());
            cmbDriverId.setItems(obList);*/
            cmbDriverId.setItems(FXCollections.observableArrayList(DriverCrudController.getDriverIds()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDriverDetails(String selectedDriverId){
        try {
            Driver d = DriverCrudController.getDriver(selectedDriverId);
            if(d!=null){
                txtDriverName.setText(d.getName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setProductDetails(String selectedProductId) {
        try {
            PetProduct pp = ProductCrudController.getProduct(selectedProductId);
            if (pp != null) {
                txtProductName.setText(pp.getName());
                txtProductType.setText(pp.getType());
                txtProductQTYOnHand.setText(String.valueOf(pp.getQTYOnHand()));
                txtProductPrice.setText(String.valueOf(pp.getPrice()));
            } else {
                //new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setProductIds() {
        try {
            cmbProductId.setItems(FXCollections.observableArrayList(ProductCrudController.getProductIds()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setPetDetails(String selectedPetId) {
        try {
            Pet p = PetCrudController.getPet(selectedPetId);
            if (p != null) {
                txtPetType.setText(p.getType());
                txtPetBreeds.setText(p.getBreeds());
                txtPetQTYOnHand.setText(String.valueOf(p.getQTYOnHand()));
                txtPetPrice.setText(String.valueOf(p.getPrice()));
            } else {
                //new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setPetIds() {
        try {
            cmbPetId.setItems(FXCollections.observableArrayList(PetCrudController.getPetIds()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtName.setText(c.getName());
                txtAddress.setText(c.getAddress());
                txtContact.setText(c.getContact());
            } else {
                //new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerIds() {

        try {
            ObservableList<String> CIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            cmbCustomerId.setItems(CIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    ObservableList<PetCartTm> petTmList = FXCollections.observableArrayList();
    ObservableList<ProductCartTm> ProductTmList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(cmbPetId.getValue()!=null){
            setPetData();
        }
        if(cmbProductId.getValue()!=null){
            setProductData();
        }
        tblPetCart.refresh();
        tblProductCart.refresh();
        calculateTotal();

    }

    public void setPetData(){

        PetCartTm isPetExists = isPetExists(cmbPetId.getValue());
        //P-Pet
        int PUnitPrice = Integer.parseInt(txtPetPrice.getText());
        int PQty = Integer.parseInt(txtPetQTY.getText());
        int PetTotalCost = (PUnitPrice * PQty);

        if (isPetExists != null) {
            for (PetCartTm temp : petTmList
            ) {
                if (temp.equals(isPetExists)) {
                    temp.setPetQty(temp.getPetQty() + PQty);
                    temp.setPetTotalCost(temp.getPetTotalCost() + PetTotalCost);
                }
            }
        }else{
            Button Pbtn = new Button("Delete");
            PetCartTm Ptm = new PetCartTm(
                    cmbCustomerId.getValue(),
                    cmbPetId.getValue(),
                    PUnitPrice,
                    PQty,
                    PetTotalCost,
                    cmbDriverId.getValue(),
                    Pbtn
            );
            Pbtn.setOnAction(e -> {
                petTmList.remove(Ptm);
                calculateTotal();
            });
            petTmList.add(Ptm);
            tblPetCart.setItems(petTmList);
        }

    }

    public void setProductData(){

        ProductCartTm isProductExists = isProductExists(cmbProductId.getValue());
        //PP-Pet Product
        int PPUnitPrice = Integer.parseInt(txtProductPrice.getText());
        int PPQty =Integer.parseInt(txtProductQTY.getText());
        int ProductTotalCost =(PPUnitPrice * PPQty);

        if (isProductExists != null) {
            for (ProductCartTm temp : ProductTmList
            ) {
                if (temp.equals(isProductExists)) {
                    temp.setProductQty(temp.getProductQty() + PPQty);
                    temp.setProductTotalCost(temp.getProductTotalCost() + ProductTotalCost);
                }
            }
        }else{
            Button PPbtn = new Button("Delete");
            ProductCartTm PPtm = new ProductCartTm(
                    cmbCustomerId.getValue(),
                    cmbProductId.getValue(),
                    PPUnitPrice,
                    PPQty,
                    ProductTotalCost,
                    cmbDriverId.getValue(),
                    PPbtn
            );
            PPbtn.setOnAction(e->{
                ProductTmList.remove(PPtm);
                tblProductCart.setItems(ProductTmList);
            });
            ProductTmList.add(PPtm);
            tblProductCart.setItems(ProductTmList);
        }

    }

    public void openNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeAddCustomerForm.fxml"))));
        stage.show();
    }

    private PetCartTm isPetExists(String petId) {
        for (PetCartTm tm : petTmList
        ) {
            if (tm.getPId().equals(petId)) {
                return tm;
            }
        }
        return null;
    }

    private ProductCartTm isProductExists(String ProductId) {
        for (ProductCartTm tm : ProductTmList
        ) {
            if (tm.getPPId().equals(ProductId)) {
                return tm;
            }
        }
        return null;
    }

    private void calculateTotal() {
        int total = 0;
        for (PetCartTm tm : petTmList
        ) {
            total += tm.getPetTotalCost();
        }
        for (ProductCartTm tm:ProductTmList
             ) {
            total +=tm.getProductTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }


    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order order = new Order(
                OrderCrudController.getOrderId(),
                lblDate.getText(),
                cmbCustomerId.getValue()

        );
        ArrayList<Delivery> deliveryDetails = new ArrayList<>();
        if(cmbDriverId.getValue()!=null){
            deliveryDetails.add(
                    new Delivery(
                            OrderCrudController.getDeliveryId(),
                            lblDate.getText(),
                            lblTime.getText(),
                            cmbDriverId.getValue(),
                            OrderCrudController.getOrderId(),
                            txtAddress.getText()

                    )
            );
        }

        ArrayList<petOrderDetails> petDetails = new ArrayList<>();
        for (PetCartTm Ptm:petTmList
             ) {
            petDetails.add(
                    new petOrderDetails(
                            OrderCrudController.getOrderId(),
                            Ptm.getPId(),
                            Ptm.getPetQty(),
                            Ptm.getPetUnitPrice()
                    )
            );
        }
        ArrayList<ProductOrderDetails> productDetails = new ArrayList<>();
        for (ProductCartTm PPtm:ProductTmList
             ) {
            productDetails.add(
                    new ProductOrderDetails(
                            OrderCrudController.getOrderId(),
                            PPtm.getPPId(),
                            PPtm.getProductQty(),
                            PPtm.getProductUnitPrice()
                    )
            );
        }

        Connection connection = null;

        try {
            connection=DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailSaved = new OrderCrudController().saveOrderDetails(petDetails,productDetails);
                if (isDetailSaved) {
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error..").show();
                }
                boolean isDeliverySaved = new OrderCrudController().SaveDeliveryDetails(deliveryDetails);
                if(isDeliverySaved){
                    connection.commit();
                    //
                }else{
                    connection.rollback();
                    //
                }
            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {

        }finally {
            connection.setAutoCommit(true);
        }

        if(cmbPetId.getValue()!=null){
            printPetReport();
        }
        if(cmbProductId.getValue()!=null){
            printProductReport();
        }

        ObservableList<String> obList = FXCollections.observableArrayList(DriverCrudController.getDriverIds());
        obList.remove(cmbDriverId.getValue());
        cmbDriverId.setItems(obList);
        clearAllTableData();
    }


    private void printProductReport() {
        try {
            JasperReport compileProductReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/ProductBeanArray.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileProductReport, null, new JRBeanArrayDataSource(tblProductCart.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void printPetReport() {
        try {
            JasperReport compilePetReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/PetBeanArrayReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compilePetReport, null, new JRBeanArrayDataSource(tblPetCart.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void clearAllTableData(){
        tblPetCart.getItems().clear();
        tblProductCart.getItems().clear();
        lblTotalCost.setText("");

    }

    public void removeItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        cmbCustomerId.setValue(null);
        cmbPetId.setValue(null);
        cmbProductId.setValue(null);
        cmbDriverId.setValue(null);

        txtName.clear();
        txtAddress.clear();
        txtContact.clear();

        txtPetBreeds.clear();
        txtPetPrice.clear();
        txtPetQTYOnHand.clear();
        txtPetQTY.clear();
        txtPetType.clear();

        txtProductPrice.clear();
        txtProductType.clear();
        txtProductQTYOnHand.clear();
        txtProductQTY.clear();
        txtProductName.clear();

        txtDriverName.clear();

    }

    public void textFields_key_Released(KeyEvent keyEvent) {

    }
}
