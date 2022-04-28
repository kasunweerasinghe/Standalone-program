package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
import model.Pet;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;



public class AdminDashboardFormController {
    public AnchorPane AdminDashboardFormContext;
    public Label lblDate;
    public Label lblTime;
    public Text lblTotalPets;
    public Text lblToTalPetProducts;
    public Text lblTotalEmployees;
    public Text lblTotalDrivers;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        loadTotalValues();
    }

    private void loadTotalValues() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT SUM(qtyonhand) FROM PetShop.Pet");
        if(result.next()){
            String foundType = result.getString(1);
            lblTotalPets.setText(foundType);
        }

        ResultSet result1 = CrudUtil.execute("SELECT SUM(qtyonhand) FROM PetShop.PetProduct");
        if(result1.next()){
            String foundType1 = result1.getString(1);
            lblToTalPetProducts.setText(String.valueOf(foundType1));
        }

        ResultSet result2 = CrudUtil.execute("SELECT COUNT(*) FROM PetShop.Employee");
        if(result2.next()){
            String foundType2 = result2.getString(1);
            lblTotalEmployees.setText(String.valueOf(foundType2));
        }

        ResultSet result3 = CrudUtil.execute("SELECT COUNT(*) FROM PetShop.Driver");
        if(result3.next()){
            String foundType3 = result3.getString(1);
            lblTotalDrivers.setText(String.valueOf(foundType3));
        }

    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO,e->{
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

    public void goEmployeeOnAction(ActionEvent actionEvent) throws IOException {
       setUi("AdminManageEmployeeForm");
    }

    public void goPetOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminManagePetForm");
    }

    public void goPetProductOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminManagePetProductForm");
    }

    public void goDriverOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AdminManageDriverForm");
    }

    public void SignOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MainLoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) AdminDashboardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
        stage.show();

    }

    public void btnCustomerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileCustomerReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/CustomerSQLReport.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileCustomerReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);


        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnPetOrdersReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compilePetOrdersReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/PetOrdersReport.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compilePetOrdersReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnProductOrdersReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileProductOrdersReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/ProductOrderReport.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileProductOrdersReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);


        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
