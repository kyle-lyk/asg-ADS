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
import main.model.Manager;

public class LoginPageController implements Initializable{

    @FXML
    private PasswordField c_passwordField;

    @FXML
    private ChoiceBox<String> identityBox;

    private String[] identityList = {"Donor", "NGO", "DC Admin"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        identityBox.getItems().addAll(identityList);
        identityBox.setValue("Donor");
        
    }

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField;

    @FXML
    void switch_to_RegisterPage(ActionEvent event) {
        try{
            Stage mainStage = Manager.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/RegisterPage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    void loginValidate(ActionEvent e){
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
                // If database is not empty, check if the username and password entered match any of the accounts
                if (Acc_Info.size() != 0) { 
                    boolean userIsExist = false;
                    for(int i=0; i < Acc_Info.size(); i++){
                        if(usernameField.getText().equals(Acc_Info.get(i).get(0))){
                            if(passwordField.getText().equals(Acc_Info.get(i).get(1))) {
                                userIsExist = true;
                                break;
                            }
                            else{
                                statusLabel.setText("Username or password is incorrect");
                            }
                        }
                        else{
                            statusLabel.setText("Username or password is incorrect");
                        }

                    }
                    // If account is found, switch to the main page
                    if(userIsExist){
                        statusLabel.setText("Account found");
                    }
                }
                // If database is empty, set the status label to error
                else{
                    statusLabel.setText("Account not found!");
                }
                
            } 
            // If passwords do not match, set the status label to error
            else {
                statusLabel.setText("Password not match");
            }
    
        }
    }



}
