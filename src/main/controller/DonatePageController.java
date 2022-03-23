package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import main.model.Database;
import main.model.DonateInfo;
import main.model.Donor;
import main.model.GlobalState;

public class DonatePageController implements Initializable{
    @FXML
    private Button donateBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phonenumLabel;    
    @FXML
    private Label donate_statusLabel;
    @FXML
    private Label prof_statusLabel;
    @FXML
    private TextField donateItemName;
    @FXML
    private TextField donateItemQty;
    @FXML
    private TextField new_nameField;
    @FXML
    private TextField new_phonenumField;
    @FXML
    private TableView<DonateInfo> donatedItemTable;
    @FXML
    private TableColumn<DonateInfo, String> donorCol;
    @FXML
    private TableColumn<DonateInfo, String> phoneCol;
    @FXML
    private TableColumn<DonateInfo,String> donatedCol;
    @FXML
    private TableColumn<DonateInfo,Integer> qtyCol;
    @FXML
    private TableColumn<DonateInfo,String> ngoCol;

    private GlobalState state = GlobalState.getInstance();
    private Donor donorUserInfo = state.getDonorSession();
    private String identity = donorUserInfo.getIdentity();
    private String username = donorUserInfo.getUsername();
    
    String DataFileName = state.getDataFileName(identity);
    List<List<String>> Acc_Info = Database.readData(DataFileName);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadTableInfo();
        reloadProfileInfo();
    }

    @FXML
    public void reloadTableInfo(){
        ObservableList<DonateInfo> itemlist = FXCollections.observableArrayList();
        donatedItemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        donorCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("donorName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("phoneNum"));
        donatedCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("donatedItem"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, Integer>("donatedItemQty"));
        ngoCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("NGOReceived"));

        List<List<String>> ItemList = Database.readData("donated_Info");
        if(ItemList != null){
            for(List<String> item : ItemList){
                if(item.get(0).equals(donorUserInfo.getUsername())){
                    DonateInfo info = new DonateInfo(item.get(1), item.get(2),item.get(3),Integer.parseInt(item.get(4)), item.get(5));
                    itemlist.add(info);
                }
            }
        }
        donatedItemTable.setItems(itemlist);
    }

    @FXML
    public void donateAids(){
        if(!(donorUserInfo.getName().isBlank()) || !(donorUserInfo.getPhonenum().isBlank())){
            String donateItem = donateItemName.getText();
            if(!(donateItem.isBlank()) || !(donateItemQty.getText().isBlank())){
                try{
                    Integer itemQty = Integer.parseInt(donateItemQty.getText());
                    if(itemQty <= 0){
                        donate_statusLabel.setText("Quantity must be positive");
                    }
                    else {
                        List<String> Data = new ArrayList<String>();
                        Data.add(donorUserInfo.getUsername());
                        Data.add(donorUserInfo.getName());
                        Data.add(donorUserInfo.getPhonenum());
                        Data.add(donateItem);
                        Data.add(itemQty.toString());
                        Data.add(" ");
                        Database.writeData("donated_Info", Data);
                        donate_statusLabel.setText("Successfully Donated");
                        reloadTableInfo();
                    }
                }catch(NumberFormatException e){
                    donate_statusLabel.setText("Quantity must be an integer");
                }
            
            }
            else{
                donate_statusLabel.setText("Please enter item name and quantity");
            }
        }else{
            donate_statusLabel.setText("Update your profile before donate");
        }
    }

    @FXML 
    public void reloadProfileInfo(){
        String name = donorUserInfo.getName();
        String phonenum = donorUserInfo.getPhonenum();
        if(name.isBlank()){
            nameLabel.setText("No Data");
        }
        else{
            nameLabel.setText(name);
        }

        if(phonenum.isBlank()){
            phonenumLabel.setText("No Data");
        }
        else{
            phonenumLabel.setText(phonenum);
        }
    }

    @FXML
    public void updateProfile(ActionEvent e){
        List<List<String>> Donate_Info = Database.readData("donated_Info");
        String NewName = new_nameField.getText();

        if(NewName.isBlank() || new_phonenumField.getText().isBlank()){
            prof_statusLabel.setText("No empty or whitespace characters");
        }
        else if(new_phonenumField.getText().length() > 11){
            prof_statusLabel.setText("Invalid phone number length");
        }
        else{
            try {
                Integer NewPhoneNum = Integer.parseInt(new_phonenumField.getText());
                    for(int i=0; i < Acc_Info.size(); i++){
                        if(username.equals(Acc_Info.get(i).get(0))){
                            Acc_Info.get(i).set(2, NewName);
                            Acc_Info.get(i).set(3, NewPhoneNum.toString());
                        }
                    }
                    for(int i=0; i < Donate_Info.size(); i++){
                        if(username.equals(Donate_Info.get(i).get(0))){
                            Donate_Info.get(i).set(1, NewName);
                            Donate_Info.get(i).set(2, NewPhoneNum.toString());
                        }
                    }
                    Database.updateData(DataFileName, Acc_Info);
    
                    Database.updateData("donated_Info", Donate_Info);
                    donorUserInfo.setName(NewName);
                    donorUserInfo.setPhonenum(NewPhoneNum.toString());
                    reloadProfileInfo();
                    reloadTableInfo();
                    prof_statusLabel.setText("Profile Updated");
                }
                catch (NumberFormatException e1) {
                    prof_statusLabel.setText("Phone number must be in integer");
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

