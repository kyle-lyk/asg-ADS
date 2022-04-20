package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.model.AidList;
import main.model.Database;
import main.model.GlobalState;
import main.model.Fifo;
import main.model.Priority;

/**
 * This controller will handle the user interaction logic for CollectionPage.fxml 
 */
public class CollectionPageController implements Initializable {

    //////////////// start of JavaFX Components Variables ///////////////////

    @FXML
    private Text generalText;

    @FXML
    private ComboBox queueChoice;

    @FXML
    private Text queueText;

    @FXML
    private TextField ngoNameField;

    @FXML
    private TableColumn<AidList, String> donorColumn;

    @FXML
    private TableColumn<AidList, String> phoneColumn;

    @FXML
    private TableColumn<AidList, String> aidColumn;

    @FXML
    private TableColumn<AidList, Integer> quantityColumn;

    @FXML
    private TableColumn<AidList, Integer> manpowerColumn;

    @FXML
    private TableColumn<AidList, String> ngoColumn;

    @FXML
    private TableColumn<AidList, String> statusColumn;

    @FXML
    private Text textErrorMsg;

    @FXML
    private TableView<AidList> tableViewRecords;

    //////////////// end of JavaFX Components Variables ///////////////////

    private GlobalState state = GlobalState.getInstance();
    ObservableList<String> queueChoiceList = FXCollections.observableArrayList("FIFO Queue", "Priority Queue");

    boolean fifoFlag = false;
    boolean priorityFlag = false;

    Fifo fifoQ = new Fifo(); 
    Priority priorityQ = new Priority();

    LinkedHashSet<String> ngoExistListUnique = new LinkedHashSet<String>(); // unique NGOs that have "Reserved" as status
    ArrayList<String> ngoExistList = new ArrayList<String>(); // unique NGOs that have "Reserved" as status, but as ArrayList
    ArrayList<Integer> manpowerList = new ArrayList<Integer>(); // manpower amount of unique NGOs that have "Reserved" as status

    /**
     * Populates MatchedAidHistory table when DCHistoryPage is initialized (JavaFX method).
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetVar();
        queueChoice.setItems(queueChoiceList);
        queueChoice.setPromptText("Please Select");
        queueChoiceListener();

        updateRecordsTable();
        rowSelection();
    }

    /**
     * When button is clicked, NGO name typed inside
     * text field will be inserted into queue.
     * @param event mouse click action from user
     */
    @FXML
    void enqueueBtn(ActionEvent event) {
        if (!fifoFlag && !priorityFlag) {
            textErrorMsg.setText("You must select a mode of queueing first!");
        }
        else if (ngoExistList.stream().anyMatch(ngoNameField.getText()::equalsIgnoreCase)) {
            textErrorMsg.setText("");
            String ngoName = ngoNameField.getText().toString().substring(0, 1).toUpperCase() + ngoNameField.getText().toString().substring(1);

            if (fifoFlag) {
                fifoQ.enqueue(ngoName);
                queueText.setText(fifoQ.getNgoList().toString());
                
            }
            else if (priorityFlag) {
                int manpowerIndex = ngoExistList.indexOf(ngoName);
                int manpowerAmount = manpowerList.get(manpowerIndex);
                priorityQ.enqueue(ngoName, manpowerAmount);
                queueText.setText(priorityQ.getNgoList());
            }   
        }
        else {
            textErrorMsg.setText("You can only queue NGOs that exist and has 'Reserved' status!");
        }
    }

    /**
     * When button is clicked, dequeue process will occur
     * based on the mode of queue selected.
     * @param event mouse click action from user
     */
    @FXML
    void dequeueBtn(ActionEvent event) {

        if (!fifoFlag && !priorityFlag) {
            textErrorMsg.setText("You must select a mode of queueing first!");
        }
        else if (!fifoQ.getNgoList().isEmpty() || !(priorityQ.getNgoList().equals("[]"))) {
            textErrorMsg.setText("");
            if (fifoFlag) {
                String dequeuedNGO = fifoQ.dequeuePoll();
                System.out.println(dequeuedNGO); //////////////
                updateCollectionStatus(dequeuedNGO);
                queueText.setText(fifoQ.getNgoList().toString());

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // The below code returns a stacktrace error (Exception in thread "JavaFX Application Thread" java.lang.RuntimeException)
                // but program still runs in expected/predictable manner
                updateRecordsTable();
                rowSelection();
            }
            else if (priorityFlag) {
                String dequeuedNGO = priorityQ.dequeuePoll();
                System.out.println(dequeuedNGO); //////////////
                updateCollectionStatus(dequeuedNGO);
                queueText.setText(priorityQ.getNgoList());

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // The below code returns a stacktrace error (Exception in thread "JavaFX Application Thread" java.lang.RuntimeException)
                // but program still runs in expected/predictable manner
                updateRecordsTable();
                rowSelection();
            }
        }
        else {
            textErrorMsg.setText("Queue is empty!");
        }
    }

