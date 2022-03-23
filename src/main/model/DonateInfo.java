package main.model;

public class DonateInfo{
    
    private String donorName;
    private String phoneNum;
    private String donatedItem;
    private Integer donatedItemQty;
    private String NGOReceived;

    public DonateInfo(String donorName, String phoneNum, String donatedItem, Integer donatedItemQty, String NGOReceived) {
        this.donorName = donorName;
        this.phoneNum = phoneNum;
        this.donatedItem = donatedItem;
        this.donatedItemQty = donatedItemQty;
        this.NGOReceived = NGOReceived;
    }
    public String getDonorName(){
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    public String getPhoneNum(){
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDonatedItem(){
        return donatedItem;
    }

    public void setDonatedItem(String donatedItem){
        this.donatedItem = donatedItem;
    }

    public Integer getDonatedItemQty(){
        return donatedItemQty;
    }

    public void setDonatedItemQty(Integer donatedItemQty){
        this.donatedItemQty = donatedItemQty;
    }

    public String getNGOReceived() {
        return NGOReceived;
    }

    public void setNGOReceived(String NGOReceived){
        this.NGOReceived = NGOReceived;
    }
}

