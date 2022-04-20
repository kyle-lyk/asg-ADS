package main.model;

import javafx.stage.Stage;

/**
 * GlobalState Class use Singleton Design Pattern to provide Global Access Point to only one instance.
 * It is use to store the Public Stage, User Information of current Login Session.
 * This allow different classes to access to the same Stage, User Information.
 */
public class GlobalState {

    ////// start of GlobalState Stage //////

    private Stage publicStage;

    // Static Varible reference to the Singleton instance
    private static GlobalState instance = null;

    /**
     * Static method to create instance of GlobalState.
     * @return instance of GlobalState
     */
    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    /**
     * Default Constructor of GlobalState.
     */
    private GlobalState() {
        Init();
    }

    /**
     * Get the Public Stage
     * @return Public Stage
     */
    public Stage getStage(){
        return this.publicStage;
    }

    /**
     * Set the Public Stage
     * @param stage Public Stage
     */
    public void saveStage(Stage s){
        this.publicStage = s;
    }

    /**
     * To initialize and to flush User Information from Previous Login Session.
     */
    public void Init(){
        this.username = "";
        this.identity = "";
    }
    ////// end of GlobalState Stage //////


    ////// start of Set login Session //////
    private String username;
    private String password;
    private String identity;
    private String name;
    private String info;

    private Donor donorUserInfo = new Donor(null, null, null, null, null);
    private Ngo ngoUserInfo = new Ngo(null, null, null, null, null);

    /**
     * Set the Current Login Session Identity
     * @param identity Identity of the User
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * Get the Current Login Session Identity
     */
    public String getIdentity() {
        return this.identity;
    }
        
    /**
     * Set the User Information of current Login Session.
     * @param username Username
     * @param password User Password
     * @param identity User Identity (Ngo/Donor)
     * @param name User's Name
     * @param info Manpower or Phone Number
     */
    public void setSession(String username, String password ,String identity, String name, String info){
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.name = name;
        this.info = info;

        if(identity == "NGO"){
            this.ngoUserInfo.setUsername(username);
            this.ngoUserInfo.setPassword(password);
            this.ngoUserInfo.setIdentity(identity);
            this.ngoUserInfo.setName(name);
            this.ngoUserInfo.setManpower(info);

        }else if(identity == "Donor"){
            this.donorUserInfo.setUsername(username);
            this.donorUserInfo.setPassword(password);
            this.donorUserInfo.setIdentity(identity);
            this.donorUserInfo.setName(name);
            this.donorUserInfo.setPhonenum(info);            
        }
        System.out.println(this.identity);
    }
    
    /**
     * Get the Donor User Information of current Login Session.
     * @return Donor User Information
     */
    public Donor getDonorSession(){
        return this.donorUserInfo;
    }

    /**
     * Set the Ngo User Information of current Login Session.
     * @param donorUserInfo Donor User Information
     */
    public void setDonorSession(Donor donorUserInfo){
        this.donorUserInfo = donorUserInfo;
    }

    /**
     * Get the Ngo User Information of current Login Session.
     * @return Ngo User Information
     */
    public Ngo getNgoSession(){
        return this.ngoUserInfo;
    }

    /**
     * Set the Ngo User Information of current Login Session.
     * @param ngoUserInfo Ngo User Information
     */
    public void setNgoSession(Ngo ngoUserInfo){
        this.ngoUserInfo = ngoUserInfo;
    }

    ////// end of Set login Session //////
}
