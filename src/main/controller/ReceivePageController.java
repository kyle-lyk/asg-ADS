package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.Database;
import main.model.GlobalState;
import main.model.Ngo;
import main.model.RequestInfo;

public class ReceivePageController implements Initializable{

    @FXML
    private TableView<RequestInfo> requestTable;

    @FXML
    private TableColumn<RequestInfo, String> ngonameCol;

    @FXML
    private TableColumn<RequestInfo, Integer> manpowerCol;

    @FXML
    private TableColumn<RequestInfo, String> aidsCol;

    @FXML
    private TableColumn<RequestInfo, Integer> qtyCol;

    @FXML
    private TableColumn<RequestInfo, List<String>> donorCol;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField itemQtyField;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button refreshTableBtn;

    @FXML
    private Label manpowerLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label prof_statusLabel;

    @FXML
    private Label req_statusLabel;

    @FXML
    private TextField new_manpowerField;

    @FXML
    private TextField new_nameField;

    @FXML
    private Button requestBtn;

    @FXML
    private Button updateBtn;

    private GlobalState state = GlobalState.getInstance();
    private Ngo ngoUserInfo = state.getNgoSession();
    private String identity = ngoUserInfo.getIdentity();
    private String username = ngoUserInfo.getUsername();

    String DataFileName = state.getDataFileName(identity);
    List<List<String>> Acc_Info = Database.readData(DataFileName);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        reloadProfileInfo();
        reloadTableInfo();
    }

    @FXML
    public void reloadTableInfo(){
        ObservableList<RequestInfo> DataInfo = FXCollections.observableArrayList();
        requestTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ngonameCol.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("ngoName"));
        manpowerCol.setCellValueFactory(new PropertyValueFactory<RequestInfo, Integer>("manpower"));
        aidsCol.setCellValueFactory(new PropertyValueFactory<RequestInfo, String>("aids"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<RequestInfo, Integer>("qty"));
        donorCol.setCellValueFactory(new PropertyValueFactory<RequestInfo, List<String>>("donorName"));

        List<List<String>> DataList;
        DataList = Database.readData("requested_Info");
        if (DataList != null){
            for (List<String> Data : DataList){
                if(Data.get(0).equals(ngoUserInfo.getUsername())){
                    RequestInfo info = new RequestInfo(Data.get(1), Integer.parseInt(Data.get(2)), Data.get(3), Integer.parseInt(Data.get(4)), Data.get(5));
                    DataInfo.add(info);
                }     
            }
        }

        requestTable.setItems(DataInfo);
    }

    @FXML
    public void requestAids(){
        if (!(ngoUserInfo.getName().isBlank()) || !(ngoUserInfo.getManpower().isBlank())){
            String itemName = itemNameField.getText();
            if (!(itemName.isBlank()) || !(itemQtyField.getText().isBlank()))
                try {
                    Integer itemQty = Integer.parseInt(itemQtyField.getText());
                    if (itemQty <= 0){
                        req_statusLabel.setText("Quantity must be positive");
                    }
                    else {
                        List<String> Data = new ArrayList<String>();
                        Data.add(ngoUserInfo.getUsername());
                        Data.add(ngoUserInfo.getName());
                        Data.add(ngoUserInfo.getManpower());
                        Data.add(itemName);
                        Data.add(itemQty.toString());
                        Data.add(" ");
                        Database.writeData("requested_Info", Data);
                        req_statusLabel.setText("Request added successfully");
                        reloadTableInfo();
                    }      
                } 
                catch (NumberFormatException e) {
                    req_statusLabel.setText("Quantity must be an integer");
                }
            else {
                req_statusLabel.setText("Please enter item name and quantity");
            }
        }
        else{
            req_statusLabel.setText("Update your Profile before request");
        }
        

    }

   
    @FXML 
    public void reloadProfileInfo(){
        String name = ngoUserInfo.getName();
        String manpower = ngoUserInfo.getManpower();
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
        List<List<String>> Req_Info = Database.readData("requested_Info");
        String NewName = new_nameField.getText();

        if(NewName.isBlank() || new_manpowerField.getText().isBlank()){
            prof_statusLabel.setText("No empty or whitespace characters");
        }
        else{
            try {
                Integer NewManpower = Integer.parseInt(new_manpowerField.getText());
                if (NewManpower <= 0){
                    prof_statusLabel.setText("Manpower must be positive");
                }
                else{
                    for(int i=0; i < Acc_Info.size(); i++){
                        if(username.equals(Acc_Info.get(i).get(0))){
                            Acc_Info.get(i).set(2, NewName);
                            Acc_Info.get(i).set(3, NewManpower.toString());
                        }
                    }
                    for(int i=0; i < Req_Info.size(); i++){
                        if(username.equals(Req_Info.get(i).get(0))){
                            Req_Info.get(i).set(1, NewName);
                            Req_Info.get(i).set(2, NewManpower.toString());
                        }
                    }
                    Database.updateData(DataFileName, Acc_Info);
    
                    Database.updateData("requested_Info", Req_Info);
                    ngoUserInfo.setName(NewName);
                    ngoUserInfo.setManpower(NewManpower.toString());
                    reloadProfileInfo();
                    reloadTableInfo();
                    prof_statusLabel.setText("Profile Updated");
                }
                
            }
            catch (NumberFormatException e1) {
                prof_statusLabel.setText("Manpower must be an integer");
            }

        }

    }


    @FXML
    public void logout(ActionEvent e){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Confirm to log out?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            switch_to_LoginPage();
            state.Init();        
            System.out.println("Successfully logged out");
        }
    }

    @FXML
    void switch_to_LoginPage() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/LoginPage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
