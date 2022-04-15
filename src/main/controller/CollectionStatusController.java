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
import main.model.GlobalState;

public class CollectionStatusController implements Initializable{
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

    private GlobalState state = GlobalState.getInstance();
    private Donor donorUserInfo = state.getDonorSession();
    private String identity = donorUserInfo.getIdentity();
    private String username = donorUserInfo.getUsername();


    String DataFileName = state.getDataFileName(identity);
    List<List<String>> Acc_Info = Database.readData(DataFileName);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCollectionStatusTable();
    }

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
        if (DataList != null) {
            for (List<String> Data: DataList) {
                for(int i=0; i < Acc_Info.size(); i++){
                    System.out.println(Acc_Info.get(i).get(2));
                    //System.out.println(Data.get(0));
                    //System.out.println(donorUserInfo.getUsername());
                    if(Data.get(0).equals(Acc_Info.get(i).get(2))){
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

    public void switchToDonatePage(){
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/DonatePage.fxml"));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }        
    }
}
