package BackstockSub;

import Entity.InventoryItem;
import Entity.States;
import ISMS.ISMSContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
* @author Paige Barnes
* CSIS 483-D02
* 04/21
 */
public class BackstockController implements Initializable {

    BackstockHelper backstockHelper = new BackstockHelper();
    ISMSContext context = new ISMSContext();

    public void setContext(ISMSContext context){
        this.context = context;
    }


    @FXML
    private TextField UPC;

    @FXML
    private Button searchButton;

    @FXML
    private Button resetButton;

    @FXML
    private RadioButton otherStateRadio;

    @FXML
    private RadioButton currentStateRadio;

    @FXML
    private ComboBox statesCombo;

    @FXML
    private TableView resultsTable = new TableView();

    @FXML
    private Label message;

    public void initialize(URL url, ResourceBundle rb){

        // Radio buttons, combobox, and table view set according to Oracle standards
        ObservableList<States> States = FXCollections.observableArrayList(Entity.States.values());
        statesCombo.setItems(States);
        statesCombo.setDisable(true);

        // https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm
        ToggleGroup rbuttons = new ToggleGroup();
        currentStateRadio.setToggleGroup(rbuttons);
        otherStateRadio.setToggleGroup(rbuttons);


        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
        resultsTable.setPlaceholder(new Label("No rows to display"));
        TableColumn UPC = new TableColumn("UPC");
        UPC.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("UPC"));

        TableColumn Size = new TableColumn("Size");
        Size.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("size"));

        TableColumn Color = new TableColumn("Color");
        Color.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("color"));

        TableColumn Quantity = new TableColumn("Quantity Available");
        Quantity.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("quantityOnHand"));

        TableColumn Store = new TableColumn("Store Name");
        Store.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("storeName"));

        TableColumn Phone = new TableColumn("Phone Number");
        Phone.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("phoneNumber"));

        TableColumn Zipcode = new TableColumn("Zipcode");
        Zipcode.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("zipCode"));

        resultsTable.getColumns().addAll(UPC, Size, Color, Quantity, Store, Phone, Zipcode);


    }


    @FXML
    public void enableCombo(ActionEvent event){
        statesCombo.setDisable(false);
    }

    @FXML
    public void disableCombo(ActionEvent event){
        statesCombo.setDisable(true);
    }

    @FXML
    public void checkBackstock(ActionEvent event) throws SQLException{
        String UPC = this.UPC.getText();
        int storeID = context.getCurrentStore().getStoreID();

        ObservableList<InventoryItem> itemResults =  FXCollections.observableArrayList();

        if(UPC.isEmpty()){
            message.setText("Please enter a UPC");
        }
        else if(!currentStateRadio.isSelected() && !otherStateRadio.isSelected()){
            message.setText("Please select a radio button");
        }
        else{
            if(currentStateRadio.isSelected()){

                itemResults = this.backstockHelper.getBackstock(UPC, storeID);
            }
            if(otherStateRadio.isSelected() && !statesCombo.getSelectionModel().isEmpty()){
                itemResults = this.backstockHelper.getBackstock(UPC, statesCombo.getValue().toString());
            }

            if(!itemResults.isEmpty()){
                resultsTable.setItems(itemResults);
            }
            else{
                Label placeholder = new Label("No results.");
                resultsTable.setPlaceholder(placeholder);
            }
        }

    }

    @FXML
    public void reset(ActionEvent event){
        Label placeholder = new Label("No rows to display.");
        resultsTable.setPlaceholder(placeholder);
        resultsTable.getItems().clear();
        resultsTable.setPlaceholder(placeholder);
        UPC.clear();
        statesCombo.setDisable(true);
        message.setText("");

    }


    }








