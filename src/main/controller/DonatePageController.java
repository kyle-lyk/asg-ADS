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
import java.util.UUID;

import main.model.Database;
import main.model.DonateInfo;
import main.model.Donor;
import main.model.GlobalState;

/**
 * Controller for DonatePage to control logic in the DonatePage.fxml when user interact with it.
 */
public class DonatePageController implements Initializable{

    //////////////// start of JavaFX Components Variables ///////////////////

    @FXML
    private Button donateBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button refreshTableBtn;
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
    private TableColumn<DonateInfo,Integer> remainQtyCol;
    @FXML
    private TableColumn<DonateInfo,String> ngoCol;

    //////////////// end of JavaFX Components Variables ///////////////////

    private GlobalState state = GlobalState.getInstance();
    private Donor donorUserInfo = state.getDonorSession();
    private String identity = donorUserInfo.getIdentity();
    private String username = donorUserInfo.getUsername();
    
    String DataFileName = state.getDataFileName(identity);
    List<List<String>> Acc_Info = Database.readData(DataFileName);

    @Override
    /**
     * Show the donate table and donor profile information when DonatePage is initialized. Method from javafx.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle rb) {
        reloadTableInfo();
        reloadProfileInfo();
    }

    @FXML
    /**
     * To reload the donate table information after donor donated item.
     */
    private void reloadTableInfo(){
        ObservableList<DonateInfo> itemlist = FXCollections.observableArrayList();
        donatedItemTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        donorCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("donorName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("phoneNum"));
        donatedCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("donatedItem"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, Integer>("donatedItemQty"));
        remainQtyCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, Integer>("remainQty"));
        ngoCol.setCellValueFactory(new PropertyValueFactory<DonateInfo, String>("NGOReceived"));

        List<List<String>> ItemList = Database.readData("donated_Info");
        if(ItemList != null){
            for(List<String> item : ItemList){
                if(item.get(1).equals(donorUserInfo.getUsername())){
                    DonateInfo info = new DonateInfo(
                                        item.get(2), 
                                        item.get(3),
                                        item.get(4),
                                        Integer.parseInt(item.get(5)), 
                                        Integer.parseInt(item.get(6)), 
                                        item.get(7)
                                    );
                    itemlist.add(info);
                }
            }
        }
        donatedItemTable.setItems(itemlist);
    }

    @FXML
    /**
     * Read donor input and check input validity only update the data to database as well as donate table.
     */
    private void donateAids(){
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
                        Data.add(UUID.randomUUID().toString());
                        Data.add(donorUserInfo.getUsername());
                        Data.add(donorUserInfo.getName());
                        Data.add(donorUserInfo.getPhonenum());
                        Data.add(donateItem);
                        Data.add(itemQty.toString()); 
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
    /**
     * Reload the Donor Profile after donor updated their profile data.
     */
    private void reloadProfileInfo(){
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
    /**
     * Update donor profile after validating the inputs.
     * @param e mouse click action received from user
     */
    private void updateProfile(ActionEvent e){
        String NewName = new_nameField.getText();

        if ( (donorUserInfo.getName().isBlank()) && (donorUserInfo.getPhonenum().isBlank()) )
        {
            if(NewName.isBlank() || new_phonenumField.getText().isBlank()){
                prof_statusLabel.setText("No empty or whitespace characters");
            }
            else{
                try {
                    Integer NewPhoneNum = Integer.parseInt(new_phonenumField.getText());
                    int length = NewPhoneNum.toString().length();

                    if(length > 10 || length < 9){
                        prof_statusLabel.setText("Invalid phone number length");

                    }else{boolean donorNameIsExist = false;
                    
                        for (int i = 0; i < Acc_Info.size(); i++){
                            if(NewName.equals(Acc_Info.get(i).get(2))){
                                prof_statusLabel.setText("Username already exists!");
                                donorNameIsExist = true;
                                break;
                            }
                        }
                        if(!donorNameIsExist){
                            for(int i=0; i < Acc_Info.size(); i++){
                                if(username.equals(Acc_Info.get(i).get(0))){
                                    Acc_Info.get(i).set(2, NewName);
                                    Acc_Info.get(i).set(3, "+60" + NewPhoneNum.toString());
                                }
                            }
                            Database.updateData(DataFileName, Acc_Info);
                            donorUserInfo.setName(NewName);
                            donorUserInfo.setPhonenum(NewPhoneNum.toString());
                            reloadProfileInfo();
                            prof_statusLabel.setText("Profile Updated");
                        }
                    }
                    

                }
                catch (NumberFormatException e1) {
                    prof_statusLabel.setText("Phone number must be in integer");
                }
    
            }

        }
        else{
            prof_statusLabel.setText("You have already updated your profile");
        }

    }

    @FXML
    /**
     * Log out from donor account.
     * @param e mouse click action received from user
     */
    private void logout(ActionEvent e){
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
    /**
     * Switch to LoginPage after logged out from donor account.
     */
    private void switch_to_LoginPage() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/LoginPage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }    
}

