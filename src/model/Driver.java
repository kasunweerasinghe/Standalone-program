package model;

public class Driver {
    private String DId;
    private String name;
    private String contact;
    private int age;
    private String status;

    public Driver() {
    }

    public Driver(String DId, String name, String contact, int age, String status) {
        this.DId = DId;
        this.name = name;
        this.contact = contact;
        this.age = age;
        this.status = status;
    }

    public String getDId() {
        return DId;
    }

    public void setDId(String DId) {
        this.DId = DId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "DId='" + DId + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", age=" + age +
                ", status='" + status + '\'' +
                '}';
    }
}
