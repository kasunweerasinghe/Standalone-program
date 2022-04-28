package model;

public class ProductOrderDetails {
    private String OId;
    private String PPId;
    private int PPqty;
    private int PPunitPrice;

    public ProductOrderDetails() {
    }

    public ProductOrderDetails(String OId, String PPId, int PPqty, int PPunitPrice) {
        this.OId = OId;
        this.PPId = PPId;
        this.PPqty = PPqty;
        this.PPunitPrice = PPunitPrice;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getPPId() {
        return PPId;
    }

    public void setPPId(String PPId) {
        this.PPId = PPId;
    }

    public int getPPqty() {
        return PPqty;
    }

    public void setPPqty(int PPqty) {
        this.PPqty = PPqty;
    }

    public int getPPunitPrice() {
        return PPunitPrice;
    }

    public void setPPunitPrice(int PPunitPrice) {
        this.PPunitPrice = PPunitPrice;
    }

    @Override
    public String toString() {
        return "ProductOrderDetails{" +
                "OId='" + OId + '\'' +
                ", PPId='" + PPId + '\'' +
                ", PPqty=" + PPqty +
                ", PPunitPrice=" + PPunitPrice +
                '}';
    }
}
