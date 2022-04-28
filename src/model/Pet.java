package model;

public class Pet {
    private String PId;
    private String type;
    private String breeds;
    private int price;
    private String gender;
    private String age;
    private int QTYOnHand;

    public Pet() {
    }

    public Pet(String PId, String type, String breeds, int price, String gender, String age, int QTYOnHand) {
        this.PId = PId;
        this.type = type;
        this.breeds = breeds;
        this.price = price;
        this.gender = gender;
        this.age = age;
        this.QTYOnHand = QTYOnHand;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreeds() {
        return breeds;
    }

    public void setBreeds(String breeds) {
        this.breeds = breeds;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getQTYOnHand() {
        return QTYOnHand;
    }

    public void setQTYOnHand(int QTYOnHand) {
        this.QTYOnHand = QTYOnHand;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "PId='" + PId + '\'' +
                ", type='" + type + '\'' +
                ", breeds='" + breeds + '\'' +
                ", price=" + price +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", QTYOnHand=" + QTYOnHand +
                '}';
    }
}
