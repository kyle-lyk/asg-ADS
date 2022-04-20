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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.Database;
import main.model.GlobalState;
import main.model.Router;
import main.model.tablemodel.AidList;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This controller will handle the user interaction logic for DCHistoryPage.fxml 
 */
public class DCHistoryPageController implements Initializable{

    //////////////// start of JavaFX Components Variables ///////////////////

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

    //////////////// end of JavaFX Components Variables ///////////////////

    private GlobalState state = GlobalState.getInstance();

    /**
     * Populates MatchedAidHistory table when DCHistoryPage is initialized (JavaFX method).
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // load data from CSV and populates table
        updateMatchedAidHistory();

    }

    /**
     * When button is clicked, scene will be
     * changed to DistributePage.
     * @param event mouse click action from user
     */
    @FXML
    private void switch_to_DistributePage(ActionEvent event) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.DistributePage()));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));

            DistributePageController dcItemController = loader.getController();
            dcItemController.resetFlag();        

        } catch (IOException ioe){
            ioe.printStackTrace();
            }
    }

    /**
     * When button is clicked, scene will be 
     * changed to Collection Page.
     * @param event mouse click action from user
     */
    @FXML
    void switch_to_DCCollectionPage(ActionEvent event) {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Router.DCCollectionPage()));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, 1280, 720));
        } catch (IOException ioe){
            ioe.printStackTrace();
            }
    }

    /**
     * When button is clicked, an alert window will pop up
     * to confirm log out process.
     * @param event mouse click action from user
     */
    @FXML
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

    /**
     * Changes the content of the text component.
     * @param newString new text
     */
    @FXML
    public void showGeneralText(String newString) {
        tGeneralText3.setText(newString);;
    }

    /**
     * Updates and populates the MatchedAidHistory table
     */
    private void updateMatchedAidHistory() { 
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

    /**
     * Change scene to LoginPage.
     */
    private void switch_to_LoginPage() {
        try{
            Stage mainStage = GlobalState.getInstance().getStage();
            Parent root = FXMLLoader.load(getClass().getResource(Router.LoginPage()));
            mainStage.setScene(new Scene(root, 1280, 720));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
