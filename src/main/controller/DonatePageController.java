package main.controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DonatePageController {
    @FXML
    private BorderPane scenePane;
    @FXML
    private  TextField donateItemName;
    @FXML
    private TextField donateItemQty;
    @FXML
    private Button donateBtn;
    @FXML
    private Button logoutBtn;
    //@FXML
    //private TableView donatedItemList;

    public void donateItem(ActionEvent e){
        int itemDonateQty = Integer.parseInt(donateItemQty.getText());
        String itemDonate = donateItemName.getText();
    }

    public void logout(ActionEvent e){
        Stage stage;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Confirm to log out?");

        if(alert.showAndWait().get() == ButtonType.OK) {        
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Successfully logged out");
            stage.close();
        }
    }
}
