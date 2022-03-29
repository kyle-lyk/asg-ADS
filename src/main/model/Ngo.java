package main.model;

/**
 * Ngo class contains Ngo user information.
 * It contains username, password, identity, Ngo Name, Manpower
 * where it inherits username, password and identity from User class.
 */
public class Ngo extends User{
        
    private String ngoName;
    private String manpower;

    /**
     * Constructs a Ngo with the username, password, identity, Ngo Name, and Manpower.
     * @param username Ngo username
     * @param password Ngo password
     * @param identity Ngo identity
     * @param ngoName Ngo name
     * @param manpower Ngo manpower
     */
    public Ngo(String username, String password, String identity, String ngoName, String manpower) {
        super(username, password, identity);
        this.ngoName = ngoName;
        this.manpower = manpower;
    }

    /**
     * Get Ngo's name.
     * @return Ngo name
     */
    public String getName() {
        return ngoName;
    }

    /**
     * Set Ngo's name.
     * @param ngoName Ngo name
     */
    public void setName(String ngoName) {
        this.ngoName = ngoName;
    }

    /**
     * Get Ngo's manpower.
     * @return Ngo manpower
     */
    public String getManpower() {
        return manpower;
    }

    /**
     * Set Ngo's manpower.
     * @param manpower Ngo manpower
     */
    public void setManpower(String manpower) {
        this.manpower = manpower;
    }

}
