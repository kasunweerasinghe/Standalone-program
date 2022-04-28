package view.tm;

import javafx.scene.control.Button;

import java.awt.*;

public class ProductCartTm {
    private String productCId;
    private String PPId;
    private int productUnitPrice;
    private int productQty;
    private int productTotalCost;
    private String productDId;
    private Button btn;

    public ProductCartTm() {
    }

    public ProductCartTm(String productCId, String PPId, int productUnitPrice, int productQty, int productTotalCost, String productDId, Button btn) {
        this.productCId = productCId;
        this.PPId = PPId;
        this.productUnitPrice = productUnitPrice;
        this.productQty = productQty;
        this.productTotalCost = productTotalCost;
        this.productDId = productDId;
        this.btn = btn;
    }

    public String getProductCId() {
        return productCId;
    }

    public void setProductCId(String productCId) {
        this.productCId = productCId;
    }

    public String getPPId() {
        return PPId;
    }

    public void setPPId(String PPId) {
        this.PPId = PPId;
    }

    public int getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(int productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getProductTotalCost() {
        return productTotalCost;
    }

    public void setProductTotalCost(int productTotalCost) {
        this.productTotalCost = productTotalCost;
    }

    public String getProductDId() {
        return productDId;
    }

    public void setProductDId(String productDId) {
        this.productDId = productDId;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ProductCartTm{" +
                "productCId='" + productCId + '\'' +
                ", PPId='" + PPId + '\'' +
                ", productUnitPrice=" + productUnitPrice +
                ", productQty=" + productQty +
                ", productTotalCost=" + productTotalCost +
                ", productDId='" + productDId + '\'' +
                ", btn=" + btn +
                '}';
    }
}
