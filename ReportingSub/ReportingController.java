package ReportingSub;

import Entity.RefillItem;
import Entity.ReportItem;
import ISMS.ISMSContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 05/21
 */
public class ReportingController implements Initializable {

    ISMSContext context = new ISMSContext();
    public void setContext(ISMSContext context){
        this.context = context;
    }

    // sales trend report variables
    @FXML
    private DatePicker trendFrom;
    @FXML
    private DatePicker trendTo;
    @FXML
    private ChoiceBox trendGender = new ChoiceBox();
    @FXML
    private TableView<ReportItem> trendReport = new TableView<ReportItem>();
    @FXML
    private Label trendMsg;


    // refill report variables
    @FXML
    private TextField fullStack;
    @FXML
    private TextField UPC;
    @FXML
    private TextField numOnFloor;
    @FXML
    private TableView<RefillItem> refillReport = new TableView<RefillItem>();
    @FXML
    private Label refillMsg;

    // report visualization variables
    @FXML
    private RadioButton byCategory;
    @FXML
    private RadioButton bySeason;
    @FXML
    private ChoiceBox visualGender = new ChoiceBox();
    @FXML
    private DatePicker visualFrom;
    @FXML
    private DatePicker visualTo;

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number>  visualChart = new BarChart<String, Number>(xAxis, yAxis);
    @FXML
    private Label visualMsg;


    // general variables for genders
    private ObservableList<String> trendGenders = FXCollections.observableArrayList("MENS", "WOMENS", "");
    private ObservableList<String> visGenders = FXCollections.observableArrayList("MENS", "WOMENS", "ALL");

    private RefillHelper refillHelper = new RefillHelper();
    private SalesTrendHelper salesTrendHelper = new SalesTrendHelper();
    private VisualizationHelper visualizationHelper = new VisualizationHelper();
    private ReportPrintingHelper printingHelper = new ReportPrintingHelper();



