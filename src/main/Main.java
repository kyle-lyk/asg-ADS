package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import main.model.GlobalState;

/**
 * Main.java is the main file to run the whole program
 */
public class Main extends Application {

    /**
     * Start method to start the JavaFx program.
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        
        try {
            // Singleton Design Pattern
            GlobalState mainScreen = GlobalState.getInstance();
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

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}