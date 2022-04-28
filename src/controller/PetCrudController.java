package controller;

import model.Pet;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetCrudController {
    public static ArrayList<String> getPetIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT PetShop.Pet.PId FROM PetShop.Pet");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Pet getPet(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PetShop.Pet WHERE PId=?", id);
        if(result.next()){
            return new Pet(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7)

            );
        }
        return null;

    }
}
