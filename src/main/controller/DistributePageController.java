package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.Database;
import main.model.DistAids;
import main.model.DonateInfo;
import main.model.GlobalState;
import main.model.RequestInfo;
import main.model.AidList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

/**
 * This controller will handle the user interaction logic
 * for DistributePage.fxml 
 */
public class DistributePageController implements Initializable{

    @FXML
    private TableColumn<AidList, Integer> rowNumColumn3;

    @FXML
    private TableColumn<AidList, Integer> rowNumColumn4;

    @FXML
    private TableColumn<AidList, String> aidColumn3;

    @FXML
    private TableColumn<AidList, String> aidColumn4;

    @FXML
    private Button selectItemBtn;

    @FXML
    private Button showAllBtn;

    @FXML
    private Button matchAidsBtn;

    @FXML
    private TableColumn<AidList, String> donorColumn4;

    @FXML
    private TableColumn<AidList, Integer> manpowerColumn3;

    @FXML
    private TableColumn<AidList, String> ngoColumn3;

    @FXML
    private TableColumn<AidList, String> phoneColumn4;

    @FXML
    private TableColumn<AidList, Integer> quantityColumn3;

    @FXML
    private TableColumn<AidList, Integer> quantityColumn4;

    @FXML
    private Text tErrorMsg3;

    @FXML
    private Text matchAidsText;

    @FXML
    private Text tGeneralText2;

    @FXML
    private Text tTableName1;

    @FXML
    private Text tTableName2;

    @FXML
    private TextField itemNameField;

    @FXML
    private TableView<AidList> tvMainDonor;

    @FXML
    private TableView<AidList> tvMainNGO;

    private GlobalState state = GlobalState.getInstance();
    boolean itemFilterExistNgo = false;
    boolean itemFilterExistDonor = false;
    
    boolean rowselectable = false;

    ArrayList<String> donorUUIDList = new ArrayList<String>();
    ArrayList<String> ngoUUIDList = new ArrayList<String>();

