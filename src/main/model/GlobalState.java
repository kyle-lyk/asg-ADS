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
    String identity;


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

    public void setSession(String username, String identity){
        this.username = username;
        this.identity = identity;
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
                viewpath = "/main/view/DistributeePage.fxml";
                break;
        }
        return viewpath;
    }

    public String getUsername(){
        return this.username;
    }

    public String getIdentity(){
        return this.identity;
    }

    public String getName(){
        String Name = "";
        List<List<String>> Acc_Info = Database.readData(getDataFileName(identity));
        for(int i=0; i < Acc_Info.size(); i++){
            if(username.equals(Acc_Info.get(i).get(0))){
                Name = Acc_Info.get(i).get(2);
            }
        }
        return Name;
    }

    public String getManPower(){
        String ManPower = "";
        List<List<String>> Acc_Info = Database.readData(getDataFileName(identity));
        for(int i=0; i < Acc_Info.size(); i++){
            if(username.equals(Acc_Info.get(i).get(0))){
                ManPower = Acc_Info.get(i).get(3);
            }
        }
        return ManPower;
    }

    public String getPhoneNumber(){
        String PhoneNumber = "";
        List<List<String>> Acc_Info = Database.readData(getDataFileName(identity));
        for(int i=0; i < Acc_Info.size(); i++){
            if(username.equals(Acc_Info.get(i).get(0))){
                PhoneNumber = Acc_Info.get(i).get(3);
            }
        }
        return PhoneNumber;
    }

}
