package main.model;

public class Ngo extends User{
        
    private String ngoName;
    private String manpower;

    public Ngo(String username, String password, String identity, String ngoName, String manpower) {
        super(username, password, identity);
        this.ngoName = ngoName;
        this.manpower = manpower;
    }

    public String getName() {
        return ngoName;
    }

    public void setName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getManpower() {
        return manpower;
    }

    public void setManpower(String manpower) {
        this.manpower = manpower;
    }

    //requestAids
        
}
