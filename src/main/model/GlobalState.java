package main.model;

import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GlobalState {
    private Stage publicStage;

    String username;
    String password;
    String identity;
    String name;
    String info;

    Donor donorUserInfo = new Donor(null, null, null, null, null);
    Ngo ngoUserInfo = new Ngo(null, null, null, null, null);

    // Static Varible reference to the Singleton instance
    private static GlobalState instance = null;

    // Static method to create instance of Singleton class
    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public Stage getStage(){
        return this.publicStage;
    }

    public void saveStage(Stage s){
        this.publicStage = s;
    }

    private GlobalState() {
        Init();
    }

    public void Init(){
        this.username = "";
        this.identity = "";
    }

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

    public Donor getDonorSession(){
        return this.donorUserInfo;
    }

    public void setDonorSession(Donor donorUserInfo){
        this.donorUserInfo = donorUserInfo;
    }

    public Ngo getNgoSession(){
        return this.ngoUserInfo;
    }

    public void setNgoSession(Ngo ngoUserInfo){
        this.ngoUserInfo = ngoUserInfo;
    }

    public String getDataFileName(String identity){
        String filename = "";
        switch (identity) {
            case "Donor":
                filename = "donor_acc";
                break;
            case "NGO":
                filename = "ngo_acc";
                break;
            case "DC Admin":
                filename = "dc_admin_acc";
                break;
        }
        return filename;
    }

    public String getViewPath(String identity){
        String viewpath = "";
        switch (identity) {
            case "Donor":
                viewpath = "/main/view/DonatePage.fxml";
                break;
            case "NGO":
                viewpath = "/main/view/ReceivePage.fxml";
                break;
            case "DC Admin":
                viewpath = "/main/view/DistributePage.fxml";
                break;
        }
        return viewpath;
    }



}
