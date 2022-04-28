package model;

public class Customer {
    private String CId;
    private String name;
    private String address;
    private String contact;
    private String nic;

    public Customer() {
    }

    public Customer(String CId, String name, String address, String contact, String nic) {
        this.CId = CId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CId='" + CId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
