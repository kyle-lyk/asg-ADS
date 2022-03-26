package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

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


    boolean itemFilterExistNgo = false;
    boolean itemFilterExistDonor = false;
    
    boolean rowselectable = false;

    ArrayList<String> donorUUIDList = new ArrayList<String>();
    ArrayList<String> ngoUUIDList = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // load test data from csv
        updateNGOTable();
        updateDonorTable();

        ngoPerRowEventHandler();
        donorPerRowEventHandler();
    }

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
                    if (Title_Cased_filterItem.isBlank() || Title_Cased_filterItem.equals(Data.get(4))) {
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

    // Method Overloading
    private void updateNGOTable(){
        updateNGOTable("");
    }
    
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
                    if (Title_Cased_filterItem.isBlank() || Title_Cased_filterItem.equals(Data.get(4))) {
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

    // Method Overloading
    private void updateDonorTable(){
        updateDonorTable("");
    }

    ///////////////// ///////////////////


    @FXML
    void btnAidHistoryClicked3(ActionEvent event) {
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
    void filterItem(ActionEvent event) {
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
    void showAll(ActionEvent event) {
        updateDonorTable();
        updateNGOTable();
        tErrorMsg3.setText("");
        rowselectable = false;
    }

    @FXML
    void matchAids(ActionEvent event) {
        // System.out.println("ngoSelectedList: " + ngoSelectedList);
        // System.out.println("donorSelectedList: " + donorSelectedList);
        // System.out.println("ngoUUIDList: " + ngoUUIDList.get( ngoSelectedList.get(0) - 1 ));
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
                    dcHistoryController.tableTitleName("           Result");
                    dcHistoryController.showGeneralText("Aid matched successfully!");
        
                } catch (IOException ioe){
                    ioe.printStackTrace();
                    }
                // output and update to all 3 database (donatedInfo, requestedInfo, distributedInfo)

                // switch back to original scene
                updateNGOTable();
                updateDonorTable();

            }
        }
    }

    private boolean associationFlag() {
        boolean isMatch = true; // set to true for testing, default will be false

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

        // Add selected donate row into a list
        for (int i=0; i < selected_donorUUIDList.size(); i++) {
            for (int j=0; j < donatedList.size(); j++) {
                if (selected_donorUUIDList.get(i).equals(donatedList.get(j).get(0))) {
                    selected_donatedList.add(donatedList.get(j));
                }
            }
        }


        // Add selected request row into a list
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
            System.out.println("One-to-one"); // pass model function here

            DonateInfo donateInfo = loadDonateInfo(selected_donatedList,0);
            RequestInfo requestInfo = loadRequestInfo(selected_requestedList,0);

            DistAids distAids = new DistAids(donateInfo, requestInfo);
            // System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
            // System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
            distAids.matchAids();
            // System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
            // System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());

        }
        else if ((donorSelectedList.size()) == 1 && ((ngoSelectedList.size()) >= 1)) {
            // 6 // 2 3 -> /
            // 2 // 1 3 -> /
            // 2 // 3 1 -> X 
            System.out.println("One-to-many"); // pass model function here
            DonateInfo donateInfo = loadDonateInfo(selected_donatedList,0);
            if (donateInfo.getRemainQty() <= (loadRequestInfo(selected_requestedList,0)).getRemainQty()){
                System.out.println("Illegal one to many");
            }
            else{
                for (int j=0; j < selected_requestedList.size(); j++) {
                    RequestInfo requestInfo = loadRequestInfo(selected_requestedList,j);
    
                    DistAids distAids = new DistAids(donateInfo, requestInfo);
                    System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                    distAids.matchAids();
                    System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                }
            }

        }
        else if ((donorSelectedList.size()) >= 1 && ((ngoSelectedList.size()) == 1)) {
            // 2 3 // 6 -> /
            // 1 3 // 2 -> /
            // 3 1 // 2 -> X 
            System.out.println("Many-to-one"); // pass model function here

            RequestInfo requestInfo = loadRequestInfo(selected_requestedList,0);
            if (requestInfo.getRemainQty() <= (loadDonateInfo(selected_donatedList,0)).getRemainQty()){
                System.out.println("Illegal many to one");
            }
            else{
                for (int j=0; j < selected_donatedList.size(); j++) {
                    DonateInfo donateInfo = loadDonateInfo(selected_donatedList,j);
    
                    DistAids distAids = new DistAids(donateInfo, requestInfo);
                    System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                    distAids.matchAids();
                    System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                }
            }

        }
        else if ((donorSelectedList.size()) >= 1 && ((ngoSelectedList.size()) >= 1)) {
            System.out.println("Many-to-many"); // pass model function here
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
            }
            else{
                int i = 0;
                int j = 0;
                RequestInfo requestInfo = loadRequestInfo(selected_requestedList,i);
                DonateInfo donateInfo = loadDonateInfo(selected_donatedList,j);
                while(i < selected_requestedList.size() && j < selected_donatedList.size()){
                    DistAids distAids = new DistAids(donateInfo, requestInfo);
                    System.out.println("pre-donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("pre-requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                    distAids.matchAids();
                    System.out.println("donateRemainQty: " + distAids.getDonateInfo().getRemainQty());
                    System.out.println("requestRemainQty: " + distAids.getRequestInfo().getRemainQty());
                    if (distAids.getDonateInfo().getRemainQty() == 0){
                        j++;
                        if (j < selected_donatedList.size()){
                            donateInfo = loadDonateInfo(selected_donatedList,j);
                        }
                    }
                    else if (distAids.getRequestInfo().getRemainQty() == 0){
                        i++;
                        if (i < selected_requestedList.size()){
                            requestInfo = loadRequestInfo(selected_requestedList,i);
                        }
                    }
                    else{
                        j++;
                        if (j < selected_donatedList.size()){
                            donateInfo = loadDonateInfo(selected_donatedList,j);
                        }
                    }
                    
                }
            }
        }

        return isMatch;
    }

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


    @FXML
    void tableDonorSelected(MouseEvent event) {

    }

    @FXML
    void tableNGOSelected(MouseEvent event) {

    }

    public void resetFlag() {
        itemFilterExistNgo = false;
        itemFilterExistDonor = false;
    }

    ////////////////////////////////////////////////////////////////
    ArrayList<Integer> ngoSelectedList = new ArrayList();
    ArrayList<Integer> donorSelectedList = new ArrayList();

    // handles row factory for NGO table
    private void ngoPerRowEventHandler() {
        EventHandler<MouseEvent> onClick = this::ngoTableRowMouseClickHandler;

        tvMainNGO.setRowFactory(param -> {
            TableRow<AidList> row1 = new TableRow<>();
            row1.setOnMouseClicked(onClick);
            return row1;
        });    
    }

    // handles row factory for Donor table
    private void donorPerRowEventHandler() {
        EventHandler<MouseEvent> onClick = this::donorTableRowMouseClickHandler;

        tvMainDonor.setRowFactory(param -> {
            TableRow<AidList> row2 = new TableRow<>();
            row2.setOnMouseClicked(onClick);
            return row2;
        });
    }

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
                
                // debugging
                ListIterator<Integer> listItr = ngoSelectedList.listIterator();
                while(listItr.hasNext()) {
                    Integer ele = listItr.next();
                    System.out.print(ele + " ");
                    }
                System.out.println();
                // debugging
    
                event.consume();
                }
            }
        }       
      }

    private void donorTableRowMouseClickHandler(MouseEvent event) {
        if (rowselectable){
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 1) {
                @SuppressWarnings("unchecked")
                TableRow<AidList> row2 = (TableRow<AidList>) event.getSource();
                AidList rowList = row2.getItem();
    
                if (!row2.isEmpty() && rowList != null) {
                    if (!donorSelectedList.contains(rowList.getRowNum())) {
                        //selectedNgoRows.add([[]]);
                        donorSelectedList.add(rowList.getRowNum());
                        // list.get(2).get(0);
                        // [rowList.getDonor(), rowList.getManpower(), rowList.getAid(), rowList.getQuantity()]
                        row2.setStyle("-fx-background-color:limegreen");
                    } 
                    else {
                        donorSelectedList.remove(Integer.valueOf(rowList.getRowNum()));
                        row2.setStyle("-fx-background-color: -fx-background");
                    }
                    
                    // debugging
                    ListIterator<Integer> listItr = donorSelectedList.listIterator();
                    while(listItr.hasNext()) {
                        Integer ele = listItr.next();
                        System.out.print(ele + " ");
                        }
                    System.out.println();
                    // debugging
        
                    event.consume();
                    }
            }
        }
        
    }

    ////////////////////////////////////////////////////////////////

    

    public void resetVar() {
        ngoSelectedList = new ArrayList();
        donorSelectedList = new ArrayList();
        // filterItem = "";
        // itemFilterExist = false;
        // selectedNgoRows = new ArrayList<List<String>>();
        // selectedDonorRows = new ArrayList<List<String>>();
    }

}
