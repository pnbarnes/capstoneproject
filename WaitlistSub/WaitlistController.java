package WaitlistSub;

import Entity.Customer;
import Entity.Employee;
import Entity.Finder;
import Entity.WaitlistItem;
import ISMS.ISMSContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/25
 */
public class WaitlistController implements Initializable {

    Finder Finder = new Finder();
    ISMSContext context = new ISMSContext();
    WaitlistHelper waitlistHelper = new WaitlistHelper();

    public void setContext(ISMSContext context){
        this.context = context;
    }

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField rewardsNum;

    @FXML
    private TextField UPC;

    @FXML
    private TextField quantityDesired;

    @FXML
    private Label rewardsMsg;

    @FXML
    private Label addMsg;

    @FXML
    private TableView<WaitlistItem> waitlistResults = new TableView<WaitlistItem>();

    @FXML
    private RadioButton findByCustomer;

    @FXML
    private RadioButton findByUPC;

    @FXML
    private RadioButton findByDate;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private TextField searchUPC;

    @FXML
    private TextField searchCustomerLast;

    @FXML
    private TextField searchCustomerPhone;

    @FXML
    private Label searchMsg;







    public void initialize(URL url, ResourceBundle rb){
        // https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm
        ToggleGroup rbuttons = new ToggleGroup();
        findByCustomer.setToggleGroup(rbuttons);
        findByUPC.setToggleGroup(rbuttons);
        findByDate.setToggleGroup(rbuttons);

        searchCustomerLast.setDisable(true);
        searchCustomerPhone.setDisable(true);
        fromDate.setDisable(true);
        toDate.setDisable(true);
        searchUPC.setDisable(true);


        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
        waitlistResults.setPlaceholder(new Label("No rows to display"));

        TableColumn UPC = new TableColumn("UPC");
        UPC.setCellValueFactory(new PropertyValueFactory<WaitlistItem, String>("UPC"));

        TableColumn Size = new TableColumn("Size");
        Size.setCellValueFactory(new PropertyValueFactory<WaitlistItem, String>("size"));

        TableColumn Color = new TableColumn("Color");
        Color.setCellValueFactory(new PropertyValueFactory<WaitlistItem, String>("color"));

        TableColumn QuantityAvail = new TableColumn("Quantity Available");
        QuantityAvail.setCellValueFactory(new PropertyValueFactory<WaitlistItem, Integer>("quantityOnHand"));

        TableColumn ItemName = new TableColumn("Item Name");
        ItemName.setCellValueFactory(new PropertyValueFactory<WaitlistItem, String>("itemName"));

        TableColumn QuantityRequested = new TableColumn("Quantity Requested");
        QuantityRequested.setCellValueFactory(new PropertyValueFactory<WaitlistItem, Integer>("desiredQuantity"));

        TableColumn EmployeeID = new TableColumn("Employee ID");
        EmployeeID.setCellValueFactory(new PropertyValueFactory<WaitlistItem, Integer>("employeeId"));

        TableColumn DateAdded = new TableColumn("Date Added");
        DateAdded.setCellValueFactory(new PropertyValueFactory<WaitlistItem, Date>("waitlistStart"));

        TableColumn WaitlistID = new TableColumn("Waitlist Item ID");
        WaitlistID.setCellValueFactory(new PropertyValueFactory<WaitlistItem, Integer>("waitlistItemID"));


        waitlistResults.getColumns().addAll(DateAdded, UPC, ItemName, Size, Color, QuantityRequested, QuantityAvail, EmployeeID, WaitlistID);


    }

    @FXML
    public void populateCustomer(ActionEvent event) throws SQLException {
        String rewardsNum = this.rewardsNum.getText();

        if(rewardsNum.isEmpty()){
            rewardsMsg.setText("Please enter a valid rewards number");
        }
        else {
            Customer customer = Finder.customerFinder(rewardsNum);

            if (customer != null){

                firstName.setText(customer.getFirstName());
                lastName.setText(customer.getLastName());
                phoneNum.setText(customer.getPhoneNumber());
                emailAddress.setText(customer.getEmailAddress());

            }
            else{
                rewardsMsg.setText("Please enter a valid rewards number");
            }
        }
    }

