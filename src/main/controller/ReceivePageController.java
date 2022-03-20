package main.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.model.Database;
import main.model.GlobalState;

public class ReceivePageController implements Initializable{
    @FXML
    private TextField itemNameField;

    @FXML
    private TextField itemQtyField;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label manpowerLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField new_manpowerField;

    @FXML
    private TextField new_nameField;

    @FXML
    private TableView<?> receivedItemList;

    @FXML
    private TableColumn<?, ?> RequestView;

    @FXML
    private Button requestBtn;

    @FXML
    private Button updateBtn;

    private GlobalState state = GlobalState.getInstance();
    private String username = state.getUsername();
    private String identity = state.getIdentity();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateProfileInfo();
        
    }

    @FXML 
    public void updateProfileInfo(){
        String name = state.getName();
        String manpower = state.getManPower();
        if(name.isBlank()){
            nameLabel.setText("No Data");
        }
        else{
            nameLabel.setText(name);
        }

        if(manpower.isBlank()){
            manpowerLabel.setText("No Data");
        }
        else{
            manpowerLabel.setText(manpower);
        }
    }

    @FXML
    public void updateProfile(ActionEvent e){
        String NewName = new_nameField.getText();
        String NewManpower = new_manpowerField.getText();

        if(NewName.isBlank() || NewName.contains(" ") || NewManpower.isBlank() || NewManpower.contains(" ")){
            statusLabel.setText("No empty or whitespace characters allowed");
        }
        else{
            String DataFileName = state.getDataFileName(identity);
            List<List<String>> Acc_Info = Database.readData(DataFileName);
            for(int i=0; i < Acc_Info.size(); i++){
                if(username.equals(Acc_Info.get(i).get(0))){
                    Acc_Info.get(i).set(2, NewName);
                    Acc_Info.get(i).set(3, NewManpower);
                }
            }
            Database.updateData(DataFileName, Acc_Info);
            updateProfileInfo();
            statusLabel.setText("Profile Updated");
        }

    }


    @FXML
    public void logout(ActionEvent e){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Confirm to log out?");

        if(alert.showAndWait().get() == ButtonType.OK) {        
            System.out.println("Successfully logged out");
        }
    }

}
