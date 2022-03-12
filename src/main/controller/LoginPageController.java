package main.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import main.model.Manager;

public class LoginPageController {

    @FXML
    private Button dc_adminBtn;

    @FXML
    private Button donorBtn;

    @FXML
    private Button ngo_Btn;

    @FXML
    private Hyperlink registerLink;
    
    public void login_Donor(ActionEvent e) {
        
    }

    public void login_dcAdmin(ActionEvent e) {

    }

    public void login_NGO(ActionEvent e) {

    }

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

}
