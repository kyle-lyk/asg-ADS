package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import main.model.Manager;
 
public class Run extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        try {
            // Singleton Design Pattern
            Manager mainScreen = Manager.getInstance();
            mainScreen.saveStage(primaryStage);
            Parent root = FXMLLoader.load(getClass().getResource("view/LoginPage.fxml"));

            primaryStage.setTitle("Aid Distribution System Cyberjaya - G9");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 1280, 720));

            primaryStage.show();
                    
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}