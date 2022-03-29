package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.model.Database;
import main.model.GlobalState;

/**
 * Controller for RegisterPage to control logic in the RegisterPage.fxml when user interact with it.
 */
public class RegisterPageController implements Initializable {
     
    //////////////// start of JavaFX Components Variables ///////////////////

    @FXML
    private PasswordField c_passwordField;
    @FXML
    private ChoiceBox<String> identityBox;
    private String[] identityList = {"Donor", "NGO"};
    @FXML
    private Hyperlink loginLink;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField usernameField;
    @FXML
    private Label statusLabel;

    //////////////// end of JavaFX Components Variables ///////////////////

    /**
     * Initialize action for Register Page. Method from JavaFx.
     * To assign Identity Choice Box Values.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        identityBox.getItems().addAll(identityList);
        identityBox.setValue("Donor");
        
    }
    
    /**
     * Switch to Login Page.
     * @param event Mouse click action received from user
     */
    @FXML
    void switch_to_LoginPage(ActionEvent e) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/LoginPage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Register Validation before User register new User data to the Database.
     * @param event Mouse click action received from user
     */
    @FXML
    void registerValidate(ActionEvent e){
        // Check if any of the fields contain empty or whitespace characters, if yes set the status label to error
        if ((passwordField.getText().isBlank()) || (passwordField.getText().contains(" ")) ||
            (c_passwordField.getText().isBlank()) || (c_passwordField.getText().contains(" ")) ||
            (usernameField.getText().isBlank()) || (usernameField.getText().contains(" ")))
            statusLabel.setText("No empty or whitespace characters allowed");
        else{ 
            // If all fields are filled, check if the passwords match
            if (passwordField.getText().equals(c_passwordField.getText())) {
                // Obtain selected identity from choicebox and convert it to database file name
                String identity = identityBox.getValue();
                String filename;
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
                    default:
                        filename = "";
                        break;
                }
                
                List<List<String>> Acc_Info = Database.readData(filename);
                // If database is not empty, check if the username is already taken
                if (Acc_Info.size() != 0) { 
                    boolean usernameIsExist = false;
                    for(int i=0; i < Acc_Info.size(); i++){
                        if(usernameField.getText().equals(Acc_Info.get(i).get(0))){
                            statusLabel.setText("Username already exists!");
                            usernameIsExist = true;
                            break;
                        }
                    }
                    // If username is not exist in the database, write a new data to the database
                    if(!usernameIsExist){
                        Database.writeData(filename, List.of(usernameField.getText(), passwordField.getText(), " ", " "));
                        statusLabel.setText("Registration Successful!");
                    }
                }
                // If database is empty, write data without checking anything
                else{
                    Database.writeData(filename, List.of(usernameField.getText(), passwordField.getText(), " ", " "));
                    statusLabel.setText("Registration Successful!");
                }
                
            } 
            // If passwords do not match, set the status label to error
            else {
                statusLabel.setText("Password not match");
            }
    
        }
    }
    
}
