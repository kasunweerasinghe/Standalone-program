package model;

public class PetProduct {
    private String PPId;
    private String Name;
    private String Type;
    private int price;
    private int QTYOnHand;

    public PetProduct() {
    }

    public PetProduct(String PPId, String name, String type, int price, int QTYOnHand) {
        this.PPId = PPId;
        Name = name;
        Type = type;
        this.price = price;
        this.QTYOnHand = QTYOnHand;
    }

    public String getPPId() {
        return PPId;
    }

    public void setPPId(String PPId) {
        this.PPId = PPId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQTYOnHand() {
        return QTYOnHand;
    }

    public void setQTYOnHand(int QTYOnHand) {
        this.QTYOnHand = QTYOnHand;
    }

    @Override
    public String toString() {
        return "PetProduct{" +
                "PPId='" + PPId + '\'' +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", price=" + price +
                ", QTYOnHand=" + QTYOnHand +
                '}';
    }
}
