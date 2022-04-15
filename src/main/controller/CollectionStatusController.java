package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.AidList;
import main.model.Database;
import main.model.Donor;
import main.model.Ngo;
import main.model.GlobalState;

/**
 * This controller will handle the user interaction logic for CollectionStatus.fxml
 */
public class CollectionStatusController implements Initializable{

    //////////////// start of JavaFX Components Variables ///////////////////

    @FXML
    private TableView<AidList> statusTable;
    @FXML
    private TableColumn<AidList, String> donorCol;
    @FXML
    private TableColumn<AidList, String> phoneCol;
    @FXML
    private TableColumn<AidList,String> aidsCol;
    @FXML
    private TableColumn<AidList,Integer> qtyCol;
    @FXML
    private TableColumn<AidList,String> ngoCol;
    @FXML
    private TableColumn<AidList,Integer> manpowerCol;
    @FXML
    private TableColumn<AidList,String> statusCol;
    @FXML
    private Button backBtn;

    //////////////// end of JavaFX Components Variables ///////////////////

    private GlobalState state = GlobalState.getInstance();
    private String identity = state.getIdentity();

    /**
     * Show the donate table and donor profile information when DonatePage is initialized. Method from JavaFx.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCollectionStatusTable();
    }

    /**
     * To load the collection status table.
     */
    @FXML
    private void loadCollectionStatusTable() { 
        ObservableList<AidList> collectionStatus = FXCollections.observableArrayList();

        donorCol.setCellValueFactory(new PropertyValueFactory<AidList, String>("donor"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<AidList, String>("phone"));
        aidsCol.setCellValueFactory(new PropertyValueFactory<AidList, String>("aids"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("quantity"));
        ngoCol.setCellValueFactory(new PropertyValueFactory<AidList, String>("ngo"));
        manpowerCol.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("manpower"));
        statusCol.setCellValueFactory(new PropertyValueFactory<AidList, String>("status"));

        List<List<String>> DataList = Database.readData("distributed_Info");

        if (identity == "Donor"){
            Donor donorUserInfo = state.getDonorSession();
            if (DataList != null) {
                for (List<String> Data: DataList) {
                    if(Data.get(0).equals(donorUserInfo.getName())){
                    AidList info = new AidList(Data.get(0), 
                                                Data.get(1), 
                                                Data.get(2), 
                                                Integer.parseInt(Data.get(3)), 
                                                Data.get(4), 
                                                Integer.parseInt(Data.get(5)),
                                                Data.get(6));
                    collectionStatus.add(info);
                    }
                }
            }

        }
        else if (identity == "NGO"){
            Ngo ngoUserInfo = state.getNgoSession();
            if (DataList != null) {
                for (List<String> Data: DataList) {
                    if(Data.get(4).equals(ngoUserInfo.getName())){
                    AidList info = new AidList(Data.get(0), 
                                                Data.get(1), 
                                                Data.get(2), 
                                                Integer.parseInt(Data.get(3)), 
                                                Data.get(4), 
                                                Integer.parseInt(Data.get(5)),
                                                Data.get(6));
                    collectionStatus.add(info);
                
                    }
                }
            }

        }

        statusTable.setItems(collectionStatus);
    }

    /**
     * Switch to the previous page according to current Login Session Identity.
     */
    @FXML
    private void switch_to_backPage(){
        String viewPath = "";
        if (identity == "Donor")
            viewPath = "/main/view/DonatePage.fxml";
        else if (identity == "NGO")
            viewPath = "/main/view/RequestPage.fxml";

        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }        
    }

}
