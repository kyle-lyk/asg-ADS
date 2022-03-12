package main.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Manager {
    private Stage publicStage;
    private boolean isLoggedIn = false;

    // Static Varible reference to the Singleton instance
    private static Manager instance = null;

    // Static method to create instance of Singleton class
    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public Stage getStage(){
        return this.publicStage;
    }

    public void saveStage(Stage s){
        this.publicStage = s;
    }
    
}