    /**
     * When button is clicked, an alert window will pop up
     * to confirm log out process.
     * @param event mouse click action from user
     */
    @FXML
    void logoutBtn(ActionEvent event) {
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
     * When button is clicked, scene will change
     * to Distribute Page.
     * @param event mouse click action from user
     */
    @FXML
    private void DCPageBtn(ActionEvent event) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/DistributePage.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));

            DistributePageController dcItemController = loader.getController();
            dcItemController.resetFlag();        

        } catch (IOException ioe){
            ioe.printStackTrace();
            }
    }

    /**
     * Adds a listener to ComboBox to keep track of any selection changes.
     */
    @SuppressWarnings("unchecked")
    private void queueChoiceListener() {
        queueChoice.setOnAction((event) -> {
            System.out.println("choice: " + queueChoice.getValue());
            queueText.setText("[]");
            if (((String)queueChoice.getValue()).equals("FIFO Queue")) {
                fifoFlag = true;
                priorityFlag = false;
                fifoQ = new Fifo(); 
                System.out.println(fifoFlag);
                textErrorMsg.setText("");
            }
            else if (((String)queueChoice.getValue()).equals("Priority Queue")) {
                priorityFlag = true;
                fifoFlag = false;
                priorityQ = new Priority();
                System.out.println(priorityFlag);
                textErrorMsg.setText("");
            }
        });
    }

    /**
     * Updates the status of the distributed aid to "Collected".
     * @param ngoName NGO's name
     */
    private void updateCollectionStatus(String ngoName) {

        List<List<String>> distDataList = Database.readData("distributed_Info");
        List<List<String>> updatedList = new ArrayList<List<String>>();
        if (distDataList != null) {
            for (List<String> Data: distDataList) {
                if (Data.get(4).toString().equals(ngoName)) {
                    Data.set(6, "Collected");
                }
                updatedList.add(Data);
            }
            Database.updateData("distributed_Info", updatedList);
        }
    }

    /**
     * Updates and populates the Records table based on the relevant CSV file.
     */
    private void updateRecordsTable() { 
        ngoExistListUnique = new LinkedHashSet<String>();
        ngoExistList = new ArrayList<String>();

        ObservableList<AidList> recordsList = FXCollections.observableArrayList();
        tableViewRecords.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        donorColumn.setCellValueFactory(new PropertyValueFactory<AidList, String>("donor"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<AidList, String>("phone"));
        aidColumn.setCellValueFactory(new PropertyValueFactory<AidList, String>("aids"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("quantity"));
        ngoColumn.setCellValueFactory(new PropertyValueFactory<AidList, String>("ngo"));
        manpowerColumn.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("manpower"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<AidList, String>("status"));

        List<List<String>> distDataList = Database.readData("distributed_Info");
        if (distDataList != null) {
            for (List<String> Data: distDataList) {
                AidList info = new AidList(Data.get(0), Data.get(1), Data.get(2), Integer.parseInt(Data.get(3)), Data.get(4), Integer.parseInt(Data.get(5)), Data.get(6));
                recordsList.add(info);
            
                if (Data.get(6).toString().equals("Reserved")) {
                    if (ngoExistListUnique.add(Data.get(4))) { // if NGO can be added into a LinkedHashSet (ie. unique), then add it into ArrayList as well
                        ngoExistList.add(Data.get(4));
                        manpowerList.add(Integer.valueOf(Data.get(5)));
                    }  
                    /////// debugging
                    System.out.println(String.join(", ", ngoExistList));
                    String listString = manpowerList.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                    System.out.println(listString);
                    /////// debugging
                }
            }
        }

        List<List<String>> donateDataList = Database.readData("donated_Info");
        if (donateDataList != null) {
            for (List<String> Data: donateDataList) {
                if (Data.get(7).isBlank()) {
                    AidList info = new AidList(Data.get(2), Data.get(3), Data.get(4), Integer.parseInt(Data.get(5)), "-", 0, "Available");
                    recordsList.add(info);
                }
            }
        }

        tableViewRecords.setItems(recordsList);
    }

    /**
     * Sets up the the table view selection model and listener.
     */
    private void rowSelection() {
        TableViewSelectionModel<AidList> selectionModel = tableViewRecords.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<AidList> selectedList = selectionModel.getSelectedItems();

        selectedList.addListener(
            new ListChangeListener<AidList>() {
                @Override
                public void onChanged(Change<? extends AidList> arg0) {
                    ngoNameField.setText(tableViewRecords.getSelectionModel().getSelectedItems().get(0).getNgo());
                    }
            });
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
     * Reset the values of queues and lists.
     */
    private void resetVar() {
        fifoQ = new Fifo();
        priorityQ = new Priority();
        ngoExistListUnique = new LinkedHashSet<String>();
        ngoExistList = new ArrayList<String>();
    }

    /**
     * Refresh the current scene.
     */
    private void refreshScene() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/CollectionSimulation.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
