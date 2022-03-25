package main.model;


public class RequestInfo {
    
    String ngoName;
    Integer manpower;
    String aids;
    Integer qty;
    Integer remainQty;
    String donorName;

    public RequestInfo(String ngoName, Integer manpower, String aids, Integer qty, Integer remainQty, String donorName) {
        this.ngoName = ngoName;
        this.manpower = manpower;
        this.aids = aids;
        this.qty = qty;
        this.remainQty = remainQty;
        this.donorName = donorName;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public Integer getManpower() {
        return manpower;
    }

    public void setManpower(Integer manpower) {
        this.manpower = manpower;
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(Integer remainQty) {
        this.remainQty = remainQty;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    

}
