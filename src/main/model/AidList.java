package main.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * AidList Class is TableView formats for DistributePage and DCHistoryPage.
 * It contains information of aid list donated, requested by Donor and Ngo.
 */
public class AidList {
    private SimpleStringProperty donor, phone, aids, ngo;
    private String status;
    private int quantity, manpower;

    private int rowNum;

    /**
     * Constructs the content for
     * the rows of MatchedAidHistory table.
     */
    public AidList(String donor, String phone, String aids, Integer quantity, String ngo, Integer manpower) {
        this.donor = new SimpleStringProperty(donor);
        this.phone = new SimpleStringProperty(phone);
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
        this.ngo = new SimpleStringProperty(ngo);
        this.manpower = manpower;
    }

    public AidList(String donor, String phone, String aids, Integer quantity, String ngo, Integer manpower, String status) {
        this.donor = new SimpleStringProperty(donor);
        this.phone = new SimpleStringProperty(phone);
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
        this.ngo = new SimpleStringProperty(ngo);
        this.manpower = manpower;
        this.status = status;
    }

    /**
     * Constructs the content for
     * the rows of NGO table.
     */
    public AidList(Integer rowNum, String ngo, Integer manpower, String aids, Integer quantity) {
        this.rowNum = rowNum;
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
        this.ngo = new SimpleStringProperty(ngo);
        this.manpower = manpower;
    }

    /**
     * Constructs the content for
     * the rows of Donor table.
     */
    public AidList(Integer rowNum, String donor, String phone, String aids, Integer quantity) {
        this.rowNum = rowNum;
        this.donor = new SimpleStringProperty(donor);
        this.phone = new SimpleStringProperty(phone);
        this.aids = new SimpleStringProperty(aids);
        this.quantity = quantity;
    }

    /**
     * Gets the donor name.
     * @return donor's name
     */
    public String getDonor() {
        return this.donor.get();
    }

    /**
     * Sets the name of the donor.
     * @param donor donor's name
     */
    public void setDonor(SimpleStringProperty donor) {
        this.donor = donor;
    }

    /**
     * Gets the current table row number.
     * @return table row number
     */
    public int getRowNum() {
        return this.rowNum;
    }

    /**
     * Sets the table row number.
     * @param rowNum row number
     */
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * Gets the phone number of donor.
     * @return phone number
     */
    public String getPhone() {
        return this.phone.get();
    }

    /**
     * Sets the phone number of donor.
     * @param phone phone number
     */
    public void setPhone(SimpleStringProperty phone) {
        this.phone = phone;
    }

    /**
     * Gets the name of aid.
     * @return aid name
     */
    public String getAids() {
        return this.aids.get();
    }

    /**
     * Sets the name of aid.
     * @param aids aid name
     */
    public void setAids(SimpleStringProperty aids) {
        this.aids = aids;
    }

    /**
     * Gets the NGO's name.
     * @return NGO name
     */
    public String getNgo() {
        return this.ngo.get();
    }

    /**
     * Sets the NGO's name.
     * @param ngo NGO's name
     */
    public void setNgo(SimpleStringProperty ngo) {
        this.ngo = ngo;
    }

    /**
     * Gets the quantity of aid.
     * @return aid quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the quatity of aid.
     * @param quantity aid quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the NGO's manpower amount.
     * @return NGO's manpower amount
     */
    public int getManpower() {
        return this.manpower;
    }

    /**
     * Sets the NGO's manpower amount.
     * @param manpower NGO's manpower amount
     */
    public void setManpower(Integer manpower) {
        this.manpower = manpower;
    }

    public String getStatus(){
        return this.status;
    }

    public void sestStatus(String status){
        this.status = status;
    }

}
