package main.model;

/**
 * Donor class contains donor user information.
 * It contains username, password, identity, donor name, donor phone number
 * where it inherits username, password and identity from User class.
 */
public class Donor extends User{
        
    private String donorName;
    private String phonenum;

    /**
     * Constructs a donor with the username, password, identity, donor name, and donor phone nummber.
     * @param username donor username
     * @param password donor password
     * @param identity donor identity
     * @param donorName donor name
     * @param phonenum donor phone number
     */
    public Donor(String username, String password, String identity, String donorName, String phonenum) {
        super(username, password, identity);
        this.donorName = donorName;
        this.phonenum = phonenum;
    }

    /**
     * Get donor's name.
     * @return donor name
     */
    public String getName() {
        return donorName;
    }

    /**
     * Get donor's name.
     * @param donorName donor name
     */
    public void setName(String donorName) {
        this.donorName = donorName;
    }

    /**
     * Get donor's phone number.
     * @return donor phone number
     */
    public String getPhonenum() {
        return phonenum;
    }

    /**
     * Set donor's phone number.
     * @param phonenum donor phone number
     */
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
