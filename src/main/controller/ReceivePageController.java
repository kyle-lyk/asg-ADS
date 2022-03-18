package main.controller;

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

public class ReceivePageController {
    @FXML
    private BorderPane scenePane;
    @FXML
    private  TextField requestItemName;
    @FXML
    private TextField requestItemQty;
    @FXML
    private Button requestBtn;
    @FXML
    private Button logoutBtn;
    //@FXML
    //private TableView receivedItemList;

    public void requestItem(ActionEvent e){
        String itemRequest = requestItemName.getText();
        int itemRequestQty = Integer.parseInt(requestItemQty.getText());
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
