package main.model;

/**
 * Contains the donate information of donor 
 * such as donor name, donor phone number, donated item, donated item quantity, 
 * remaining donated item quantity and ngo who received the item.
 */
public class DonateInfo{
    
    private String donorName;
    private String phoneNum;
    private String donatedItem;
    private Integer donatedItemQty;
    private Integer remainQty;
    private String NGOReceived;

    /**
     * Constructs the aids donate information.
     * @param donorName Name of the donor
     * @param phoneNum Phone number of the donor
     * @param donatedItem Item donated by the donor
     * @param donatedItemQty Quantity of item donated by the donor
     * @param remainQty Remaning quantity of item donated by the donor
     * @param NGOReceived   The NGO who received the item
     */
    public DonateInfo(String donorName, String phoneNum, String donatedItem, Integer donatedItemQty, Integer remainQty, String NGOReceived) {
        this.donorName = donorName;
        this.phoneNum = phoneNum;
        this.donatedItem = donatedItem;
        this.donatedItemQty = donatedItemQty;
        this.remainQty = remainQty;
        this.NGOReceived = NGOReceived;
    }

    /**
     * Get donor's name.
     * @return donor name
     */
    public String getDonorName(){
        return donorName;
    }

    /**
     * Set donor's name.
     * @param donorName donor name
     */
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    /**
     * Get donor's phone number.
     * @return phone number of the donor
     */
    public String getPhoneNum(){
        return phoneNum;
    }

    /**
     * Set donor's phone number.
     * @param phoneNum phone number of the donor
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Get donor's donated item.
     * @return item donated by donor
     */
    public String getDonatedItem(){
        return donatedItem;
    }

    /**
     * Set donor's donated item.
     * @param donatedItem item donated by donor
     */
    public void setDonatedItem(String donatedItem){
        this.donatedItem = donatedItem;
    }

    /**
     * Get donor's donated item quantity.
     * @return donated item quantity
     */
    public Integer getDonatedItemQty(){
        return donatedItemQty;
    }

    /**
     * Set donor's donated item quantity.
     * @param donatedItemQty donated item quantity
     */
    public void setDonatedItemQty(Integer donatedItemQty){
        this.donatedItemQty = donatedItemQty;
    }

    /**
     * Get remaining donated item quantity.
     * @return remaining donated item quantity
     */
    public Integer getRemainQty(){
        return remainQty;
    }

    /**
     * Set remaining donated item quantity.
     * @param remainQty remaining donated item quantity
     */
    public void setRemainQty(Integer remainQty){
        this.remainQty = remainQty;
    }

    /**
     * Get NGO that received the donated item.
     * @return NGO that received the donated item
     */
    public String getNGOReceived() {
        return NGOReceived;
    }

    /**
     * Set NGO that received the donated item.
     * @param NGOReceived NGO that received the donated item
     */
    public void setNGOReceived(String NGOReceived){
        this.NGOReceived = NGOReceived;
    }
}
