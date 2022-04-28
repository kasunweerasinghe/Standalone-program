package controller;

import model.*;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO PetShop.Orders VALUES(?,?,?)",
                order.getOId(), order.getDate(), order.getCId());
    }

    public boolean saveOrderDetails(ArrayList<petOrderDetails> petDetails, ArrayList<ProductOrderDetails> productDetails) throws SQLException, ClassNotFoundException {
        for (petOrderDetails Pdet : petDetails
        ) {
            boolean isPetDetailSaved = CrudUtil.execute("INSERT INTO PetShop.PetOrderDetail VALUES(?,?,?,?)",
                    Pdet.getOId(), Pdet.getPId(), Pdet.getPqty(), Pdet.getPunitPrice());
            if (isPetDetailSaved) {
                if (!updatePetQty(Pdet.getPId(), Pdet.getPqty())) {
                    return false;
                }

            } else {
                return false;
            }
        }
        for (ProductOrderDetails PPdet:productDetails
        ) {
            boolean isProductDetailSaved = CrudUtil.execute("INSERT INTO PetShop.ProductOrderDetail VALUES(?,?,?,?)",
                    PPdet.getOId(),PPdet.getPPId(),PPdet.getPPqty(),PPdet.getPPunitPrice());
            if(isProductDetailSaved){
                if(!UpdateProductQty(PPdet.getPPId(), PPdet.getPPqty())){
                    return false;
                }
            }else{
                return false;
            }
        }

        return true;
    }

    public boolean SaveDeliveryDetails(ArrayList<Delivery> deliveryDetails) throws SQLException, ClassNotFoundException {
        for (Delivery del : deliveryDetails
        ) {
            CrudUtil.execute("INSERT INTO PetShop.Delivery VALUES(?,?,?,?,?,?)",
                    del.getDeId(), del.getDate(), del.getTime(), del.getDId(), del.getOId(), del.getAddress());
        }
        return true;
    }

    private boolean updatePetQty(String pId, int pqty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE PetShop.Pet SET qtyonhand=qtyonhand-? WHERE  PId=? ", pqty, pId);

    }

    private boolean UpdateProductQty(String ppId, int pPqty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE PetShop.PetProduct SET qtyonhand=qtyonhand-? WHERE  PPId=? ", pPqty, ppId);
    }


    public static String getOrderId() {
        try {
            ResultSet result = CrudUtil.execute("SELECT PetShop.Orders.OId FROM PetShop.Orders ORDER BY  OId DESC LIMIT 1");
            if (result.next()) {
                String id = result.getString(1);
                String[] split = id.split("OId");
                Integer num = Integer.valueOf(split[1]) + 1;
                String send = String.format("OId%03d", num);
                return send;
            } else {
                return "OId001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDeliveryId(){
        try {
            ResultSet result = CrudUtil.execute("SELECT PetShop.Delivery.DeId FROM PetShop.Delivery ORDER BY  DeId DESC LIMIT 1");
            if(result.next()){
                String id = result.getString(1);
                String[] split =id.split("DeId");
                Integer num = Integer.valueOf(split[1]) + 1;
                String send = String.format("DeId%03d", num);
                return send;
            }else{
                return "DeId001";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