    @Override
    /**
     * Populates both NGO and Donor table when DistributePage is initialized (JavaFX method).
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle rb) {

        // load data from CSV and populate both tables
        updateNGOTable();
        updateDonorTable();

        ngoPerRowEventHandler();
        donorPerRowEventHandler();
    }

    /**
     * Updates and populates NGO table based on relevant CSV file.
     * @param Title_Cased_filterItem filter item name
     */
    private void updateNGOTable(String Title_Cased_filterItem) {
        ObservableList<AidList> ngoList = FXCollections.observableArrayList();

        rowNumColumn3.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("rowNum"));
        ngoColumn3.setCellValueFactory(new PropertyValueFactory<AidList, String>("ngo"));
        manpowerColumn3.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("manpower"));
        aidColumn3.setCellValueFactory(new PropertyValueFactory<AidList, String>("aids"));
        quantityColumn3.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("quantity"));

        List<List<String>> DataList = Database.readData("requested_Info");
        ngoUUIDList.clear();
        if (DataList != null) {
            Integer rowNum = 0;
            for (List<String> Data: DataList) {
                if ((Integer.parseInt(Data.get(6))) != 0) {
                    if (Title_Cased_filterItem.isBlank() || Title_Cased_filterItem.equalsIgnoreCase(Data.get(4))) {
                        rowNum++;
                        AidList info = new AidList(
                                                rowNum, Data.get(2), 
                                                Integer.parseInt(Data.get(3)), 
                                                Data.get(4), 
                                                Integer.parseInt(Data.get(6))
                                                );
                        ngoList.add(info);
                        ngoUUIDList.add(Data.get(0));
                    }
                }
            }
        }
        
        if( !(ngoList.size() == 0) && !(Title_Cased_filterItem.isBlank()) ) {
            itemFilterExistNgo = true;
        }

        tvMainNGO.setItems(ngoList);
        
    }

    /**
     * Method overloading to refresh NGO table.
     */
    private void updateNGOTable(){
        updateNGOTable("");
    }
    
    /**
     * Updates and populates Donor table based on relevant CSV file.
     * @param Title_Cased_filterItem filter item name
     */
    public void updateDonorTable(String Title_Cased_filterItem) {
        ObservableList<AidList> donorList = FXCollections.observableArrayList();
        tvMainDonor.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        rowNumColumn4.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("rowNum"));
        donorColumn4.setCellValueFactory(new PropertyValueFactory<AidList, String>("donor"));
        phoneColumn4.setCellValueFactory(new PropertyValueFactory<AidList, String>("phone"));
        aidColumn4.setCellValueFactory(new PropertyValueFactory<AidList, String>("aids"));
        quantityColumn4.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("quantity"));

        List<List<String>> DataList = Database.readData("donated_Info");
        donorUUIDList.clear();
        if (DataList != null) {
            Integer rowNum = 0;
            for (List<String> Data: DataList) {
                if ((Integer.parseInt(Data.get(6))) != 0) {
                    if (Title_Cased_filterItem.isBlank() || Title_Cased_filterItem.equalsIgnoreCase(Data.get(4))) {
                        rowNum++;
                        AidList info = new AidList(
                                                rowNum, 
                                                Data.get(2), 
                                                Data.get(3), 
                                                Data.get(4), 
                                                Integer.parseInt(Data.get(6))
                                                );
                        donorList.add(info);
                        donorUUIDList.add(Data.get(0));
                    }
                }
            }
        }
        
        if( !(donorList.size() == 0) && !(Title_Cased_filterItem.isBlank()) ) {
            itemFilterExistDonor = true;
        }
        tvMainDonor.setItems(donorList);
        
    }

    /**
     * Method overloading to refresh Donor table.
     */
    private void updateDonorTable(){
        updateDonorTable("");
    }

    ///////////////// ///////////////////

    @FXML
    /**
     * When button is clicked, an alert window will pop up
     * to confirm log out process
     * @param event mouse click action from user
     */
    private void logoutBtnClicked(ActionEvent event) {
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
     * When button is clicked, scene will be 
     * changed to DCHistoryPage.
     * @param event mouse click action from user
     */
    private void btnAidHistoryClicked3(ActionEvent event) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/DCHistoryPage.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));

            DCHistoryPageController dcHistoryController = loader.getController();
            dcHistoryController.showGeneralText("Displaying matched aid history");


        } catch (IOException ioe){
            ioe.printStackTrace();
            }
    }

    @FXML
    /**
     * When button is clicked, text from itemNameField
     * will be obtained as filterItem.
     * Error message is displayed when filterItem
     * does not meet the required conditions.
     * @param event mouse click action from user
     */
    private void filterItem(ActionEvent event) {
        String filterItem = itemNameField.getText();
        System.out.println("filterItem: " + filterItem);
        
        if (!(filterItem.isEmpty())) {
            String Title_Cased_filterItem = filterItem.substring(0,1).toUpperCase() + filterItem.substring(1).toLowerCase();
            updateDonorTable(Title_Cased_filterItem);
            updateNGOTable(Title_Cased_filterItem);
            System.out.println(itemFilterExistNgo);
            System.out.println(itemFilterExistDonor);
            if (itemFilterExistDonor && itemFilterExistNgo) {               
                resetFlag();
                rowselectable = true;
            } 
            else {
                tErrorMsg3.setText("Please enter an item that exists in both tables!");
            }
        }
        else{
            tErrorMsg3.setText("Please enter an item name!");
        }

    }


    @FXML
    /**
     * When button is clicked, tables will be
     * refreshed to display unfiltered content.
     * @param event mouse click action from user
     */
    private void showAll(ActionEvent event) {
        updateDonorTable();
        updateNGOTable();
        tErrorMsg3.setText("");
        matchAidsText.setText("");
        resetVar();
        rowselectable = false;
        refreshScene();
    }

    @FXML
    /**
     * When button is clicked, scene will be 
     * changed to DCHistoryPage with updated aid matching results
     * if all the conditions are met.
     * @param event mouse click action from user
     */
    private void matchAids(ActionEvent event) {
        if ((ngoSelectedList.isEmpty() || donorSelectedList.isEmpty())) {
            matchAidsText.setText("You must select at least one row from each table!");
        }
        else {
            System.out.println("Pass to function");
            // call function that determines which function is called from model
            if (associationFlag()) {
                // switch scene
                try{
                    Stage mainStage = GlobalState.getInstance().getStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/DCHistoryPage.fxml"));
                    Parent root = loader.load();
                    mainStage.setScene(new Scene(root, 1280, 720));
        
                    // get instance of controller
                    DCHistoryPageController dcHistoryController = loader.getController();
        
                    // pass data from original scene to new scene here / modify view
                    dcHistoryController.showGeneralText("Aid matched successfully!");
        
                } catch (IOException ioe){
                    ioe.printStackTrace();
                    }
            }
        }
    }

    /**
     * This function will return a false boolean value if
     * the aid matching is rejected (illegal matching).
     * If the aid matching is accepted, display the results
     * in another scene and update the relevant CSV files
     * @return isMatch boolean
     */
    private boolean associationFlag() {
        boolean isMatch = true; // set to false for illegal matching

        List<List<String>> donatedList = Database.readData("donated_Info");
        List<List<String>> requestedList = Database.readData("requested_Info");
        ArrayList<List<String>> selected_requestedList = new ArrayList<List<String>>();
        ArrayList<List<String>> selected_donatedList = new ArrayList<List<String>>();
        ArrayList<String> selected_donorUUIDList = new ArrayList<String>();
        ArrayList<String> selected_ngoUUIDList = new ArrayList<String>();

        for (Integer rowIndex : donorSelectedList) {
            selected_donorUUIDList.add(donorUUIDList.get(rowIndex - 1));
        }
        for (Integer rowIndex : ngoSelectedList) {
            selected_ngoUUIDList.add(ngoUUIDList.get(rowIndex - 1));
        }

        // Add selected donate row into a list by comparing UUID
        for (int i=0; i < selected_donorUUIDList.size(); i++) {
            for (int j=0; j < donatedList.size(); j++) {
                if (selected_donorUUIDList.get(i).equals(donatedList.get(j).get(0))) {
                    selected_donatedList.add(donatedList.get(j));
                }
            }
        }


        // Add selected request row into a list by comparing UUID
        for (int i=0; i < selected_ngoUUIDList.size(); i++) {
            for (int j=0; j < requestedList.size(); j++) {
                if (selected_ngoUUIDList.get(i).equals(requestedList.get(j).get(0))) {
                    selected_requestedList.add(requestedList.get(j));
                }
            }
        }


        System.out.println(selected_donatedList);
        System.out.println(selected_requestedList);
        
        
        if ((donorSelectedList.size()) == 1 && ((ngoSelectedList.size())) == 1) {
            System.out.println("One-to-one");

            DonateInfo donateInfo = loadDonateInfo(selected_donatedList,0);
            RequestInfo requestInfo = loadRequestInfo(selected_requestedList,0);

            DistAids distAids = new DistAids(donateInfo, requestInfo);
            distAids.matchAids();

            save_matchAidsResult(distAids, selected_donorUUIDList, selected_ngoUUIDList);
            updateUserList(distAids, selected_donorUUIDList, selected_ngoUUIDList);

        }
        else if ((donorSelectedList.size()) == 1 && ((ngoSelectedList.size()) >= 1)) {
            System.out.println("One-to-many"); 
            DonateInfo donateInfo = loadDonateInfo(selected_donatedList,0);
            if (donateInfo.getRemainQty() <= (loadRequestInfo(selected_requestedList,0)).getRemainQty()){
                System.out.println("Illegal one to many");
                isMatch = false;
                matchAidsText.setText("Aid matching is rejected!");
            }
            else{
                for (int j=0; j < selected_requestedList.size(); j++) {
                    RequestInfo requestInfo = loadRequestInfo(selected_requestedList,j);
                    DistAids distAids = new DistAids(donateInfo, requestInfo);

                    distAids.matchAids();
                    save_matchAidsResult(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                    updateUserList(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                
                }
            }

        }
        else if ((donorSelectedList.size()) >= 1 && ((ngoSelectedList.size()) == 1)) {

            System.out.println("Many-to-one"); 

            RequestInfo requestInfo = loadRequestInfo(selected_requestedList,0);
            if (requestInfo.getRemainQty() <= (loadDonateInfo(selected_donatedList,0)).getRemainQty()){
                System.out.println("Illegal many to one");
                isMatch = false;
                matchAidsText.setText("Aid matching is rejected!");
            }
            else{
                for (int j=0; j < selected_donatedList.size(); j++) {
                    DonateInfo donateInfo = loadDonateInfo(selected_donatedList,j);
    
                    DistAids distAids = new DistAids(donateInfo, requestInfo);
                    distAids.matchAids();
                    save_matchAidsResult(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                    updateUserList(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                }
            }

        }
        else if ((donorSelectedList.size()) >= 1 && ((ngoSelectedList.size()) >= 1)) {
            System.out.println("Many-to-many"); 
            int donorQty = 0;
            int ngoQty = 0;
            for (List<String> Data : selected_donatedList) {
                donorQty += Integer.parseInt(Data.get(6));
            }
            for (int data =0; data < (selected_requestedList.size() - 1); data++) {
                ngoQty += Integer.parseInt(selected_requestedList.get(data).get(6));
            }
                
            if (donorQty <= ngoQty){
                System.out.println("Illegal many to many");
                isMatch = false;
                matchAidsText.setText("Aid matching is rejected!");
            }
            else{
                int i = 0;
                int j = 0;
                RequestInfo requestInfo = loadRequestInfo(selected_requestedList,i);
                DonateInfo donateInfo = loadDonateInfo(selected_donatedList,j);
                while(i < selected_requestedList.size() && j < selected_donatedList.size()){
                    DistAids distAids = new DistAids(donateInfo, requestInfo);
                    
                    distAids.matchAids();
                    
                    save_matchAidsResult(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                    updateUserList(distAids, selected_donorUUIDList, selected_ngoUUIDList);
                    if (distAids.getDonateInfo().getRemainQty() == 0){
                        j++;
                        if (j < selected_donatedList.size()){
                            donateInfo = loadDonateInfo(selected_donatedList,j);
                        }
                    }
                    if (distAids.getRequestInfo().getRemainQty() == 0){
                        i++;
                        if (i < selected_requestedList.size()){
                            requestInfo = loadRequestInfo(selected_requestedList,i);
                        }
                    }

                    
                }
            }
        }

        return isMatch;
    }

    /**
     * Creates DonateInfo object based on rows that user have selected.
     * @param selectedList contains rows that user selected
     * @param i index for selectedList
     * @return DonateInfo object
     */
    private DonateInfo loadDonateInfo( List<List<String>>selectedList ,int i){
        String donorName = selectedList.get(i).get(2);
        String phoneNum = selectedList.get(i).get(3);
        String donatedItem = selectedList.get(i).get(4);
        Integer donatedItemQty = Integer.parseInt(selectedList.get(i).get(5));
        Integer remainDonateQty = Integer.parseInt(selectedList.get(i).get(6));
        String NGOReceived = selectedList.get(i).get(7);

        DonateInfo donateInfo = new DonateInfo(donorName, phoneNum, donatedItem, donatedItemQty, remainDonateQty, NGOReceived);

        return donateInfo;
    }

    /**
     * Creates RequestInfo object based on rows that user have selected.
     * @param selectedList list containing rows that user selected
     * @param i index for selectedList
     * @return RequestInfo object
     */
    private RequestInfo loadRequestInfo( List<List<String>>selectedList ,int i){
        String ngoName = selectedList.get(i).get(2);
        Integer manpower = Integer.parseInt(selectedList.get(i).get(3));
        String requestedItem = selectedList.get(i).get(4);
        Integer requestedItemQty = Integer.parseInt(selectedList.get(i).get(5));
        Integer remainRequestQty = Integer.parseInt(selectedList.get(i).get(6));
        String donorDonated = selectedList.get(i).get(7);

        RequestInfo requestInfo = new RequestInfo(ngoName, manpower, requestedItem, requestedItemQty, remainRequestQty, donorDonated);

        return requestInfo;
    }

    /**
     * Saves the result of aid matching in relevant CSV file.
     * @param distAids DistAids object
     * @param selected_donorUUIDList list containing UUIDs of selected Donors 
     * @param selected_ngoUUIDList list containing UUIDs of selected NGOs 
     */
    private void save_matchAidsResult(DistAids distAids, ArrayList<String> selected_donorUUIDList, ArrayList<String> selected_ngoUUIDList) {
        //save data to distributed_Info.csv
        String donorName = distAids.getDonateInfo().getDonorName();
        String donorPhone = distAids.getDonateInfo().getPhoneNum();
        String aid = distAids.getDonateInfo().getDonatedItem();
        Integer donatedQty = distAids.getDonatedQty();
        String ngoName = distAids.getRequestInfo().getNgoName();
        Integer manpower = distAids.getRequestInfo().getManpower();

        List<String> distInfo = new ArrayList<String>(
                                                    Arrays.asList(
                                                        donorName,
                                                        donorPhone,
                                                        aid,
                                                        String.valueOf(donatedQty),
                                                        ngoName,
                                                        String.valueOf(manpower)
                                                    )
        );

        Database.writeData("distributed_Info", distInfo);

    }

    /**
     * Updates user data in relevant CSV files based on aid matching result.
     * @param distAids DistAids object
     * @param selected_donorUUIDList list containing UUIDs of selected Donors 
     * @param selected_ngoUUIDList list containing UUIDs of selected NGOs 
     */
    private void updateUserList(DistAids distAids, ArrayList<String> selected_donorUUIDList, ArrayList<String> selected_ngoUUIDList){
        Integer requestRemainQty = distAids.getRequestInfo().getRemainQty();
        Integer donateRemainQty = distAids.getDonateInfo().getRemainQty();

        String donorName = distAids.getDonateInfo().getDonorName();
        String ngoName = distAids.getRequestInfo().getNgoName();
        String donorNameList = "";

        List<List<String>> requestList = Database.readData("requested_Info");
        List<List<String>> donateList = Database.readData("donated_Info");

        for (int i = 0; i < requestList.size(); i++) { // iterate database
            for (int j = 0; j < selected_ngoUUIDList.size(); j++) { // iterate selected uuid list
                if (selected_ngoUUIDList.get(j).equals(requestList.get(i).get(0))) { // match uuid with database
                    requestList.get(i).set(6, String.valueOf((requestRemainQty)));
                    donorNameList = requestList.get(i).get(7);
                    if (donorNameList.isBlank()) {
                        requestList.get(i).set(7, donorName);
                    }
                    else if(donorNameList.contains(donorName)){
                        continue;
                    }
                    else{
                        requestList.get(i).set(7, donorNameList + "-" + donorName);
                    }
                    
                } 
            }
            System.out.println(i);      
        }
        
        for (int i = 0; i < donateList.size(); i++) {
            for (int j = 0; j < selected_donorUUIDList.size(); j++) {
                if (selected_donorUUIDList.get(j).equals(donateList.get(i).get(0))) {
                    donateList.get(i).set(6, String.valueOf((donateRemainQty)));
                    String ngoNameList = donateList.get(i).get(7);
                    if (ngoNameList.isBlank()) {
                        donateList.get(i).set(7, ngoName);
                    }
                    else if(ngoNameList.contains(ngoName)){
                        continue;
                    }
                    else{
                        donateList.get(i).set(7, ngoNameList + "-" + ngoName);
                    }
                }
            }
        }
        
        Database.updateData("requested_Info", requestList);
        Database.updateData("donated_Info", donateList);
    }

    /**
     * Resets the boolean value for item filter checking.
     */
    public void resetFlag() {
        itemFilterExistNgo = false;
        itemFilterExistDonor = false;
    }

    ////////////////////////////////////////////////////////////////
    ArrayList<Integer> ngoSelectedList = new ArrayList();
    ArrayList<Integer> donorSelectedList = new ArrayList();


    /**
     * Event handler for per row selection in NGO table.
     */
    private void ngoPerRowEventHandler() {
        EventHandler<MouseEvent> onClick = this::ngoTableRowMouseClickHandler;

        tvMainNGO.setRowFactory(param -> {
            TableRow<AidList> row1 = new TableRow<>();
            row1.setOnMouseClicked(onClick);
            return row1;
        });    
    }

    /**
     * Event handler for per row selection in Donor table.
     */
    private void donorPerRowEventHandler() {
        EventHandler<MouseEvent> onClick = this::donorTableRowMouseClickHandler;

        tvMainDonor.setRowFactory(param -> {
            TableRow<AidList> row2 = new TableRow<>();
            row2.setOnMouseClicked(onClick);
            return row2;
        });
    }

    /**
     * This function will change the NGO table row colour 
     * that users click on to green.
     * @param event mouse click action from user
     */
    private void ngoTableRowMouseClickHandler(MouseEvent event) {
        if (rowselectable){
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 1) {
                @SuppressWarnings("unchecked")
                TableRow<AidList> row1 = (TableRow<AidList>) event.getSource();
                AidList rowList = row1.getItem();
    
                if (!row1.isEmpty() && rowList != null) {
                    if (!ngoSelectedList.contains(rowList.getRowNum())) {
                        ngoSelectedList.add(rowList.getRowNum());
                        // change row colour to green when clicked on for the 1st time (select)
                        row1.setStyle("-fx-background-color:limegreen");
                    } 
                    else {
                        ngoSelectedList.remove(Integer.valueOf(rowList.getRowNum()));
                        // change green row colour to default colour when clicked on (deselect)
                        row1.setStyle("-fx-background-color: -fx-background");
                }
                event.consume();
                }
            }
        }       
      }

    /**
     * This function will change the Donor table row colour 
     * that users click on to green.
     * @param event mouse click action from user
     */
    private void donorTableRowMouseClickHandler(MouseEvent event) {
        if (rowselectable){
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 1) {
                @SuppressWarnings("unchecked")
                TableRow<AidList> row2 = (TableRow<AidList>) event.getSource();
                AidList rowList = row2.getItem();
    
                if (!row2.isEmpty() && rowList != null) {
                    if (!donorSelectedList.contains(rowList.getRowNum())) {
                        donorSelectedList.add(rowList.getRowNum());
                        row2.setStyle("-fx-background-color:limegreen");
                    } 
                    else {
                        donorSelectedList.remove(Integer.valueOf(rowList.getRowNum()));
                        row2.setStyle("-fx-background-color: -fx-background");
                    }
                    event.consume();
                    }
            }
        }
        
    }

    /**
     * Change scene to LoginPage.
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

    /**
     * Refresh the current scene.
     */
    private void refreshScene() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/DistributePage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////

    
    /**
     * Reset value for for selected lists
     */
    public void resetVar() {
        ngoSelectedList = new ArrayList();
        donorSelectedList = new ArrayList();
    }

}
