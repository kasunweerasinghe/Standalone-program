package model;

public class Delivery {
    private String DeId;
    private String date;
    private String time;
    private String DId;
    private String OId;
    private String address;

    public Delivery() {
    }

    public Delivery(String deId, String date, String time, String DId, String OId, String address) {
        DeId = deId;
        this.date = date;
        this.time = time;
        this.DId = DId;
        this.OId = OId;
        this.address = address;
    }

    public String getDeId() {
        return DeId;
    }

    public void setDeId(String deId) {
        DeId = deId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDId() {
        return DId;
    }

    public void setDId(String DId) {
        this.DId = DId;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "DeId='" + DeId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", DId='" + DId + '\'' +
                ", OId='" + OId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
