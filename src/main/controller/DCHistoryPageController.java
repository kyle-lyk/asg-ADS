package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.Database;
import main.model.GlobalState;
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
import java.util.ResourceBundle;

public class DCHistoryPageController implements Initializable{

    @FXML
    private TableColumn<AidList, String> aidColumn5;

    @FXML
    private TableColumn<AidList, String> donorColumn5;

    @FXML
    private TableColumn<AidList, Integer> manpowerColumn5;

    @FXML
    private TableColumn<AidList, String> ngoColumn5;

    @FXML
    private TableColumn<AidList, String> phoneColumn5;

    @FXML
    private TableColumn<AidList, Integer> quantityColumn5;

    @FXML
    private Text tGeneralText3;

    @FXML
    private Text tErrorMsg3;

    @FXML
    private Text tSelectedDonor3;

    @FXML
    private Text tSelectedNGO3;

    @FXML
    private Text tTableName3;

    @FXML
    private Button btnNGODonorClicked3;

    @FXML
    private TableView<AidList> tvMainAidHistory;

    @FXML
    void btnNGODonorClicked3(ActionEvent event) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/DistributePage.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));

            DistributePageController dcItemController = loader.getController();
            dcItemController.resetFlag();

            // get instance of controller
            // DCEnterItemController dcItemController = loader.getController();         

        } catch (IOException ioe){
            ioe.printStackTrace();
            }
    }

    public void btnNameChange(String newName) {
        btnNGODonorClicked3.setText(newName);
    }

    public void tableTitleName(String newName) {
        tTableName3.setText(newName);
    }

    public void showGeneralText(String newString) {
        tGeneralText3.setText(newString);;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // load test data from csv
        updateMatchedAidHistory();

    }

    public void updateMatchedAidHistory() {
        ObservableList<AidList> matchedAidHistoryList = FXCollections.observableArrayList();

        donorColumn5.setCellValueFactory(new PropertyValueFactory<AidList, String>("donor"));
        phoneColumn5.setCellValueFactory(new PropertyValueFactory<AidList, String>("phone"));
        aidColumn5.setCellValueFactory(new PropertyValueFactory<AidList, String>("aids"));
        quantityColumn5.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("quantity"));
        ngoColumn5.setCellValueFactory(new PropertyValueFactory<AidList, String>("ngo"));
        manpowerColumn5.setCellValueFactory(new PropertyValueFactory<AidList, Integer>("manpower"));

        List<List<String>> DataList = Database.readData("distributed_Info");
        if (DataList != null) {
            for (List<String> Data: DataList) {
                AidList info = new AidList(Data.get(0), Data.get(1), Data.get(2), Integer.parseInt(Data.get(3)), Data.get(4), Integer.parseInt(Data.get(5)));
                matchedAidHistoryList.add(info);
            }
        }

        tvMainAidHistory.setItems(matchedAidHistoryList);
    }

}
