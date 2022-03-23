package main.model;

public class Donor extends User{
        
    private String donorName;
    private String phonenum;

    public Donor(String username, String password, String identity, String donorName, String phonenum) {
        super(username, password, identity);
        this.donorName = donorName;
        this.phonenum = phonenum;
    }

    public String getName() {
        return donorName;
    }

    public void setName(String donorName) {
        this.donorName = donorName;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
