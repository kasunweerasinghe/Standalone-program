package controller;

import model.PetProduct;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductCrudController {
    public static ArrayList<String> getProductIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT PetShop.PetProduct.PPId FROM PetShop.PetProduct ");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static PetProduct getProduct(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.PetProduct WHERE PPId=?", id);
        if(result.next()){
            return new PetProduct(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getInt(5)

            );
        }
        return null;

    }

}
