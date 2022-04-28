package model;

public class Order {
    private String OId;
    private String date;
    private String CId;

    public Order() {
    }

    public Order(String OId, String date, String CId) {
        this.OId = OId;
        this.date = date;
        this.CId = CId;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OId='" + OId + '\'' +
                ", date='" + date + '\'' +
                ", CId='" + CId + '\'' +
                '}';
    }
}