    public void initialize(URL url, ResourceBundle rb) {
        // https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm

        // initialize sales trend variables
        trendGender.setItems(trendGenders);

        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
        trendReport.setPlaceholder(new Label("No rows to display"));
        TableColumn itemId = new TableColumn("ItemID");
        itemId.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("ItemId"));
        TableColumn storeId = new TableColumn("StoreID");
        storeId.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("storeId"));
        TableColumn trendUPC = new TableColumn("UPC");
        trendUPC.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("UPC"));
        TableColumn itemName = new TableColumn("ItemName");
        itemName.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("ItemName"));
        TableColumn size = new TableColumn("Size");
        size.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("size"));
        TableColumn color = new TableColumn("Color");
        color.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("color"));
        TableColumn quantityAvail = new TableColumn("QuantityAvail");
        quantityAvail.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("quantityOnHand"));
        TableColumn QuantitySold = new TableColumn("QuantitySold");
        QuantitySold.setCellValueFactory(new PropertyValueFactory<ReportItem, String>("QuantitySold"));
        trendReport.getColumns().addAll(itemId, storeId, trendUPC, itemName, size, color, quantityAvail, QuantitySold);

        // initialize refill report variables
        refillReport.setPlaceholder(new Label("No rows to display"));
        TableColumn refillUPC = new TableColumn("UPC");
        refillUPC.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("UPC"));
        TableColumn refillItemName = new TableColumn("ItemName");
        refillItemName.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("itemName"));
        TableColumn refillColor = new TableColumn("Color");
        refillColor.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("color"));
        TableColumn refillSize = new TableColumn("Size");
        refillSize.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("size"));
        TableColumn refillQuantity = new TableColumn("RefillQuantity");
        refillQuantity.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("refillQuantity"));
        TableColumn refillIsInStock = new TableColumn("RefillIsInStock");
        refillIsInStock.setCellValueFactory(new PropertyValueFactory<RefillItem, String>("refillInStock"));
        refillReport.getColumns().addAll(refillUPC, refillItemName, refillColor, refillSize, refillQuantity, refillIsInStock);

        // initialize report visualization variables
        ToggleGroup rbuttons = new ToggleGroup();
        byCategory.setToggleGroup(rbuttons);
        bySeason.setToggleGroup(rbuttons);
        visualGender.setItems(visGenders);


    }

    // sales trend methods
    @FXML
    public void getSalesTrendReport(ActionEvent event) throws SQLException {
        trendReport.getItems().clear();
        trendMsg.setText("");
        ObservableList<ReportItem> trendItems = FXCollections.observableArrayList();
        int currentStoreId = this.context.getCurrentStore().getStoreID();

        if(trendFrom.getValue() != null && trendTo.getValue() != null){

            Date fromDate = Date.valueOf(trendFrom.getValue());
            Date toDate = Date.valueOf(trendTo.getValue());

            if(!trendGender.getSelectionModel().isEmpty() && trendGender.getValue().toString() != ""){
                String gender = trendGender.getValue().toString();
                trendItems = this.salesTrendHelper.getBestsellingItemsByGender(fromDate, toDate, gender, currentStoreId);
            }
            else{
                trendItems = this.salesTrendHelper.getBestsellingItemsByDate(fromDate, toDate, currentStoreId);
            }

        }
        else{
            trendMsg.setText("Select a valid date range");
        }

        if(!trendItems.isEmpty()){
            trendReport.setItems(trendItems);
        }
        else{
            Label placeholder = new Label("No results.");
            trendReport.setPlaceholder(placeholder);
        }

    }
    @FXML
    public void printTrendReport(ActionEvent event){
        if(!trendReport.getItems().isEmpty()){
            this.printingHelper.createReportFile(trendReport.getItems(), "Trend Report File");
        }
        else{
            trendMsg.setText("Create a report to print.");
        }
    }

    // refill methods
    @FXML
    public void addToRefillReport(ActionEvent event) throws SQLException {
        if(!this.fullStack.getText().isEmpty() && !this.UPC.getText().isEmpty() && !this.numOnFloor.getText().isEmpty()){

            int fullStack = Integer.parseInt(this.fullStack.getText());
            String UPC = this.UPC.getText();
            int numOnFloor = Integer.parseInt(this.numOnFloor.getText());

            this.refillHelper.addToRefillReport(fullStack, UPC, numOnFloor);
            refillMsg.setText("Item added to report");
        }
        else{
            refillMsg.setText("Enter full stack, UPC, and number on floor");
        }
    }

    @FXML
    public void getRefillReport(ActionEvent event){
        if(!this.refillHelper.getRefillReport().isEmpty()){
            refillReport.setItems(this.refillHelper.getRefillReport());
        }
        else{
            Label placeholder = new Label("No results.");
            refillReport.setPlaceholder(placeholder);
        }

    }

    @FXML
    public void resetRefillReport(ActionEvent event){

        this.refillHelper.resetRefillReport();
        refillMsg.setText("");
    }

    @FXML
    public void printRefillReport(ActionEvent event){

        if (!refillReport.getItems().isEmpty()){
            this.printingHelper.createRefillFile(refillReport.getItems(), "Refill Report");
        }
        else{
            refillMsg.setText("Create a report to print.");
        }
    }

    // report visualization methods
    @FXML
    public void getReportVisualization(ActionEvent event) throws SQLException {

        visualChart.getData().clear();
        int currentStoreId = this.context.getCurrentStore().getStoreID();
        if(!byCategory.isSelected() && !bySeason.isSelected()){
            visualMsg.setText("Please select a radio button");
        }
        else if(visualGender.getSelectionModel().isEmpty()){
            visualMsg.setText("Please select a gender");
        }
        else if(visualFrom.getValue() == null || visualTo.getValue() == null) {
            visualMsg.setText("Please enter a date range");
        }
        else{
            Date fromDate = Date.valueOf(visualFrom.getValue());
            Date toDate = Date.valueOf(visualTo.getValue());
            String gender = visualGender.getValue().toString();
            if(byCategory.isSelected()){
                ArrayList<ReportItem> reportItems = this.visualizationHelper.getItemsByCategory(fromDate, toDate, gender, currentStoreId);
                visualChart.setTitle("Sales Trend by Category");
                xAxis.setLabel("Category");
                yAxis.setLabel("Sales");

                // http://tutorials.jenkov.com/javafx/barchart.html

                for(ReportItem item : reportItems) {
                    XYChart.Series dataSeries = new XYChart.Series();
                    dataSeries.setName(item.getCategory());
                    dataSeries.getData().add(new XYChart.Data(item.getCategory(), item.getQuantitySold()));
                    visualChart.getData().add(dataSeries);
                }
                visualChart.setData(visualChart.getData());


            }
            else if(bySeason.isSelected()){
                this.visualizationHelper.getItemsBySeason(fromDate, toDate, gender, currentStoreId);
                ArrayList<ReportItem> reportItems = this.visualizationHelper.getItemsBySeason(fromDate, toDate, gender, currentStoreId);
                visualChart.setTitle("Sales Trend by Season");
                xAxis.setLabel("Season");
                yAxis.setLabel("Sales");

                // http://tutorials.jenkov.com/javafx/barchart.html
                XYChart.Series dataSeries = new XYChart.Series();
                for(ReportItem item : reportItems){
                    dataSeries.getData().add(new XYChart.Data(item.getSeason(), item.getQuantitySold()));
                }
                visualChart.getData().add(dataSeries);
                visualChart.setData(visualChart.getData());

            }

        }

    }

    @FXML
    public void printVisualReport(ActionEvent event) throws IOException {
        if(!visualChart.getData().isEmpty()){
            this.printingHelper.createBarChartFile(visualChart, "Visual Bar Chart");
        }
        else{
            visualMsg.setText("Create a report to print");
        }
    }



}
