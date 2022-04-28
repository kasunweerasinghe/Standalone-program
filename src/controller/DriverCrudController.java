package controller;

import javafx.beans.Observable;
import model.Driver;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverCrudController {
    public static ArrayList<String> getDriverIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT PetShop.Driver.DId FROM PetShop.Driver");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Driver getDriver(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Driver WHERE DId=?", id);
        if(result.next()){
            return new Driver(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5)

            );
        }
        return null;
    }
}
