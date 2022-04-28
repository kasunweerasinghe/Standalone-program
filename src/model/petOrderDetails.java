package model;

public class petOrderDetails {
    private String OId;
    private String PId;
    private int Pqty;
    private int PunitPrice;

    public petOrderDetails() {
    }

    public petOrderDetails(String OId, String PId, int pqty, int punitPrice) {
        this.OId = OId;
        this.PId = PId;
        Pqty = pqty;
        PunitPrice = punitPrice;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public int getPqty() {
        return Pqty;
    }

    public void setPqty(int pqty) {
        Pqty = pqty;
    }

    public int getPunitPrice() {
        return PunitPrice;
    }

    public void setPunitPrice(int punitPrice) {
        PunitPrice = punitPrice;
    }

    @Override
    public String toString() {
        return "petOrderDetails{" +
                "OId='" + OId + '\'' +
                ", PId='" + PId + '\'' +
                ", Pqty=" + Pqty +
                ", PunitPrice=" + PunitPrice +
                '}';
    }
}
