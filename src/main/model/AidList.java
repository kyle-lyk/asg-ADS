package main.model;

import javafx.beans.property.SimpleStringProperty;

public class AidList {
    private SimpleStringProperty donor, phone, aids, ngo;
    private int quantity, manpower;
    private String filterItem;

    private int rowNum;


    public String getFilterItem() {
        return this.filterItem;
    }

    public void setFilterItem(String filterItem) {
        this.filterItem = filterItem;
    }

    // constructor for Matched Aids table content
    public AidList(String donor, String phone, String aids, Integer quantity, String ngo, Integer manpower) {
        this.donor = new SimpleStringProperty(donor);
        this.phone = new SimpleStringProperty(phone);
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
        this.ngo = new SimpleStringProperty(ngo);
        this.manpower = manpower;
    }

    // constructor for NGO table content
    public AidList(Integer rowNum, String ngo, Integer manpower, String aids, Integer quantity) {
        this.rowNum = rowNum;
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
        this.ngo = new SimpleStringProperty(ngo);
        this.manpower = manpower;
    }

    // constructor for Donor table content
    public AidList(Integer rowNum, String donor, String phone, String aids, Integer quantity) {
        this.rowNum = rowNum;
        this.donor = new SimpleStringProperty(donor);
        this.phone = new SimpleStringProperty(phone);
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
    }

    public String getDonor() {
        return this.donor.get();
    }

    public void setDonor(SimpleStringProperty donor) {
        this.donor = donor;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getPhone() {
        return this.phone.get();
    }

    public void setPhone(SimpleStringProperty phone) {
        this.phone = phone;
    }

    public String getAids() {
        return this.aids.get();
    }

    public void setAids(SimpleStringProperty aids) {
        this.aids = aids;
    }

    public String getNgo() {
        return this.ngo.get();
    }

    public void setNgo(SimpleStringProperty ngo) {
        this.ngo = ngo;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getManpower() {
        return this.manpower;
    }

    public void setManpower(Integer manpower) {
        this.manpower = manpower;
    }


}
