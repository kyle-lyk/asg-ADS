package main.model;

/**
 * Contains the donate information of donor 
 * such as donor name, donor phone number, donated item, donated item quantity, 
 * remaining donated item quantity and ngo who received the item.
 */
public class RequestInfo {
     
    private String ngoName;
    private Integer manpower;
    private String aids;
    private Integer qty;
    private Integer remainQty;
    private String donorName;

    /**
     * Constructs the aids request information.
     * @param ngoName Name of the NGO
     * @param manpower Manpower of NGO
     * @param aids Aid needed
     * @param qty Quantity of aid needed
     * @param remainQty Remaning quantity of aid needed
     * @param donorName Name of the donor who donated the aid
     */
    public RequestInfo(String ngoName, Integer manpower, String aids, Integer qty, Integer remainQty, String donorName) {
        this.ngoName = ngoName;
        this.manpower = manpower;
        this.aids = aids;
        this.qty = qty;
        this.remainQty = remainQty;
        this.donorName = donorName;
    }

    /**
     * Get the name of the NGO.
     * @return name of the NGO
     */
    public String getNgoName() {
        return ngoName;
    }

    /**
     * Set the name of the NGO.
     * @param ngoName name of the NGO
     */
    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    /**
     * Get the manpower of the NGO.
     * @return manpower of the NGO
     */
    public Integer getManpower() {
        return manpower;
    }

    /**
     * Set the manpower of the NGO.
     * @param manpower manpower of the NGO
     */
    public void setManpower(Integer manpower) {
        this.manpower = manpower;
    }

    /**
     * Get the aid needed.
     * @return aid needed
     */
    public String getAids() {
        return aids;
    }

    /**
     * Set the aid needed.
     * @param aids aid needed
     */
    public void setAids(String aids) {
        this.aids = aids;
    }

    /**
     * Get the quantity of aid needed.
     * @return quantity of aid needed
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * Set the quantity of aid needed.
     * @param qty quantity of aid needed
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * Get the remaining quantity of aid needed.
     * @return remaining quantity of aid needed
     */
    public Integer getRemainQty() {
        return remainQty;
    }

    /**
     * Set the remaining quantity of aid needed.
     * @param remainQty remaining quantity of aid needed
     */
    public void setRemainQty(Integer remainQty) {
        this.remainQty = remainQty;
    }

    /**
     * Get the name of the donor who donated the aid.
     * @return name of the donor who donated the aid
     */
    public String getDonorName() {
        return donorName;
    }

    /**
     * Set the name of the donor who donated the aid.
     * @param donorName name of the donor who donated the aid
     */
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}
