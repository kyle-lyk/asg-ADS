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
 * This controller will handle the user interaction logic for DonatePage.fxml
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
    private Button checkStatusBtn;
    @FXML
    private Label donate_statusLabel;
    @FXML
    private Label prof_statusLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField donateItemName;
    @FXML
    private TextField donateItemQty;
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
    
    String DataFileName = state.getDataFileName(identity);
    List<List<String>> Acc_Info = Database.readData(DataFileName);

    /**
     * Show the donate table and donor profile information when DonatePage is initialized. Method from JavaFx.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadTableInfo();
        nameLabel.setText(donorUserInfo.getName());
    }

    /**
     * To reload the donate table information after donor donated item.
     */
    @FXML
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

    /**
     * Read donor input and check input validity only update the data to database as well as donate table.
     */
    @FXML
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

    /**
     * Log out from donor account.
     * @param e mouse click action received from user
     */
    @FXML
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

    /**
     * Switch to LoginPage after logged out from donor account.
     */
    @FXML
    private void switch_to_LoginPage() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/LoginPage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }    

    /**
     * Switch to CollectionStatusPage.
     */
    @FXML
    private void switch_to_CollectionStatusPage() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/CollectionStatusPage.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


}
