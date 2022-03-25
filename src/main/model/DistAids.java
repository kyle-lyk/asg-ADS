package main.model;

import java.util.ArrayList;

public class DistAids {
    
    private String donorNames; 
    private String donorPhones;
    private String Aids;
    private int quantity;
    private String ngoNames;
    private String ngoManpower;

    public DistAids(String donorNames, String donorPhones, String Aids, int quantity, String ngoNames, String ngoManpower) {
        this.donorNames = donorNames;
        this.donorPhones = donorPhones;
        this.Aids = Aids;
        this.quantity = quantity;
        this.ngoNames = ngoNames;
        this.ngoManpower = ngoManpower;
    }

    public String getDonorNames() {
        return donorNames;
    }

    public void setDonorNames(String donorNames) {
        this.donorNames = donorNames;
    }

    public String getDonorPhones() {
        return donorPhones;
    }

    public void setDonorPhones(String donorPhones) {
        this.donorPhones = donorPhones;
    }

    public String getAids() {
        return Aids;
    }

    public void setAids(String Aids) {
        this.Aids = Aids;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNgoNames() {
        return ngoNames;
    }

    public void setNgoNames(String ngoNames) {
        this.ngoNames = ngoNames;
    }

    public String getNgoManpower() {
        return ngoManpower;
    }

    public void setNgoManpower(String ngoManpower) {
        this.ngoManpower = ngoManpower;
    }

}
