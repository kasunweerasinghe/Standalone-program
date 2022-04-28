package view.tm;

import javafx.scene.control.Button;

import java.awt.*;

public class PetCartTm {
    private String petCId;
    private String PId;
    private int petUnitPrice;
    private int petQty;
    private int petTotalCost;
    private String petDId;
    private Button btn;

    public PetCartTm() {
    }

    public PetCartTm(String petCId, String PId, int petUnitPrice, int petQty, int petTotalCost, String petDId, Button btn) {
        this.petCId = petCId;
        this.PId = PId;
        this.petUnitPrice = petUnitPrice;
        this.petQty = petQty;
        this.petTotalCost = petTotalCost;
        this.petDId = petDId;
        this.btn = btn;
    }

    public String getPetCId() {
        return petCId;
    }

    public void setPetCId(String petCId) {
        this.petCId = petCId;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public int getPetUnitPrice() {
        return petUnitPrice;
    }

    public void setPetUnitPrice(int petUnitPrice) {
        this.petUnitPrice = petUnitPrice;
    }

    public int getPetQty() {
        return petQty;
    }

    public void setPetQty(int petQty) {
        this.petQty = petQty;
    }

    public int getPetTotalCost() {
        return petTotalCost;
    }

    public void setPetTotalCost(int petTotalCost) {
        this.petTotalCost = petTotalCost;
    }

    public String getPetDId() {
        return petDId;
    }

    public void setPetDId(String petDId) {
        this.petDId = petDId;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "PetCartTm{" +
                "petCId='" + petCId + '\'' +
                ", PId='" + PId + '\'' +
                ", petUnitPrice=" + petUnitPrice +
                ", petQty=" + petQty +
                ", petTotalCost=" + petTotalCost +
                ", petDId='" + petDId + '\'' +
                ", btn=" + btn +
                '}';
    }
}