    @FXML
    public void addToWaitlist(ActionEvent event) throws SQLException {
        Customer customer = new Customer();
        WaitlistItem item = new WaitlistItem();
        Employee employee = this.context.getLoggedInEmployee();

        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String phoneNum = this.phoneNum.getText();
        String email = this.emailAddress.getText();
        String UPC = this.UPC.getText();


        if(!this.quantityDesired.getText().isEmpty()){

            int quantityDesired = Integer.parseInt(this.quantityDesired.getText());

            if(!firstName.isEmpty() && !lastName.isEmpty() && !phoneNum.isEmpty() && !UPC.isEmpty() && quantityDesired != 0){
                // sets customer object
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmailAddress(email);
                customer.setPhoneNumber(phoneNum);

                // sets item object
                item.setUPC(UPC);
                item.setDesiredQuantity(quantityDesired);

                this.waitlistHelper.addWaitlistItem(item, customer, employee);
                addMsg.setText("Waitlist entry added successfully");
            }

        }
        else{
            addMsg.setText("Please enter data in required fields");
        }



    }

    @FXML
    public void reset(ActionEvent event){
        UPC.clear();
        firstName.clear();
        lastName.clear();
        phoneNum.clear();
        emailAddress.clear();
        rewardsNum.clear();
        quantityDesired.clear();
        rewardsMsg.setText("");
        addMsg.setText("");

        Label placeholder = new Label("No rows to display.");
        waitlistResults.setPlaceholder(placeholder);
        waitlistResults.getItems().clear();
        waitlistResults.setPlaceholder(placeholder);
        searchCustomerLast.clear();
        searchCustomerPhone.clear();
        fromDate.setValue(null);
        toDate.setValue(null);
        searchUPC.clear();
        searchMsg.setText("");

    }


    @FXML
    public void enableUPC(ActionEvent event){
        searchUPC.setDisable(false);

        searchCustomerPhone.setDisable(true);
        searchCustomerLast.setDisable(true);
        fromDate.setDisable(true);
        toDate.setDisable(true);
    }

    @FXML
    public void enableCustomer(ActionEvent event){
        searchCustomerPhone.setDisable(false);
        searchCustomerLast.setDisable(false);

        fromDate.setDisable(true);
        toDate.setDisable(true);
        searchUPC.setDisable(true);
    }

    @FXML
    public void enableDate(ActionEvent event){
        fromDate.setDisable(false);
        toDate.setDisable(false);

        searchUPC.setDisable(true);
        searchCustomerPhone.setDisable(true);
        searchCustomerLast.setDisable(true);
    }

    @FXML
    public void searchWaitlist(ActionEvent event) throws SQLException {

        ObservableList<WaitlistItem> waitlistItems = FXCollections.observableArrayList();


        if(!searchUPC.isDisabled()){

            if(!searchUPC.getText().isEmpty()){

                waitlistItems = this.waitlistHelper.findWaitlistItems(searchUPC.getText());

            }
            else{
                searchMsg.setText("Please enter a valid UPC");
            }

        }

        if(!searchCustomerLast.isDisabled() && !searchCustomerPhone.isDisabled()){

            if(!searchCustomerLast.getText().isEmpty() && !searchCustomerPhone.getText().isEmpty()){

                waitlistItems = this.waitlistHelper.findWaitlistItems(searchCustomerPhone.getText(), searchCustomerLast.getText());

            }
            else{
                searchMsg.setText("Please enter a valid customer name and phone number");
            }
        }

        if(!fromDate.isDisabled() && !toDate.isDisabled()){

            if(fromDate.getValue() != null && toDate.getValue() != null){

                Date startDate = Date.valueOf(fromDate.getValue());
                Date endDate = Date.valueOf(toDate.getValue());

                waitlistItems = this.waitlistHelper.findWaitlistItems(startDate, endDate);

            }
            else{
                searchMsg.setText("Please enter a valid date range");
            }
        }

        if(!waitlistItems.isEmpty()){
            waitlistResults.setItems(waitlistItems);
        }
        else{
            Label placeholder = new Label("No results.");
            waitlistResults.setPlaceholder(placeholder);
        }

    }

    @FXML
    public void deleteWaitlistItem(ActionEvent event) throws SQLException {

        if(waitlistResults.getSelectionModel().getSelectedItem() != null){

            WaitlistItem itemToDelete = waitlistResults.getSelectionModel().getSelectedItem();

            this.waitlistHelper.deleteFromWaitlist(itemToDelete);

            searchMsg.setText("Item deleted successfully");

        }
        else{
            searchMsg.setText("Please select a row to delete");
        }
    }


}
